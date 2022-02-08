package kr.co.hconnect.controller;

import kr.co.hconnect.service.PatientDetailDashboardService;
import kr.co.hconnect.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 환자 상세 대쉬보드 컨트롤러
 */
@Controller
@RequestMapping("/patientDetailDashboard")
public class PatientDetailDashboardController {

	/**
	 * Slf4j Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PatientDetailDashboardController.class);

	/**
	 * 환자 상세 대쉬보드 서비스
	 */
	public final PatientDetailDashboardService service;

	/**
	 * 생성자
	 * @param patientDetailDashboardService - 환자상세 대시보드 서비스
	 */
	@Autowired
	public PatientDetailDashboardController(PatientDetailDashboardService patientDetailDashboardService) {
		this.service = patientDetailDashboardService;
	}
	
	/**
	 * 환자 상세 대쉬보드 화면 호출
	 * @param admissionId - 입소ID
	 * @param model - model (입소ID 담을 Model)
	 * @return 환자상세 대시보드 뷰
	 */
	@RequestMapping(value = "/patientHome.do", method = RequestMethod.GET)
	public String selectPatientInfo(@RequestParam(value = "admissionId", required = false) String admissionId
			, @RequestParam(value = "centerId", required = false) String centerId
            , @SessionAttribute SessionVO sessionVO
			, Model model) {
		LOGGER.info("ADMISSION ID = {}", admissionId);

		// 전달된 센터가 없을 경우 로그인 사용자 센터 지정 처리
		if (StringUtils.isEmpty(centerId)) {
			// TODO::사용자 센터 정보 변경
			centerId = sessionVO.getCenterId();
		}

		List<ItemVO> centerItemListVO = service.selectCenterItemListVO();

		model.addAttribute("admissionId", admissionId);
		model.addAttribute("centerItemListVO", centerItemListVO);

		return "/dashboard/patientDetailDashboard";
	}

	/**
	 * 환자 상세정보 (최신 측정결과 V.I 및 환자 내용)
	 * @param admissionId - 입소ID
	 * @return PatientDetailDashboardPatientInfoVO - 환자상세정보 VO
	 */
	@RequestMapping(value = "/patientDetails.ajax", method = RequestMethod.GET)
	public ResponseEntity<?> selectPatientDetails(
			@RequestParam(value = "admissionId") String admissionId){
		LOGGER.info("ADMISSION ID = {}", admissionId);

		if(StringUtils.isEmpty(admissionId)){
			return ResponseEntity.badRequest()
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body("입소ID는 필수입니다. 입소ID와 함께 다시 요청해주세요.");
		}

		PatientDetailDashboardPatientInfoVO patientDetailDashboardPatientInfoVO = service.selectPatientDetailDashboardPatientInfo(admissionId);

		return ResponseEntity.ok(patientDetailDashboardPatientInfoVO);
	}

	/**
	 * 환자상세 대시보드 초기 로딩 시 전체 데이터 조회 및 제공
	 * @param admissionId - 입소 ID
	 * @return ResponseEntity<?> - 입소ID 없으면 BAD_REQUEST response 리턴
	 *                             ELSE Response OK + 환자상세대시보드 화면에 필요한 모든 데이터 VO 전달
	 */
	@RequestMapping(value = "/patientDashboardInfo.ajax")
	public ResponseEntity<?> selectPatientDashboardInfo(@RequestParam String admissionId){

		PatientDashboardInfoVO patientDashboardInfoVO;
		try {
			patientDashboardInfoVO = service.selectAllPatientDetailDashboardInfo(admissionId);
		}catch (RuntimeException e){
			LOGGER.error(e.getMessage());
			return ResponseEntity.badRequest()
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(e.getMessage());
		}

		return ResponseEntity.ok(patientDashboardInfoVO);
	}

	/**
	 *  환자 측정결과 리스트 조회
	 * @param patientDetailDashboardResultSearchVO -  측정결과 조회 vo
	 * @return ResponseEntity<?> - 응답에러메세지 또는 환자 측정결과 리스트
	 */
	@RequestMapping(value = "/patientDetailResultList.ajax", method = RequestMethod.GET)
	public ResponseEntity<?> selectPatientResultList(
			@ModelAttribute PatientDetailDashboardResultSearchVO patientDetailDashboardResultSearchVO){

		if(StringUtils.isEmpty(patientDetailDashboardResultSearchVO.getAdmissionId())){
			LOGGER.error("입소ID 검색조건이 NULL입니다.");
			return ResponseEntity.badRequest()
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body("입소ID는 필수값입니다.");
		}
		if(patientDetailDashboardResultSearchVO.getResultFromDt() == null){
			LOGGER.error("resultFromDt 검색조건 값이 NULL입니다.");
			return ResponseEntity.badRequest()
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body("검색조건의 START DATE는 필수값입니다.");
		}
		if(patientDetailDashboardResultSearchVO.getResultToDt() == null){
			LOGGER.error("resultToDt 검색조건 값이 NULL입니다.");
			return ResponseEntity.badRequest()
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body("검색조건의 END DATE는 필수값입니다.");
		}

		List<PatientDetailDashboardResultVO> patientDetailDashboardResultVOList
				= service.selectPatientDetailDashboardResultList(patientDetailDashboardResultSearchVO);

		return ResponseEntity.ok(patientDetailDashboardResultVOList);
	}
	
}
