package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.hconnect.exception.ActiveAdmissionExistsException;
import kr.co.hconnect.repository.TreatmentCenterDao;
import kr.co.hconnect.vo.TreatmentCenterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

/**
 * 생활치료센터 Service
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class TreatmentCenterService extends EgovAbstractServiceImpl {

    /**
     * 생활치료센터 Dao
     */
    private final TreatmentCenterDao treatmentCenterDao;
    /**
     * 생활치료센터 Id채번 서비스
     */
    private final EgovIdGnrService centerIdGnrService;
    /**
     * MessageSource
     */
    private final MessageSource messageSource;

    /**
     * 생성자
     * @param treatmentCenterDao 생활치료센터 Dao
     * @param centerIdGnrService 생활치료센터 Id 채번 서비스
     * @param messageSource MessageSource
     */
    @Autowired
    public TreatmentCenterService(TreatmentCenterDao treatmentCenterDao
            , @Qualifier("centerIdGnrService") EgovIdGnrService centerIdGnrService, MessageSource messageSource) {
        this.treatmentCenterDao = treatmentCenterDao;
        this.centerIdGnrService = centerIdGnrService;
        this.messageSource = messageSource;
    }

    /**
     * 생활치료센터 정보 조회
     * @param vo 생활치료센터 조회 조건
     * @return 생활치료센터 정보
     */
    public TreatmentCenterVO selectTreatmentCenter(TreatmentCenterVO vo) {
        return treatmentCenterDao.selectTreatmentCenter(vo);
    }


    /**
     * 생활치료센터 리스트
     * @return 생활치료센터 목록
     */
    public List<TreatmentCenterVO> selectTreatmentCenterList(TreatmentCenterVO vo){
        return treatmentCenterDao.selectTreatmentCenterList(vo);
    }

    /**
     * 생활치료센터 저장
     * @param vo 생활치료센터 저장 정보
     */
    @Transactional(rollbackFor = Exception.class)
    public String insertTreatmentCenter(TreatmentCenterVO vo) throws FdlException {
        String centerId = centerIdGnrService.getNextStringId(); //생활치료센터 Id
        vo.setCenterId(centerId);

        treatmentCenterDao.insertTreatmentCenter(vo);

        return centerId;
    }

    /**
     * 생활치료센터 수정
     * @param vo 생활치료센터 수정 정보
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateTreatmentCenter(TreatmentCenterVO vo) {
        treatmentCenterDao.updateTreatmentCenter(vo);
    }

    /**
     * 생활치료센터 삭제
     * @param vo 생활치료센터 삭제 정보
     * @throws ActiveAdmissionExistsException 삭제 대상 센터에 퇴소하지 않은 입소자가 존재 할 경우 발생
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteTreatmentCenter(TreatmentCenterVO vo) throws ActiveAdmissionExistsException {
        int admissionCount = treatmentCenterDao.selectAdmissionCountByCenter(vo.getCenterId());

        // 퇴소되지 않은 입소자 존재여부 확인
        if (admissionCount > 0) {
            throw new ActiveAdmissionExistsException(
                    messageSource.getMessage("message.treat.center.active.admission"
                            , new Object[] { admissionCount }
                            , Locale.getDefault()));
        }

        treatmentCenterDao.deleteTreatmentCenter(vo);
    }
}
