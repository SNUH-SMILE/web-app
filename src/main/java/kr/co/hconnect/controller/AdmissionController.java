package kr.co.hconnect.controller;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.service.AdmissionService;
import kr.co.hconnect.vo.AdmissionListResponseByCenterVO;
import kr.co.hconnect.vo.AdmissionListSearchByCenterVO;
import kr.co.hconnect.vo.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 생활치료센터/자가격리자 입소내역 관리 Controller
 */
@RestController
@RequestMapping("/api/admission")
public class AdmissionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdmissionController.class);

	/**
	 * 격리/입소내역 관리 Service
	 */
	private final AdmissionService admissionService;

	/**
	 * 생성자
	 *
	 * @param admissionService 입소내역 Service
	 */
	@Autowired
	public AdmissionController(AdmissionService admissionService) {
		this.admissionService = admissionService;
	}

	/**
	 * 생활치료센터 입소자 리스트 조회
	 * 
	 * @param vo 생활치료센터 입소자 리스트 조회조건
	 * @return ResponseVO&lt;AdmissionListResponseByCenterVO&gt; 생활치료센터 입소자 리스트 조회 결과
	 */
	@RequestMapping(value = "/list/center", method = RequestMethod.POST)
	public ResponseVO<AdmissionListResponseByCenterVO> selectAdmissionListByCenter(
			@Valid @RequestBody AdmissionListSearchByCenterVO vo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new InvalidRequestArgumentException(bindingResult);
		}

		ResponseVO<AdmissionListResponseByCenterVO> responseVO = new ResponseVO<>();
		responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
		responseVO.setMessage("조회 성공");
		responseVO.setResult(admissionService.selectAdmissionListByCenter(vo));

		return responseVO;
	}
	
}
