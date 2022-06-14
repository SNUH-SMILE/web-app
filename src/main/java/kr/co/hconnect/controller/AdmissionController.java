package kr.co.hconnect.controller;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.VoValidationGroups;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.jwt.TokenDetailInfo;
import kr.co.hconnect.service.AdmissionService;
import kr.co.hconnect.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
	 * 입소내역 정보 조회
	 * @param admissionId-입소내역ID
	 * @return AdmissionInfoVO-입소내역 정보
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public ResponseVO<AdmissionInfoVO> selectAdmissionInfo(@RequestParam String admissionId) {
		AdmissionInfoVO admissionInfoVO = admissionService.selectAdmissionInfo(admissionId);

		ResponseVO<AdmissionInfoVO> responseVO = new ResponseVO<>();

		responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
		responseVO.setMessage("입소내역 조회 성공");
		responseVO.setResult(admissionInfoVO);

		return responseVO;
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

	/**
	 * 생활치료센터 입소자 등록
	 *
	 * @param vo 입소자 등록 정보
	 * @return ResponseVO&lt;AdmissionSaveResponseByCenterVO&gt; 입소자 등록 완료 정보
	 */
	@RequestMapping(value = "/save", method = RequestMethod.PUT)
	public ResponseVO<AdmissionSaveResponseByCenterVO> insertAdmissionByCenter(
			@Validated(VoValidationGroups.add.class) @RequestBody AdmissionSaveByCenterVO vo
			, BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {
		if (bindingResult.hasErrors()) {
			throw new InvalidRequestArgumentException(bindingResult);
		}

		return saveAdmissionByCenter(vo, tokenDetailInfo, true);
	}

	/**
	 * 생활치료센터 입소자 수정
	 *
	 * @param vo 입소자 수정 정보
	 * @return ResponseVO&lt;AdmissionSaveResponseByCenterVO&gt; 입소자 수정 완료 정보
	 */
	@RequestMapping(value = "/save", method = RequestMethod.PATCH)
	public ResponseVO<AdmissionSaveResponseByCenterVO> updateAdmissionByCenter(
			@Validated(VoValidationGroups.modify.class) @RequestBody AdmissionSaveByCenterVO vo
			, BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {
		if (bindingResult.hasErrors()) {
			throw new InvalidRequestArgumentException(bindingResult);
		}

		return saveAdmissionByCenter(vo, tokenDetailInfo, false);
	}

	/**
	 * 생활치료센터 입소자 등록/수정
	 *
	 * @param vo 입소자 등록/수정 정보
	 * @param tokenDetailInfo 토큰상세 정보
	 * @param isNew true: 등록, false: 수정
	 * @return ResponseVO&lt;AdmissionSaveResponseByCenterVO&gt; 입소자 등록/수정 완료 정보
	 */
	private ResponseVO<AdmissionSaveResponseByCenterVO> saveAdmissionByCenter(AdmissionSaveByCenterVO vo
			, TokenDetailInfo tokenDetailInfo, boolean isNew) {
		ResponseVO<AdmissionSaveResponseByCenterVO> responseVO = new ResponseVO<>();

		// 시작일, 종료예정일 확인
		if (vo.getAdmissionDate().compareTo(vo.getDschgeSchdldDate()) > 0) {
			responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
			responseVO.setMessage("종료 예정일이 시작일 이전입니다.\r\n확인 후 다시 진행하세요.");

			return responseVO;
		}

		vo.setRegId(tokenDetailInfo.getId());
		vo.setUpdId(tokenDetailInfo.getId());

		try {
			String admissionId = admissionService.saveAdmissionByCenter(vo, isNew);

			AdmissionSaveResponseByCenterVO responseByCenterVO = new AdmissionSaveResponseByCenterVO();
			responseByCenterVO.setAdmissionId(admissionId);
			responseByCenterVO.setAdmissionListResponseByCenterVO(admissionService.selectAdmissionListByCenter(vo.getAdmissionListSearchByCenterVO()));

			responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
			responseVO.setMessage(String.format("생활치료센터 입소자 %s 완료", isNew ? "등록" : "수정"));
			responseVO.setResult(responseByCenterVO);
		} catch (FdlException e) {
			responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
			responseVO.setMessage(e.getMessage());
		}

		return responseVO;
	}
}
