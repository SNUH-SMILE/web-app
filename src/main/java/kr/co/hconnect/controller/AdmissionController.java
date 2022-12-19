package kr.co.hconnect.controller;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.QantnDiv;
import kr.co.hconnect.common.VoValidationGroups;
import kr.co.hconnect.domain.Interview;
import kr.co.hconnect.exception.*;
import kr.co.hconnect.jwt.TokenDetailInfo;
import kr.co.hconnect.service.AdmissionService;
import kr.co.hconnect.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
	@RequestMapping(value = "/center/list", method = RequestMethod.POST)
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
	@RequestMapping(value = "/center/save", method = RequestMethod.PUT)
	public ResponseVO<AdmissionSaveResponseByCenterVO> insertAdmissionByCenter(
			@Validated(VoValidationGroups.add.class) @RequestBody AdmissionSaveByCenterVO vo
			, BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {
		if (bindingResult.hasErrors()) {
			throw new InvalidRequestArgumentException(bindingResult);
		}

		AdmissionVO admissionVO = new AdmissionVO();
		BeanUtils.copyProperties(vo, admissionVO);
		admissionVO.setQantnDiv(QantnDiv.CENTER.getDbValue());
		admissionVO.setRegId(tokenDetailInfo.getId());
		admissionVO.setUpdId(tokenDetailInfo.getId());

		PatientIdentityVO patientIdentityVO = new PatientIdentityVO();
		BeanUtils.copyProperties(vo, patientIdentityVO);

		// 생활치료센터 입소처리
		ResponseVO<String> saveAdmission = saveAdmission(admissionVO, patientIdentityVO, true);

		// 반환정보 구성
		ResponseVO<AdmissionSaveResponseByCenterVO> responseVO = new ResponseVO<>();

		responseVO.setCode(saveAdmission.getCode());
		responseVO.setMessage(saveAdmission.getMessage());
		if (saveAdmission.getCode().equals(ApiResponseCode.SUCCESS.getCode())) {
			AdmissionSaveResponseByCenterVO responseByCenterVO = new AdmissionSaveResponseByCenterVO();
			responseByCenterVO.setAdmissionId(saveAdmission.getResult());
			responseByCenterVO.setAdmissionListResponseByCenterVO(admissionService.selectAdmissionListByCenter(vo.getAdmissionListSearchByCenterVO()));

			responseVO.setResult(responseByCenterVO);
		}

		return responseVO;
	}

	/**
	 * 생활치료센터 입소자 수정
	 *
	 * @param vo 입소자 수정 정보
	 * @return ResponseVO&lt;AdmissionSaveResponseByCenterVO&gt; 입소자 수정 완료 정보
	 */
	@RequestMapping(value = "/center/save", method = RequestMethod.PATCH)
	public ResponseVO<AdmissionSaveResponseByCenterVO> updateAdmissionByCenter(
			@Validated(VoValidationGroups.modify.class) @RequestBody AdmissionSaveByCenterVO vo
			, BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {
		if (bindingResult.hasErrors()) {
			throw new InvalidRequestArgumentException(bindingResult);
		}

		AdmissionVO admissionVO = new AdmissionVO();
		BeanUtils.copyProperties(vo, admissionVO);
		admissionVO.setQantnDiv(QantnDiv.CENTER.getDbValue());
		admissionVO.setRegId(tokenDetailInfo.getId());
		admissionVO.setUpdId(tokenDetailInfo.getId());

		PatientIdentityVO patientIdentityVO = new PatientIdentityVO();
		BeanUtils.copyProperties(vo, patientIdentityVO);

		// 생활치료센터 입소자 정보 수정
		ResponseVO<String> saveAdmission = saveAdmission(admissionVO, patientIdentityVO, false);

		// 반화데이터 구성
		ResponseVO<AdmissionSaveResponseByCenterVO> responseVO = new ResponseVO<>();

		responseVO.setCode(saveAdmission.getCode());
		responseVO.setMessage(saveAdmission.getMessage());
		if (saveAdmission.getCode().equals(ApiResponseCode.SUCCESS.getCode())) {
			AdmissionSaveResponseByCenterVO responseByCenterVO = new AdmissionSaveResponseByCenterVO();
			responseByCenterVO.setAdmissionId(saveAdmission.getResult());
			responseByCenterVO.setAdmissionListResponseByCenterVO(admissionService.selectAdmissionListByCenter(vo.getAdmissionListSearchByCenterVO()));

			responseVO.setResult(responseByCenterVO);
		}

		return responseVO;
	}

	/**
	 * 생활치료센터 입소자 등록/수정
	 *
	 * @param admissionVO 격리/입소내역 저장 정보
	 * @param patientIdentityVO 환자 저장 정보
	 * @param isNew true: 등록, false: 수정
	 * @return ResponseVO&lt;String&gt; 격리/입소자 저장 정보(result: admissionId)
	 */
	private ResponseVO<String> saveAdmission(AdmissionVO admissionVO, PatientIdentityVO patientIdentityVO, boolean isNew) {
		ResponseVO<String> responseVO = new ResponseVO<>();

		try {
			String admissionId = admissionService.saveAdmission(admissionVO, patientIdentityVO, isNew);

			responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
			responseVO.setMessage(String.format("%s 완료", isNew ? "등록" : "수정"));
			responseVO.setResult(admissionId);
		} catch (FdlException e) {
			responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
			responseVO.setMessage(e.getMessage());
		} catch (DuplicateActiveAdmissionException e) {
			responseVO.setCode(ApiResponseCode.DUPLICATE_ACTIVE_ADMISSION_INFO.getCode());
			responseVO.setMessage(e.getMessage());
		} catch (DuplicatePatientInfoException e) {
			responseVO.setCode(ApiResponseCode.DUPLICATE_PATIENT_INFO.getCode());
			responseVO.setMessage(e.getMessage());
		} catch (NotFoundPatientInfoException e) {
			responseVO.setCode(ApiResponseCode.NOT_FOUND_PATIENT_INFO.getCode());
			responseVO.setMessage(e.getMessage());
		} catch (NotFoundAdmissionInfoException e) {
			responseVO.setCode(e.getErrorCode());
			responseVO.setMessage(e.getMessage());
		} catch (InvalidAdmissionInfoException e) {
			responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
			responseVO.setMessage(e.getMessage());
		}

		return responseVO;
	}

	/**
	 * 생활치료센터 입소자 퇴소 처리
	 * @param vo 퇴소 처리 정보 VO
	 * @return ResponseVO&lt;AdmissionListResponseByCenterVO&gt; 생활치료센터 입소자 리스트 조회 결과
	 */
	@RequestMapping(value = "/center/discharge", method = RequestMethod.PATCH)
	public ResponseVO<AdmissionListResponseByCenterVO> updateAdmissionDischargeByCenter(
			@Valid @RequestBody AdmissionDischargeByCenterVO vo, BindingResult bindingResult
			, @RequestAttribute TokenDetailInfo tokenDetailInfo) {
		if (bindingResult.hasErrors()) {
			throw new InvalidRequestArgumentException(bindingResult);
		}

		ResponseVO<AdmissionListResponseByCenterVO> responseVO = new ResponseVO<>();

		// 퇴소 정보 구성
		AdmissionVO admissionVO = new AdmissionVO();
		admissionVO.setAdmissionId(vo.getAdmissionId());
		admissionVO.setDschgeDate(vo.getDschgeDate());
		admissionVO.setQantnDiv(QantnDiv.CENTER.getDbValue());
		admissionVO.setUpdId(tokenDetailInfo.getId());
        admissionVO.setQuantLocation(vo.getQuantLocation());

		try {
			// 입소자 퇴소 처리
			admissionService.updateAdmissionDischarge(admissionVO);

			responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
			responseVO.setMessage("퇴소 처리 완료");
			responseVO.setResult(admissionService.selectAdmissionListByCenter(vo.getAdmissionListSearchByCenterVO()));
		} catch (NotFoundAdmissionInfoException e) {
			responseVO.setCode(e.getErrorCode());
			responseVO.setMessage(e.getMessage());
		} catch (InvalidAdmissionInfoException e) {
			responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
			responseVO.setMessage(e.getMessage());
		}

		return responseVO;
	}


	/**
	 * 자가격리자 리스트 조회
	 *
	 * @param vo 자가겨리자 리스트 조회조건
	 * @return ResponseVO&lt;AdmissionListResponseByQuarantineVO&gt; 자가격리자 리스트 조회 결과
	 */
	@RequestMapping(value = "/quarantine/list", method = RequestMethod.POST)
	public ResponseVO<AdmissionListResponseByQuarantineVO> selectAdmissionListByQuarantine(
			@RequestBody AdmissionListSearchByQuarantineVO vo) {
		ResponseVO<AdmissionListResponseByQuarantineVO> responseVO = new ResponseVO<>();

		responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
		responseVO.setMessage("조회 성공");
		responseVO.setResult(admissionService.selectAdmissionListByQuarantine(vo));

		return responseVO;
	}

	/**
	 * 자가격리자 등록
	 *
	 * @param vo 자가격리자 등록 정보
	 * @return ResponseVO&lt;AdmissionSaveResponseByQuarantineVO&gt; 자가격리자 등록 완료 정보
	 */
	@RequestMapping(value = "/quarantine/save", method = RequestMethod.PUT)
	public ResponseVO<AdmissionSaveResponseByQuarantineVO> insertAdmissionByQuarantine(
			@Validated(VoValidationGroups.add.class) @RequestBody AdmissionSaveByQuarantineVO vo
			, BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {
		if (bindingResult.hasErrors()) {
			throw new InvalidRequestArgumentException(bindingResult);
		}

		AdmissionVO admissionVO = new AdmissionVO();
		BeanUtils.copyProperties(vo, admissionVO);
		admissionVO.setQantnDiv(QantnDiv.QUARANTINE.getDbValue());
		admissionVO.setRegId(tokenDetailInfo.getId());
		admissionVO.setUpdId(tokenDetailInfo.getId());

		PatientIdentityVO patientIdentityVO = new PatientIdentityVO();
		BeanUtils.copyProperties(vo, patientIdentityVO);

		// 자가격리자 등록 처리
		ResponseVO<String> saveAdmission = saveAdmission(admissionVO, patientIdentityVO, true);

		// 반환정보 구성
		ResponseVO<AdmissionSaveResponseByQuarantineVO> responseVO = new ResponseVO<>();

		responseVO.setCode(saveAdmission.getCode());
		responseVO.setMessage(saveAdmission.getMessage());
		if (saveAdmission.getCode().equals(ApiResponseCode.SUCCESS.getCode())) {
			AdmissionSaveResponseByQuarantineVO responseByQuarantineVO = new AdmissionSaveResponseByQuarantineVO();
			responseByQuarantineVO.setAdmissionId(saveAdmission.getResult());
			responseByQuarantineVO.setAdmissionListResponseByQuarantineVO(admissionService.selectAdmissionListByQuarantine(vo.getAdmissionListSearchByQuarantineVO()));

			responseVO.setResult(responseByQuarantineVO);
		}

		return responseVO;
	}

	/**
	 * 자가격리자 수정
	 *
	 * @param vo 자가격리자 수정 정보
	 * @return ResponseVO&lt;AdmissionSaveResponseByQuarantineVO&gt; 자가격리자 수정 완료 정보
	 */
	@RequestMapping(value = "/quarantine/save", method = RequestMethod.PATCH)
	public ResponseVO<AdmissionSaveResponseByQuarantineVO> updateAdmissionByQuarantine(
			@Validated(VoValidationGroups.add.class) @RequestBody AdmissionSaveByQuarantineVO vo
			, BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {
		if (bindingResult.hasErrors()) {
			throw new InvalidRequestArgumentException(bindingResult);
		}

		AdmissionVO admissionVO = new AdmissionVO();
		BeanUtils.copyProperties(vo, admissionVO);
		admissionVO.setQantnDiv(QantnDiv.QUARANTINE.getDbValue());
		admissionVO.setRegId(tokenDetailInfo.getId());
		admissionVO.setUpdId(tokenDetailInfo.getId());

		PatientIdentityVO patientIdentityVO = new PatientIdentityVO();
		BeanUtils.copyProperties(vo, patientIdentityVO);

		// 자가격리자 수정 처리
		ResponseVO<String> saveAdmission = saveAdmission(admissionVO, patientIdentityVO, false);

		// 반환정보 구성
		ResponseVO<AdmissionSaveResponseByQuarantineVO> responseVO = new ResponseVO<>();

		responseVO.setCode(saveAdmission.getCode());
		responseVO.setMessage(saveAdmission.getMessage());
		if (saveAdmission.getCode().equals(ApiResponseCode.SUCCESS.getCode())) {
			AdmissionSaveResponseByQuarantineVO responseByQuarantineVO = new AdmissionSaveResponseByQuarantineVO();
			responseByQuarantineVO.setAdmissionId(saveAdmission.getResult());
			responseByQuarantineVO.setAdmissionListResponseByQuarantineVO(admissionService.selectAdmissionListByQuarantine(vo.getAdmissionListSearchByQuarantineVO()));

			responseVO.setResult(responseByQuarantineVO);
		}

		return responseVO;
	}

	/**
	 * 자가격리자 퇴소 처리
	 * @param vo 퇴소 처리 정보 VO
	 * @return ResponseVO&lt;AdmissionListResponseByQuarantineVO&gt; 자가격리자 리스트 조회 결과
	 */
	@RequestMapping(value = "/quarantine/discharge", method = RequestMethod.PATCH)
	public ResponseVO<AdmissionListResponseByQuarantineVO> updateAdmissionDischargeByQuarantine(
			@Valid @RequestBody AdmissionDischargeByQuarantineVO vo, BindingResult bindingResult
			, @RequestAttribute TokenDetailInfo tokenDetailInfo) {
		if (bindingResult.hasErrors()) {
			throw new InvalidRequestArgumentException(bindingResult);
		}

		ResponseVO<AdmissionListResponseByQuarantineVO> responseVO = new ResponseVO<>();

		// 퇴소 정보 구성
		AdmissionVO admissionVO = new AdmissionVO();
		admissionVO.setAdmissionId(vo.getAdmissionId());
		admissionVO.setDschgeDate(vo.getDschgeDate());
		admissionVO.setQantnDiv(QantnDiv.QUARANTINE.getDbValue());
        admissionVO.setQuantLocation(vo.getQuantLocation());
		admissionVO.setUpdId(tokenDetailInfo.getId());

		try {
			// 자가격리자 퇴소 처리
			admissionService.updateAdmissionDischarge(admissionVO);

			responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
			responseVO.setMessage("퇴소 처리 완료");
			responseVO.setResult(admissionService.selectAdmissionListByQuarantine(vo.getAdmissionListSearchByQuarantineVO()));
		} catch (NotFoundAdmissionInfoException e) {
			responseVO.setCode(e.getErrorCode());
			responseVO.setMessage(e.getMessage());
		} catch (InvalidAdmissionInfoException e) {
			responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
			responseVO.setMessage(e.getMessage());
		}

		return responseVO;
	}
    /**
     *  문진 리스트 조회
     *
     * @return ResponseVO&lt;AdmissionListResponseByQuarantineVO&gt; 자가격리자 리스트 조회 결과
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ResponseVO<List<InterviewList>> test(
        @Valid @RequestBody Interview interview,BindingResult bindingResult
        , @RequestAttribute TokenDetailInfo tokenDetailInfo) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        ResponseVO< List<InterviewList>> responseVO = new ResponseVO<>();
        responseVO.setResult(admissionService.test(interview));
        
        return responseVO;
    }

}
