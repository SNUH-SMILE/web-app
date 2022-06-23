package kr.co.hconnect.controller;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.jwt.TokenDetailInfo;
import kr.co.hconnect.service.NoticeService;
import kr.co.hconnect.service.PatientDetailDashboardService;
import kr.co.hconnect.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 환자 상세 대쉬보드 Controller
 */
@RestController
@RequestMapping("/api/patientDashboard/detail")
public class PatientDetailDashboardController {

	/**
	 * Slf4j Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PatientDetailDashboardController.class);

	/**
	 * 환자 상세 대쉬보드 서비스
	 */
	public final PatientDetailDashboardService patientDetailDashboardService;
	/**
	 * 알림 서비스
	 */
	public final NoticeService noticeService;

	/**
	 * 생성자
	 *
	 * @param patientDetailDashboardService 환자상세 대시보드 서비스
	 * @param noticeService 알림 서비스
	 */
	@Autowired
	public PatientDetailDashboardController(PatientDetailDashboardService patientDetailDashboardService, NoticeService noticeService) {
		this.patientDetailDashboardService = patientDetailDashboardService;
		this.noticeService = noticeService;
	}

	/**
	 * 환자 상세 대시보드 정보 조회
	 *
	 * @param admissionId 격리/입소내역ID
	 * @return ResponseVO&lt;PatientDetailDashboardVO&gt; 환자 상세 대시보드 전체 내역
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseVO<PatientDetailDashboardVO> selectPatientDetailDashboard(@RequestParam String admissionId) {
		ResponseVO<PatientDetailDashboardVO> responseVO = new ResponseVO<>();
		
		try {
			PatientDetailDashboardVO patientDetailDashboardVO = patientDetailDashboardService.selectPatientDetailDashboard(admissionId);

			responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
			responseVO.setMessage("조회 완료");
			responseVO.setResult(patientDetailDashboardVO);
		} catch (RuntimeException e) {
			responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
			responseVO.setMessage("조회 실패");
		}

		return responseVO;
	}

	/**
	 * 신규 알림 내역 저장
	 *
	 * @param vo 알림 저장 정보
	 * @return ResponseVO&lt;List&lt;NoticeVO&gt;&gt; 알림 저장 완료 정보
	 */
	@RequestMapping(value = "/notice", method = RequestMethod.PUT)
	public ResponseVO<List<NoticeVO>> insertNotice(@Valid @RequestBody NoticeSaveVO vo, BindingResult bindingResult
			, @RequestAttribute TokenDetailInfo tokenDetailInfo) {
		if (bindingResult.hasErrors()) {
			throw new InvalidRequestArgumentException(bindingResult);
		}

		// 알림 저장 정보 바인딩
		NoticeVO noticeVO = new NoticeVO();
		noticeVO.setAdmissionId(vo.getAdmissionId());
		noticeVO.setNotice(vo.getNotice());
		noticeVO.setRegId(tokenDetailInfo.getId());

		// 알림 내역 저장
		noticeService.insertNotice(noticeVO);

		// 알림 리스트 조회
		NoticeListSearchVO noticeListSearchVO = new NoticeListSearchVO();
		noticeListSearchVO.setAdmissionId(vo.getAdmissionId());

		ResponseVO<List<NoticeVO>> responseVO = new ResponseVO<>();
		responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
		responseVO.setMessage("저장 성공");
		responseVO.setResult(noticeService.selectNoticeList(noticeListSearchVO));

		return responseVO;
	}

//	/**
//	 * 환자 상세정보 (최신 측정결과 V.I 및 환자 내용)
//	 * @param admissionId - 입소ID
//	 * @return PatientDetailDashboardPatientInfoVO - 환자상세정보 VO
//	 */
//	@RequestMapping(value = "/patientDetails.ajax", method = RequestMethod.GET)
//	public ResponseEntity<?> selectPatientDetails(
//			@RequestParam(value = "admissionId") String admissionId){
//		LOGGER.info("ADMISSION ID = {}", admissionId);
//
//		if(StringUtils.isEmpty(admissionId)){
//			return ResponseEntity.badRequest()
//					.contentType(MediaType.APPLICATION_JSON_UTF8)
//					.body("입소ID는 필수입니다. 입소ID와 함께 다시 요청해주세요.");
//		}
//
//		PatientDetailDashboardPatientInfoVO patientDetailDashboardPatientInfoVO = service.selectPatientDetailDashboardPatientInfo(admissionId);
//
//		return ResponseEntity.ok(patientDetailDashboardPatientInfoVO);
//	}
//
//	/**
//	 * 환자상세 대시보드 초기 로딩 시 전체 데이터 조회 및 제공
//	 * @param admissionId - 입소 ID
//	 * @return ResponseEntity<?> - 입소ID 없으면 BAD_REQUEST response 리턴
//	 *                             ELSE Response OK + 환자상세대시보드 화면에 필요한 모든 데이터 VO 전달
//	 */
//	@RequestMapping(value = "/patientDashboardInfo.ajax")
//	public ResponseEntity<?> selectPatientDashboardInfo(@RequestParam String admissionId){
//
//		PatientDashboardInfoVO patientDashboardInfoVO;
//		try {
//			patientDashboardInfoVO = service.selectAllPatientDetailDashboardInfo(admissionId);
//		}catch (RuntimeException e){
//			LOGGER.error(e.getMessage());
//			return ResponseEntity.badRequest()
//					.contentType(MediaType.APPLICATION_JSON_UTF8)
//					.body(e.getMessage());
//		}
//
//		return ResponseEntity.ok(patientDashboardInfoVO);
//	}
//
//	/**
//	 *  환자 측정결과 리스트 조회
//	 * @param patientDetailDashboardResultSearchVO -  측정결과 조회 vo
//	 * @return ResponseEntity<?> - 응답에러메세지 또는 환자 측정결과 리스트
//	 */
//	@RequestMapping(value = "/patientDetailResultList.ajax", method = RequestMethod.GET)
//	public ResponseEntity<?> selectPatientResultList(
//			@ModelAttribute PatientDetailDashboardResultSearchVO patientDetailDashboardResultSearchVO){
//
//		if(StringUtils.isEmpty(patientDetailDashboardResultSearchVO.getAdmissionId())){
//			LOGGER.error("입소ID 검색조건이 NULL입니다.");
//			return ResponseEntity.badRequest()
//					.contentType(MediaType.APPLICATION_JSON_UTF8)
//					.body("입소ID는 필수값입니다.");
//		}
//		if(patientDetailDashboardResultSearchVO.getResultFromDt() == null){
//			LOGGER.error("resultFromDt 검색조건 값이 NULL입니다.");
//			return ResponseEntity.badRequest()
//					.contentType(MediaType.APPLICATION_JSON_UTF8)
//					.body("검색조건의 START DATE는 필수값입니다.");
//		}
//		if(patientDetailDashboardResultSearchVO.getResultToDt() == null){
//			LOGGER.error("resultToDt 검색조건 값이 NULL입니다.");
//			return ResponseEntity.badRequest()
//					.contentType(MediaType.APPLICATION_JSON_UTF8)
//					.body("검색조건의 END DATE는 필수값입니다.");
//		}
//
//		List<PatientDetailDashboardResultVO> patientDetailDashboardResultVOList
//				= service.selectPatientDetailDashboardResultList(patientDetailDashboardResultSearchVO);
//
//		return ResponseEntity.ok(patientDetailDashboardResultVOList);
//	}
	
}
