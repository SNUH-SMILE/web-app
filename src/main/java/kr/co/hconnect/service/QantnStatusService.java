package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.domain.QantnStatus;
import kr.co.hconnect.domain.SaveQuarantineStatusInfo;
import kr.co.hconnect.repository.QantnStatusDao;
import kr.co.hconnect.vo.AdmissionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 격리상태 관리 Service
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class QantnStatusService extends EgovAbstractServiceImpl {

    /**
     * 격리/입소내역 관리 Service
     */
    private final AdmissionService admissionService;

    /**
     * 격리상태 관리 Dao
     */
    private final QantnStatusDao qantnStatusDao;

    /**
     * 생성자
     *
     * @param admissionService 격리/입소내역 관리 Service
     * @param qantnStatusDao 격리상태 관리 Dao
     */
    @Autowired
    public QantnStatusService(AdmissionService admissionService, QantnStatusDao qantnStatusDao) {
        this.admissionService = admissionService;
        this.qantnStatusDao = qantnStatusDao;
    }

    /**
     * 격리상태 저장
     *
     * @param saveQuarantineStatusInfo 격리상태 저장 정보
     * @return QantnStatus 격리상태
     */
    @Transactional(rollbackFor = Exception.class)
    public QantnStatus insertQantnStatus(SaveQuarantineStatusInfo saveQuarantineStatusInfo) {
        // Active 격리/입소내역 조회
        AdmissionVO admissionVO = admissionService.selectActiveAdmissionByLoginId(saveQuarantineStatusInfo.getLoginId());

        // 격리/입소내역ID
        String admissionId = admissionVO.getAdmissionId();

        // 격리상태 저장정보 생성
        QantnStatus qantnStatus = new QantnStatus();
        qantnStatus.setAdmissionId(admissionId);
        qantnStatus.setQantnStatusDiv(saveQuarantineStatusInfo.getQuarantineStatusDiv());

        // 격리상태 저장
        qantnStatusDao.insertQantnStatus(qantnStatus);

        return qantnStatusDao.selectActiveQantnStatus(admissionId);
    }

}
