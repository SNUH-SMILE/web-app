package kr.co.hconnect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.repository.PatientDashboardDao;
import kr.co.hconnect.vo.PatientDashboardCenterInfoVO;
import kr.co.hconnect.vo.PatientDashboardVO;

/**
 * 환자 대쉬보드 서비스
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class PatientDashboardService extends EgovAbstractServiceImpl {

	/**
	 * 환자 대쉬보드 Dao
	 */
	private final PatientDashboardDao dao;
	
	@Autowired
	public PatientDashboardService(PatientDashboardDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 환자 대쉬보드 센터 정보 조회
	 * @param centerId - 센터ID
	 * @return PatientDashboardCenterInfoVO - 환자 대쉬보드 센터정보
	 */
	public PatientDashboardCenterInfoVO selectPatientDashboardCenterInfo(String centerId) {
		return dao.selectPatientDashboardCenterInfo(centerId);
	}
	
	/**
	 * 환자 대쉬보드 리스트 조회
	 * @param centerId - 센터ID
	 * @return List<PatientDashboardVO> - 환자 대쉬보드 리스트 
	 */
	public List<PatientDashboardVO> selectPatientDashboardList(String centerId) {
		return dao.selectPatientDashboardList(centerId);
	}
}
