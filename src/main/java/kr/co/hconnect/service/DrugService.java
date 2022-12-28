package kr.co.hconnect.service;


import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.hconnect.exception.ActiveAdmissionExistsException;
import kr.co.hconnect.repository.PatientDeviceDao;
import kr.co.hconnect.repository.PatientEquipDao;
import kr.co.hconnect.repository.TreatmentCenterDao;
import kr.co.hconnect.vo.*;
import kr.co.hconnect.repository.DrugDao;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 복약 서비스
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class DrugService extends EgovAbstractServiceImpl {
    /**
     * MessageSource
     */

    private final EgovIdGnrService drugSeqGnrService;
    private final EgovIdGnrService drugAlarmSeqGnrService;
    private final EgovIdGnrService drugDoseSeqGnrService;

    //@autowired
    private  final  DrugDao drugDao;

   // 서비스 생성하기
    @Autowired
    public DrugService(DrugDao drugDao
        , @Qualifier("drugSeqGnrService") EgovIdGnrService drugSeqGnrService
        , @Qualifier("drugAlarmSeqGnrService") EgovIdGnrService drugAlarmSeqGnrService
        , @Qualifier("drugDoseSeqGnrService") EgovIdGnrService drugDoseSeqGnrService
        , MessageSource messageSource
   ) {
        this.drugDao = drugDao;
        this.drugSeqGnrService = drugSeqGnrService;
        this.drugAlarmSeqGnrService = drugAlarmSeqGnrService;
        this.drugDoseSeqGnrService = drugDoseSeqGnrService;
    }


    /**
     * 생활치료센터 저장
     * @param vo 생활치료센터 저장 정보
     */
    @Transactional(rollbackFor = Exception.class)
    public String insertDrugAlarm(DrugAlarmSaveVO vo) throws FdlException {
        System.out.println("서비스 시작");
        int drugSeq = drugSeqGnrService.getNextIntegerId();

        DrugVO drugEntity = new DrugVO();
        drugEntity.setDrugSeq(drugSeq);
        drugEntity.setAdmissionId(vo.getAdmissionId());
        drugEntity.setNoticeStartDate(vo.getNoticeStartDate());
        drugEntity.setNoticeEndDate(vo.getNoticeEndDate());
        drugEntity.setNoticeDate(vo.getNoticeDate());
        drugEntity.setNoticeName(vo.getNoticeName());
        drugDao.insertDrug(drugEntity);


        System.out.println("알람 서비스 시작");
        //int drugSeq = drugVO.getDrugSeq();
        List<DrugAlarmVO> drugAlarmVO = vo.getNoticeTimeList();
        int alarmSize = drugAlarmVO.size();
        if (drugAlarmVO.size() > 0 ){
            for(DrugAlarmVO ra: drugAlarmVO){
                int drugAlarmSeq = drugAlarmSeqGnrService.getNextIntegerId();
                ra.setDrugSeq(drugSeq);
                ra.setDrugAlarmSeq(drugAlarmSeq);
                drugDao.insertDrugAlarm(ra);
            }
        }

        //약물리스트
        List<DrugListVO> drugList = vo.getDrugList();
        if (drugList.size() > 0 ){
            for(DrugListVO dl: drugList){
                dl.setDrugSeq(drugSeq);
                drugDao.insertDrugList(dl);
            }
        }

        String rtn = Integer.toString(drugSeq);
        return  rtn;
    }

    /**
     * 복약정보 저장
     * @param vo
     * @return
     * @throws FdlException
     */
    @Transactional(rollbackFor = Exception.class)
    public String insertDrugDose(DrugDoseSaveVO vo) throws FdlException {

        int drugAlarmSeq = vo.getDrugAlarmSeq();
        //select gkrh
        DrugAlarmVO alarmEntity = new DrugAlarmVO();
        alarmEntity.setDrugAlarmSeq(vo.getDrugAlarmSeq());

        DrugVO drugVO = new DrugVO();
        drugVO = drugDao.selectAlarmDrug(alarmEntity);

        if (drugVO != null){
            //복약정보 등록
            int drugDoseSeq = drugDoseSeqGnrService.getNextIntegerId();

            String noticeDD =vo.getResultDate();             //복약일자
            String noticeTime =vo.getResultTime();           //복약시간
            DrugDoseVO drugEntity = new DrugDoseVO();

            drugEntity.setDrugDoseSeq(drugDoseSeq);
            drugEntity.setAdmissionId(drugVO.getAdmissionId());
            drugEntity.setNoticeDd(noticeDD);
            drugEntity.setNoticeTime(noticeTime);
            drugEntity.setNoticeName(drugVO.getNoticeName());
           drugEntity.setTakeResult(vo.getTakeResult());
            drugEntity.setDrugSeq(drugVO.getDrugSeq());
            drugEntity.setDrugAlarmSeq(vo.getDrugAlarmSeq());

            drugDao.insertDrugDose(drugEntity);
        }

        String rtn = Integer.toString(drugAlarmSeq);
        return  rtn;
    }

    /**
     * 복약정보 알림없이저장
     * @param vo
     * @return
     * @throws FdlException
     */
    @Transactional(rollbackFor = Exception.class)
    public String insertNoAlarmDrugDose(DrugDoseSaveVO vo) throws FdlException {

        int drugDoseSeq = drugDoseSeqGnrService.getNextIntegerId();

        DrugDoseVO drugEntity = new DrugDoseVO();

        drugEntity.setDrugDoseSeq(drugDoseSeq);
        drugEntity.setAdmissionId(vo.getAdmissionId());
        drugEntity.setNoticeDd(vo.getResultDate());
        drugEntity.setNoticeTime(vo.getResultTime());
        drugEntity.setNoticeName(vo.getNoticeName());
        drugEntity.setDrugName(vo.getDrugName());
        drugEntity.setDrugCount(vo.getDrugCount());
        drugEntity.setDrugType(vo.getDrugType());
        drugEntity.setTakeResult(vo.getTakeResult());
        drugEntity.setDrugSeq(vo.getDrugSeq());
        drugEntity.setNoAlarm("Y");                            //복약 알림없이 체크 'Y'

        drugDao.insertDrugDose(drugEntity);
        String rtn = Integer.toString(drugDoseSeq);

        return  rtn;

    }


    public DrugTimeListVO selectDrugTimeList(DrugSearchVO vo) {

        System.out.println("타임 리스트 서비스 시작");

        DrugTimeListVO dtvo = new DrugTimeListVO();
        //복약리스트
        List<DrugTimeVO> dt = drugDao.selectTimeList(vo);

        //여러개의 리스틀
        if (dt != null && dt.size() > 0) {
            for (DrugTimeVO entity : dt){
                vo.setDrugSeq(entity.getDrugSeq());
                vo.setDrugDoseSeq(entity.getDrugDoseSeq());
                String noAlarm = entity.getNoAlarm();

                System.out.println("noAlarm  :  " + noAlarm);
                List<DrugListNameVO> dnm = null;
                if (StringUtils.equals(noAlarm, "Y") ) {
                    dnm = drugDao.selectDrugListNameNoAlarm(vo);
                } else {
                    dnm = drugDao.selectDrugListName(vo);
                }
                entity.setDrugList(dnm);
            }
        }
        dtvo.setDrugTimeList(dt);
        return  dtvo;
    }

    public DrugNoticeVO selectAlarmList(DrugSearchVO vo) {
        DrugNoticeVO dvo = new DrugNoticeVO();

        List<DrugNoticeListVO> dnl = drugDao.selectAlarmList(vo);

        if (dnl != null && dnl.size() > 0){
            for(DrugNoticeListVO entity : dnl){
                vo.setDrugSeq(entity.getDrugSeq());

                List<DrugListNameVO> dnm = drugDao.selectDrugListName(vo);
                entity.setDrugList(dnm);
            }
        }
        dvo.setNoticeList(dnl);

        return  dvo;
    }

    /**
     * 환자상세의 투약내역 조회
     * @param admissionId
     * @return
     */
    public  List<DrugDetailVO> selectDrugListForDetail(String admissionId){


        List<DrugDetailVO> drugDetailVOS =  drugDao.selectDrugListForDetail(admissionId);

        return drugDetailVOS;
    }

}
