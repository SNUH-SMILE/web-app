package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.common.ItemId;
import kr.co.hconnect.common.ResultType;
import kr.co.hconnect.domain.*;
import kr.co.hconnect.repository.MeasurementResultDao;
import kr.co.hconnect.vo.AdmissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class MeasurementResultService extends EgovAbstractServiceImpl {


    /**
     * 격리/입소내역 관리 Service
     */
    private final AdmissionService admissionService;

    /**
     * 메인 Dao
     */
    private final MeasurementResultDao dao;

    /**
     * 생성자
     *
     * @param dao MeasurementResultDao
     */
    @Autowired
    public MeasurementResultService(AdmissionService admissionService, MeasurementResultDao dao) {
        this.admissionService = admissionService;
        this.dao = dao;
    }

    /**
     * 신규 알림 여부조회
     * @param admissionId 입소자 Id
     * @return 읽지않은 공지사항 갯수
     */
    public int unReadNotice(String admissionId) {
        return dao.selectUnreadNotice(admissionId);
    }

    /**
     * 측정일자별 상세체온 목록 조회
     *
     * @param searchResultInfo 측정결과 검색 조건
     * @return 측정일자별 상세체온목록
     */
    public List<BtResult> selectBtLIst(SearchResultInfo searchResultInfo) {
        searchResultInfo.setItemId(ItemId.BODY_TEMPERATURE.getItemId());
        return dao.selectBtList(searchResultInfo);
    }

    /**
     * 측정일자별 상세심박수 목록 조회
     *
     * @param searchResultInfo 측정결과 검색 조건
     * @return 측정일자별 상세심박수 목록
     */
    public List<HrResult> selectHrList(SearchResultInfo searchResultInfo) {
        searchResultInfo.setItemId(ItemId.HEART_RATE.getItemId());
        return dao.selectHrList(searchResultInfo);
    }

    /**
     * 측정일자별 상세산소포화도 목록 조회
     *
     * @param searchResultInfo 측정결과 검색 조건
     * @return 측정일자별 상세산소포화도 목록
     */
    public List<SpO2Result> selectSpO2List(SearchResultInfo searchResultInfo) {
        searchResultInfo.setItemId(ItemId.OXYGEN_SATURATION.getItemId());
        return dao.selectSpO2List(searchResultInfo);
    }


    /**
     * 측정일자별 상세혈압 목록 조회
     *
     * @param searchResultInfos 측정결과 검색 조건
     * @return 측정일자별 상세혈압 목록
     */
    public List<BpResult> selectBpList(SearchResultInfos searchResultInfos) {

        searchResultInfos.setItemId(ItemId.BLOOD_PRESSURE.getItemId());
        //최저
        searchResultInfos.setFirstResultType(ResultType.MINIMUM_BLOOD_PRESSURE.getResultType());
        //최고
        searchResultInfos.setSecondResultType(ResultType.MAXIMUM_BLOOD_PRESSURE.getResultType());

        return dao.selectBpList(searchResultInfos);
    }

    /**
     * 측정일자별 상세걸음수 목록 조회
     *
     * @param searchResultInfos 측정결과 검색 조건
     * @return 측정일자별 상세걸음수 목록
     */
    public List<StepCountResult> selectStepList(SearchResultInfos searchResultInfos) {

        searchResultInfos.setItemId(ItemId.STEP_COUNT.getItemId());
        //걸음수
        searchResultInfos.setFirstResultType(ResultType.STEP_COUNT.getResultType());
        //거리
        searchResultInfos.setSecondResultType(ResultType.DISTANCE.getResultType());

        return dao.selectStepList(searchResultInfos);
    }

    /**
     * 측정일자별 상세수면 목록 조회
     *
     * @param searchSleepResultInfo 측정결과 검색 조건
     * @return 측정일자별 상세수면 목록
     */
    public List<SleepTimeResult> selectSleepTimeList(SearchSleepResultInfo searchSleepResultInfo) {

        return dao.selectSleepTimeList(searchSleepResultInfo);
    }

    /**
     * 총수면시간 계산
     *
     * @param sleepTimeResultList 수면시간 목록
     * @return 총수면시간 분으로 반환
     */
    public int getTempTotalSleep(List<SleepTimeResult> sleepTimeResultList) {
        int tempTotalSleep = 0;
        for (int i = 0; i < sleepTimeResultList.size(); i++) {
            tempTotalSleep += LocalDateTime.of(sleepTimeResultList.get(i).getSleepStartDate()
                            , sleepTimeResultList.get(i).getSleepStartTime())
                    .until(LocalDateTime.of(sleepTimeResultList.get(i).getSleepEndDate()
                                    , sleepTimeResultList.get(i).getSleepEndTime())
                            , ChronoUnit.MINUTES);
        }
        return tempTotalSleep;
    }

    /**
     * 메인컨텐츠 조회
     *
     * @param loginId 로그인 Id
     * @return MainContentDetail
     */
    public MainContentDetail mainService(String loginId) {
        //환자정보
        AdmissionVO admissionVO = admissionService.selectActiveAdmissionByLoginId(loginId);
        //오늘날짜
        LocalDateTime today = LocalDateTime.now();
        //측정타입 하나인 검색조건
        SearchResultInfo searchResultInfo = new SearchResultInfo();
        searchResultInfo.setAdmissionId(admissionVO.getAdmissionId());
        searchResultInfo.setResultDate(today.toLocalDate());
        //측정타입 두개인 검색조건
        SearchResultInfos searchResultInfos = new SearchResultInfos();
        searchResultInfos.setAdmissionId(searchResultInfo.getAdmissionId());
        searchResultInfos.setResultDate(today.toLocalDate());
        //수면 검색조건
        SearchSleepResultInfo searchSleepResultInfo = new SearchSleepResultInfo();
        searchSleepResultInfo.setAdmissionId(searchResultInfo.getAdmissionId());
        searchSleepResultInfo.setResultStartDateTime(today.toLocalDate().minusDays(1).atTime(21, 0));
        searchSleepResultInfo.setResultEndDateTime(today.toLocalDate().atTime(9, 0));

        MainContentDetail mainContentDetail = new MainContentDetail();
        mainContentDetail.setPatientNm(admissionVO.getPatientId());
        mainContentDetail.setAdmissionDate(admissionVO.getAdmissionDate());
        mainContentDetail.setDischargeScheduledDate(admissionVO.getDschgeSchdldDate());
        mainContentDetail.setDischargeDate(admissionVO.getDschgeDate());
        mainContentDetail.setPersonCharge(admissionVO.getPersonCharge());
        mainContentDetail.setTodayBtList(selectBtLIst(searchResultInfo));
        mainContentDetail.setTodayBpList(selectBpList(searchResultInfos));
        mainContentDetail.setTodayHrList(selectHrList(searchResultInfo));
        mainContentDetail.setTodaySpO2List(selectSpO2List(searchResultInfo));
        mainContentDetail.setTodayStepCountList(selectStepList(searchResultInfos));
        mainContentDetail.setTodaySleepTimeList(selectSleepTimeList(searchSleepResultInfo));
        if(mainContentDetail.getTodaySleepTimeList().size()>0){
            //총 수면시간
            List<SleepTimeResult> sleepTimeResultList = mainContentDetail.getTodaySleepTimeList();
            int tempTodayTotalSleep = getTempTotalSleep(sleepTimeResultList);
            LocalTime tempTotalSleepTime = LocalTime.of((tempTodayTotalSleep / 60)
                    , (tempTodayTotalSleep % 60)
            );
            mainContentDetail.setTodayTotalSleepTime(tempTotalSleepTime);
        }
        else if(mainContentDetail.getTodaySleepTimeList().size()==0){
            mainContentDetail.setTodayTotalSleepTime(LocalTime.of(0,0));
        }

        return mainContentDetail;

    }
}
