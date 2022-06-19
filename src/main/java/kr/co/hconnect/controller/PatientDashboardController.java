package kr.co.hconnect.controller;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.service.PatientDashboardService;
import kr.co.hconnect.vo.PatientStatusDashboardDetailSearchVO;
import kr.co.hconnect.vo.PatientStatusDashboardVO;
import kr.co.hconnect.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 환자 대쉬보드 컨트롤러
 */
@RestController
@RequestMapping("/api/patientDashboard")
public class PatientDashboardController {

	/**
	 * 환자 대시보드 Service
	 */
	private final PatientDashboardService patientDashboardService;

	/**
	 * 생성자
	 *
	 * @param patientDashboardService 환자 대시보드 Service
	 */
	@Autowired
	public PatientDashboardController(PatientDashboardService patientDashboardService) {
		this.patientDashboardService = patientDashboardService;
	}

	/**
	 * 환자 대시보드 현황 조회
	 *
	 * @param vo 환자 현황 대시보드 환자정보 조회조건
	 * @return ResponseVO&lt;PatientStatusDashboardVO&gt; 환자 대시보드 현황 결과
	 */
	@RequestMapping(value = "/status/list", method = RequestMethod.POST)
	public ResponseVO<PatientStatusDashboardVO> selectCenterPatientStatusDashboard(@RequestBody PatientStatusDashboardDetailSearchVO vo) {
		ResponseVO<PatientStatusDashboardVO> patientStatusDashboardVOResponseVO = new ResponseVO<>();
		patientStatusDashboardVOResponseVO.setCode(ApiResponseCode.SUCCESS.getCode());
		patientStatusDashboardVOResponseVO.setMessage("조회 성공");
		patientStatusDashboardVOResponseVO.setResult(patientDashboardService.selectPatientStatusDashboardDetailList(vo));

		return patientStatusDashboardVOResponseVO;
	}
	
//	/**
//	 * 환자 대쉬보드 리스트 조회
//	 * @param centerId - 센터ID
//	 * @return List<PatientDashboardVO> - 환자 대쉬보드 리스트
//	 */
//	@RequestMapping(value = "/list.ajax")
//	@ResponseBody
//	public List<PatientDashboardVO> selectPatientDashboardList(
//              @RequestParam(value = "centerId", required = false) String centerId
//            , @SessionAttribute SessionVO sessionVO) {
//
//		// 전달된 센터가 없을 경우 로그인 사용자 센터 지정 처리
//		if (StringUtils.isEmpty(centerId)) {
//			// TODO::사용자 센터 정보 변경
//			centerId = sessionVO.getCenterId();
//		}
//
//		return patientDashboardService.selectPatientDashboardList(centerId);
//	}
}
