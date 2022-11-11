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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

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

        //String  drugSeq = drugSeqGnrService.getNextStringId(); //복약
        System.out.println("서비스 시작");
        int drugSeq = Integer.parseInt(drugSeqGnrService.getNextStringId());

        DrugVO drugEntity = new DrugVO();
        drugEntity.setDrugSeq(drugSeq);
        drugEntity.setAdmissionId(vo.getAdmissionId());
        drugEntity.setNoticeStartDate(vo.getNoticeStartDate());
        drugEntity.setNoticeEndDate(vo.getNoticeEndDate());
        drugEntity.setNoticeDate(vo.getNoticeDate());
        drugEntity.setNoticeName(vo.getNoticeName());
        drugEntity.setDrugName(vo.getDrugName());
        drugEntity.setDrugCount(vo.getDrugCount());
        drugEntity.setDrugType(vo.getDrugType());
        drugDao.insertDrug(drugEntity);

        //int drugSeq = drugVO.getDrugSeq();
        List<DrugAlarmVO> drugAlarmVO = vo.getNoticeTimeList();
        int alarmSize = drugAlarmVO.size();
        if (drugAlarmVO.size() > 0 ){
            for(DrugAlarmVO ra: drugAlarmVO){
                int drugAlarmSeq = Integer.parseInt(drugAlarmSeqGnrService.getNextStringId());
                ra.setDrugSeq(drugSeq);
                ra.setDrugAlarmSeq(drugAlarmSeq);
                drugDao.insertDrugAlarm(ra);
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
            int drugDoseSeq = Integer.parseInt(drugDoseSeqGnrService.getNextStringId());

            String noticeDD =vo.getResultDate();             //복약일자
            String noticeTime =vo.getResultTime();           //복약시간
            DrugDoseVO drugEntity = new DrugDoseVO();

            drugEntity.setDrugDoseSeq(drugDoseSeq);
            drugEntity.setAdmissionId(drugVO.getAdmissionId());
            drugEntity.setNoticeDd(noticeDD);
            drugEntity.setNoticeTime(noticeTime);
            drugEntity.setNoticeName(drugVO.getNoticeName());
            drugEntity.setDrugCount(drugVO.getDrugCount());
            drugEntity.setDrugType(drugVO.getDrugType());
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

        int drugDoseSeq = Integer.parseInt(drugDoseSeqGnrService.getNextStringId());

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

        drugDao.insertDrugDose(drugEntity);
        String rtn = Integer.toString(drugDoseSeq);

        return  rtn;

    }


    public List<DrugTimeListVO> selectDrugTimeList(DrugSearchVO vo) {
        return drugDao.selectTimeList(vo);
    }

    public List<DrugNoticeListVO> selectAlarmList(DrugSearchVO vo) {
        return  drugDao.selectAlarmList(vo);
    }

}
