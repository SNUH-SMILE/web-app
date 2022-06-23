package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.exception.NotFoundAdmissionInfoException;
import kr.co.hconnect.repository.AdmissionDao;
import kr.co.hconnect.repository.NoticeDao;
import kr.co.hconnect.vo.AdmissionInfoVO;
import kr.co.hconnect.vo.NoticeListSearchVO;
import kr.co.hconnect.vo.NoticeVO;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

/**
 * 알림 Service
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class NoticeService extends EgovAbstractServiceImpl {

    /**
     * 알림 Dao
     */
    private final NoticeDao noticeDao;
    /**
     * 입소내역 Dao
     */
    private final AdmissionDao admissionDao;
    /**
     * MessageSource
     */
    private final MessageSource messageSource;

    /**
     * 생성자
     * @param noticeDao 알림 Dao
     * @param admissionDao 입소내역 Dao
     * @param messageSource MessageSource
     */
    public NoticeService(NoticeDao noticeDao, AdmissionDao admissionDao, MessageSource messageSource) {
        this.noticeDao = noticeDao;
        this.admissionDao = admissionDao;
        this.messageSource = messageSource;
    }

    /**
     * 신규 알림 내역 추가
     *
     * @param vo 신규 알림 저장 정보
     * @return int 알림순번(NOTICE_SEQ)
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertNotice(NoticeVO vo) throws NotFoundAdmissionInfoException {
        // 입소내역 존재여부 확인
        AdmissionInfoVO admissionInfoVO = admissionDao.selectAdmissionInfo(vo.getAdmissionId());
        if (admissionInfoVO == null || admissionInfoVO.getDelYn().equals("Y")) {
            throw new NotFoundAdmissionInfoException(ApiResponseCode.NOT_FOUND_ADMISSION_INFO.getCode()
                    , messageSource.getMessage("message.notfound.admissionInfo"
                    , null, Locale.getDefault()));
        }

        noticeDao.insertNotice(vo);
        return vo.getNoticeSeq();
    }

    /**
     * 알림 리스트 조회 - 격리/입소내역ID 기준
     *
     * @param vo 알림 리스트 조회 조건
     * @return List&lt;NoticeVO&gt; 알림 리스트
     */
    public List<NoticeVO> selectNoticeList(NoticeListSearchVO vo) {
        return noticeDao.selectNoticeListByAdmissionId(vo);
    }
}
