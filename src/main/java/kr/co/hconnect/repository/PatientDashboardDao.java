package kr.co.hconnect.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.PatientDashboardCenterInfoVO;
import kr.co.hconnect.vo.PatientDashboardVO;

/**
 * 환자 대쉬보드 Dao
 */
@Repository
public class PatientDashboardDao extends EgovAbstractMapper {

	/**
	 * 환자 대쉬보드 센터 정보 조회
	 * @param centerId - 센터ID
	 * @return PatientDashboardCenterInfoVO - 환자 대쉬보드 센터정보
	 */
	public PatientDashboardCenterInfoVO selectPatientDashboardCenterInfo(String centerId) {
		return selectOne("kr.co.hconnect.sqlmapper.selectPatientDashboardCenterInfo", centerId);
	}
	
	/**
	 * 환자 대쉬보드 리스트 조회
	 * @param centerId - 센터ID
	 * @return List<PatientDashboardVO> - 환자 대쉬보드 리스트 
	 */
	public List<PatientDashboardVO> selectPatientDashboardList(String centerId) {
		return selectList("kr.co.hconnect.sqlmapper.selectPatientDashboardList", centerId);
	}
	
}
