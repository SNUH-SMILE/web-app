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
import java.util.stream.Collectors;

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
		// 0. 반환 정보 구성
		PatientStatusDashboardVO patientStatusDashboardVO = new PatientStatusDashboardVO();
		// 1. 대시보드 헤더 정보
		patientStatusDashboardVO.setHeader(patientDashboardDao.selectPatientStatusDashboardHeader(vo));
		// 2. 대시보드 환자 리스트
		List<PatientStatusDashboardDetailVO> list = patientDashboardDao.selectPatientStatusDashboardDetailList(vo);

		// 2-1. 최근 측정결과 조회
		if (list != null && list.size() > 0) {
			List<String> listAdmissionId = list.stream().map(PatientStatusDashboardDetailVO::getAdmissionId).collect(Collectors.toList());
			List<VitalResultVO> vitalResultVOList = resultDao.selectLastVitalResultByAdmissionIdList(listAdmissionId);

			if (vitalResultVOList != null && listAdmissionId.size() > 0) {
				for (PatientStatusDashboardDetailVO detailVO : list) {
					List<VitalResultVO> temp = vitalResultVOList.stream().filter(x -> x.getAdmissionId().equals(detailVO.getAdmissionId())).collect(Collectors.toList());

					if (temp.size() == 1) {
						BeanUtils.copyProperties(temp.get(0), detailVO);
					}
				}
			}
		}


		patientStatusDashboardVO.setPatientList(list);

		return patientStatusDashboardVO;
	}

}
