package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.domain.Interview;
import kr.co.hconnect.domain.InterviewDetail;
import kr.co.hconnect.domain.SaveInformaionInfo;
import kr.co.hconnect.domain.SaveInformationAnswerListInfo;
import kr.co.hconnect.exception.NotFoundAdmissionInfoException;
import kr.co.hconnect.repository.InterviewDao;
import kr.co.hconnect.vo.*;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class InterviewService extends EgovAbstractServiceImpl {

    private final InterviewDao interviewDao;

    private final AdmissionService admissionService;
    private final EgovIdGnrService interviewIdGnrService;
    private final MessageSource messageSource;
    private final EgovIdGnrService interviewDetailIdGnrService;

    public InterviewService(InterviewDao interviewDao, AdmissionService admissionService, EgovIdGnrService interviewIdGnrService, MessageSource messageSource, EgovIdGnrService interviewDetailIdGnrService) {
        this.interviewDao = interviewDao;
        this.admissionService = admissionService;
        this.interviewIdGnrService = interviewIdGnrService;
        this.messageSource = messageSource;
        this.interviewDetailIdGnrService = interviewDetailIdGnrService;
    }

    public String getAdmissionId(String loginId) {
        return admissionService.selectAdmissionListByLoginId(loginId).getAdmissionId();
    }
    public AdmissionInfoVO getAdmission(String admissionId){
        return admissionService.selectAdmissionInfo(admissionId);
    }
    /**
     * 문진 조회
     *  @param vo InterviewListSearchVO 문진조회정보 VO
     *  @return InterviewListResponseByCenterVO 문진 조회 결과
     * */
    public InterviewListResponseByCenterVO selectInterview(InterviewListSearchVO interviewListSearchVO){

        String admissionId = getAdmissionId(interviewListSearchVO.getLoginId());

        Interview interview = new Interview();
        interview.setRequestDate(interviewListSearchVO.getRequestDate().substring(0,8));
        interview.setAdmissionId(admissionId);

        AdmissionInfoVO admissionInfoVO = getAdmission(admissionId);
        /* 날짜비교를 위한 */
        LocalDate admissionDate = admissionInfoVO.getAdmissionDate();
        LocalDate dschgeDtate = admissionInfoVO.getDschgeDate();

        LocalDate now = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        InterviewListResponseByCenterVO vo = new InterviewListResponseByCenterVO();

        vo.setInterviewList(interviewDao.selectInterviewForDateList(interview));
        List<SymptomList> symptomLists = new ArrayList<>();
        for(InterviewList interviewList: vo.getInterviewList()){
           symptomLists.addAll(interviewDao.selectInterviewDetailSymptomList(interviewList.getInterviewSeq()));
        }
        vo.setSymptomLists(symptomLists);

        /*확진당일문진*/
        if((vo.getInterviewList().stream().filter(i -> ("01").equals(i.getInterviewType())).collect(Collectors.toList()).size()) == 0 && now.compareTo(admissionDate) == 0){
            InterviewList interviewList = new InterviewList();
            interviewList.setInterviewType("01");
            interviewList.setInterviewStatus("0");
            interviewList.setInterviewTitle("확진 당일 문진");
            vo.getInterviewList().add(interviewList);
        }
        /*일일문진*/
        if((vo.getInterviewList().stream().filter(i -> ("02").equals(i.getInterviewType())).collect(Collectors.toList()).size())==0 && dschgeDtate == null){
            InterviewList interviewList = new InterviewList();
            interviewList.setInterviewType("02");
            interviewList.setInterviewStatus("0");//작성하기
            interviewList.setInterviewTitle("일일문진");
            vo.getInterviewList().add(interviewList);
        }
        /*격리해제 문진*/
        if((vo.getInterviewList().stream().filter(i -> ("03").equals(i.getInterviewType())).collect(Collectors.toList()).size())==0 && now.equals(dschgeDtate)){
            InterviewList interviewList = new InterviewList();
            interviewList.setInterviewType("03");
            interviewList.setInterviewStatus("0"); //작성하기
            interviewList.setInterviewTitle("격리해제");
            vo.getInterviewList().add(interviewList);
        }
        /*격리해제 후 30일 문진*/
        if((vo.getInterviewList().stream().filter(i -> ("04").equals(i.getInterviewType())).collect(Collectors.toList()).size())==0 && now.minusDays(30).equals(dschgeDtate)){
            InterviewList interviewList = new InterviewList();
            interviewList.setInterviewType("04");
            interviewList.setInterviewStatus("0"); //작성하기
            interviewList.setInterviewTitle("격리해제 30일 후 문진");
            vo.getInterviewList().add(interviewList);
        }

        return vo;
    }
    /**
     * 문진 저장
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertInterview(SaveInformaionInfo saveInformaionInfo) throws FdlException {
        Integer interviewIdGnr = null;
        interviewIdGnr = interviewIdGnrService.getNextIntegerId();
        String admissionId = getAdmissionId(saveInformaionInfo.getLoginId());
        //입소내역 있으면
        String title = null;
        if(saveInformaionInfo.getInterviewType() != null) {
            String type = saveInformaionInfo.getInterviewType();
            switch(type) {
                case "01":
                    title = "확진 당일 문진";
                    break;
                case "02":
                    title = "일일 문진";
                    break;
                case "03":
                    title = "격리 해제일 문진";
                    break;
                case "04":
                    title = "격리 해제 30일 후 문진";
                    break;
            }
        }
        if(admissionId != null) {
            Interview interview = new Interview();
            interview.setId(interviewIdGnr);
            interview.setTitle(title);
            interview.setDd(saveInformaionInfo.getInterviewDate().substring(0,8));
            interview.setTime(saveInformaionInfo.getInterviewDate().substring(8,12));
            interview.setState("3"); //0:작성하기/ 1:작성하기 비활성화 / 2:작성불가 3: 작성완료
            interview.setType(saveInformaionInfo.getInterviewType());
            interview.setAdmissionId(admissionId);
            interview.setRegId(saveInformaionInfo.getLoginId());
            interviewDao.insertInterview(interview);

            for(SaveInformationAnswerListInfo list : saveInformaionInfo.getAnswerList()){
                Integer interviewDetailIdGnr = null;
                interviewDetailIdGnr = interviewDetailIdGnrService.getNextIntegerId();

                InterviewDetail detail = new InterviewDetail();
                detail.setDetailSeq(interviewDetailIdGnr);
                detail.setSeq(interviewIdGnr);
                detail.setAnswerSeq(list.getAnswerNumber());
                detail.setAnswerValue(list.getAnswerValue());
                detail.setRegId(saveInformaionInfo.getLoginId());
                interviewDao.insertInterviewDetail(detail);
            }
        } else {
            if (admissionId == null) {
                throw new NotFoundAdmissionInfoException(ApiResponseCode.NOT_FOUND_ADMISSION_INFO.getCode()
                    , messageSource.getMessage("message.notfound.admissionInfo"
                    , null, Locale.getDefault()));
            }
        }
    }

}
