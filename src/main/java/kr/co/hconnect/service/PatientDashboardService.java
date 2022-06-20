package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.repository.PatientDashboardDao;
import kr.co.hconnect.repository.ResultDao;
import kr.co.hconnect.vo.PatientStatusDashboardDetailSearchVO;
import kr.co.hconnect.vo.PatientStatusDashboardDetailVO;
import kr.co.hconnect.vo.PatientStatusDashboardVO;
import kr.co.hconnect.vo.VitalResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 환자 대시보드 서비스
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class PatientDashboardService extends EgovAbstractServiceImpl {

	/**
	 * 환자 대쉬보드 Dao
	 */
	private final PatientDashboardDao patientDashboardDao;

	/**
	 * 측정결과 Dao
	 */
	private final ResultDao resultDao;

	/**
	 * 생성자
	 *
	 * @param patientDashboardDao 환자 대쉬보드 Dao
	 * @param resultDao 측정결과 Dao
	 */
	@Autowired
	public PatientDashboardService(PatientDashboardDao patientDashboardDao, ResultDao resultDao) {
		this.patientDashboardDao = patientDashboardDao;
		this.resultDao = resultDao;
	}

	/**
	 * 환자 현황 대시보드 환자정보 리스트 조회
	 *
	 * @param vo 환자 현황 대시보드 환자정보 조회조건
	 * @return List&lt;PatientStatusDashboardDetailVO&gt; 환자 현황 대시보드 환자정보 리스트
	 */
	public PatientStatusDashboardVO selectPatientStatusDashboardDetailList(PatientStatusDashboardDetailSearchVO vo) {
		PatientStatusDashboardVO patientStatusDashboardVO = new PatientStatusDashboardVO();
		patientStatusDashboardVO.setHeader(patientDashboardDao.selectPatientStatusDashboardHeader(vo));

		List<PatientStatusDashboardDetailVO> list = patientDashboardDao.selectPatientStatusDashboardDetailList(vo);
		for (PatientStatusDashboardDetailVO detailVO : list) {
			VitalResultVO vitalResultVO = resultDao.selectLastVitalResult(detailVO.getAdmissionId());

			if (vitalResultVO != null) {
				BeanUtils.copyProperties(vitalResultVO, detailVO);
			}
		}

		patientStatusDashboardVO.setPatientList(list);

		return patientStatusDashboardVO;
	}
	
//	/**
//	 * 환자 대쉬보드 센터 정보 조회
//	 * @param centerId - 센터ID
//	 * @return PatientDashboardCenterInfoVO - 환자 대쉬보드 센터정보
//	 */
//	public PatientDashboardCenterInfoVO selectPatientDashboardCenterInfo(String centerId) {
//		return patientDashboardDao.selectPatientDashboardCenterInfo(centerId);
//	}
//
//	/**
//	 * 환자 대쉬보드 리스트 조회
//	 * @param centerId - 센터ID
//	 * @return List<PatientDashboardVO> - 환자 대쉬보드 리스트
//	 */
//	public List<PatientDashboardVO> selectPatientDashboardList(String centerId) {
//		return patientDashboardDao.selectPatientDashboardList(centerId);
//	}
}
