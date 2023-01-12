package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.exception.NotFoundAdmissionInfoException;
import kr.co.hconnect.repository.AdmissionDao;
import kr.co.hconnect.repository.RecordDao;
import kr.co.hconnect.vo.AdmissionInfoVO;
import kr.co.hconnect.vo.NoticeListSearchVO;
import kr.co.hconnect.vo.NoticeVO;
import kr.co.hconnect.vo.RecordVO;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

/**
 * 입소내역 Dao
 */




/**
 * 진료기록 Service
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class MedicalRecordService extends EgovAbstractServiceImpl {

    private final AdmissionDao admissionDao;
    /**
     * MessageSource
     */
    private final MessageSource messageSource;

    private final RecordDao recordDao;

    public MedicalRecordService(AdmissionDao admissionDao, MessageSource messageSource, RecordDao recordDao) {

        this.admissionDao = admissionDao;
        this.messageSource = messageSource;
        this.recordDao = recordDao;
    }
    /**
     * 신규 알림 내역 추가
     *
     * @param vo 신규 알림 저장 정보
     * @return int 알림순번(NOTICE_SEQ)
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertRecord(RecordVO vo) throws NotFoundAdmissionInfoException {
        // 입소내역 존재여부 확인
        AdmissionInfoVO admissionInfoVO = admissionDao.selectAdmissionInfo(vo.getAdmissionId());
        if (admissionInfoVO == null || admissionInfoVO.getDelYn().equals("Y")) {
            throw new NotFoundAdmissionInfoException(ApiResponseCode.NOT_FOUND_ADMISSION_INFO.getCode()
                , messageSource.getMessage("message.notfound.admissionInfo"
                , null, Locale.getDefault()));
        }

        recordDao.insertRecord(vo);
        return vo.getMedicalSeq();
    }
    @Transactional(rollbackFor = Exception.class)
    public void updateRecord(RecordVO vo){
        /* todo: 입소내역 확인해야 하나 ? 추후에 질문*/
    /*    AdmissionInfoVO admissionInfoVO = admissionDao.selectAdmissionInfo(vo.getAdmissionId());
        if (admissionInfoVO == null || admissionInfoVO.getDelYn().equals("Y")) {
            throw new NotFoundAdmissionInfoException(ApiResponseCode.NOT_FOUND_ADMISSION_INFO.getCode()
                , messageSource.getMessage("message.notfound.admissionInfo"
                , null, Locale.getDefault()));
        }*/

        //히스토리 저장
        recordDao.Recordhist(vo);

        //진료기록 수정
        recordDao.updatePatient(vo);

    }

    /**
     * 알림 리스트 조회 - 격리/입소내역ID 기준
     *
     * @param admissionid
     * @return List&lt;NoticeVO&gt; 알림 리스트
     */
    public List<RecordVO> selectRecordList(String admissionid) {
        return recordDao.selectRecordListByAdmissionId(admissionid);
    }
}
