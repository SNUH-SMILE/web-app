package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.hconnect.repository.TreatmentCenterDao;
import kr.co.hconnect.vo.TreatmentCenterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 생활치료센터 Service
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class TreatmentCenterService extends EgovAbstractServiceImpl {

    private final TreatmentCenterDao treatmentCenterDao; //생활치료센터 Dao

    private final EgovIdGnrService centerIdGnrService; //생활치료센터 Id채번 서비스

    /**
     * 생성자
     * @param treatmentCenterDao 생활치료센터 Dao
     * @param centerIdGnrService 생활치료센터 Id 채번 서비스
     */
    @Autowired
    public TreatmentCenterService(TreatmentCenterDao treatmentCenterDao
                                , @Qualifier("centerIdGnrService") EgovIdGnrService centerIdGnrService) {
        this.treatmentCenterDao = treatmentCenterDao;
        this.centerIdGnrService = centerIdGnrService;
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
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteTreatmentCenter(TreatmentCenterVO vo){
        treatmentCenterDao.deleteTreatmentCenter(vo);
    }
}
