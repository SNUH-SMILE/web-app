package kr.co.hconnect.controller;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.service.PatientDashboardService;
import kr.co.hconnect.vo.PatientStatusDashboardDetailSearchVO;
import kr.co.hconnect.vo.PatientStatusDashboardVO;
import kr.co.hconnect.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
	 * MessageSource
	 */
	private final MessageSource messageSource;

	/**
	 * 생성자
	 *
	 * @param patientDashboardService 환자 대시보드 Service
	 * @param messageSource MessageSource
	 */
	@Autowired
	public PatientDashboardController(PatientDashboardService patientDashboardService, MessageSource messageSource) {
		this.patientDashboardService = patientDashboardService;
		this.messageSource = messageSource;
	}

	/**
	 * 환자 대시보드 현황 조회
	 *
	 * @param vo 환자 현황 대시보드 환자정보 조회조건
	 * @return ResponseVO&lt;PatientStatusDashboardVO&gt; 환자 대시보드 현황 결과
	 */
	@RequestMapping(value = "/status/list", method = RequestMethod.POST)
	public ResponseVO<PatientStatusDashboardVO> selectPatientStatusDashboard(
			@Valid @RequestBody PatientStatusDashboardDetailSearchVO vo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new InvalidRequestArgumentException(bindingResult);
		}

		ResponseVO<PatientStatusDashboardVO> patientStatusDashboardVOResponseVO = new ResponseVO<>();
		patientStatusDashboardVOResponseVO.setCode(ApiResponseCode.SUCCESS.getCode());
		patientStatusDashboardVOResponseVO.setMessage("조회 성공");
		patientStatusDashboardVOResponseVO.setResult(patientDashboardService.selectPatientStatusDashboardDetailList(vo));

		return patientStatusDashboardVOResponseVO;
	}
}
