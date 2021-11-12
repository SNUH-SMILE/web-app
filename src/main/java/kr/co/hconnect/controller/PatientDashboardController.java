package kr.co.hconnect.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.hconnect.service.PatientDashboardService;
import kr.co.hconnect.vo.PatientDashboardCenterInfoVO;
import kr.co.hconnect.vo.PatientDashboardVO;

/**
 * 환자 대쉬보드 컨트롤러
 */
@Controller
@RequestMapping("/patientDashboard")
public class PatientDashboardController {

	/**
	 * 환자 대쉬보드 Service
	 */
	private final PatientDashboardService service;
	
	@Autowired
	public PatientDashboardController(PatientDashboardService service) {
		this.service = service;
	}
	
	/**
	 * 환자 대쉬보드 화면 호출
	 * @param centerId - 센터ID
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/call.do")
	public String selectCenterInfo(@RequestParam(value = "centerId", required = false) String centerId, Model model) {
		
		// 전달된 센터가 없을 경우 로그인 사용자 센터 지정 처리
		if (StringUtils.isEmpty(centerId)) {
			// TODO::사용자 센터 정보 변경
			centerId = "C999";
		}
		
		PatientDashboardCenterInfoVO centerInfoVO = service.selectPatientDashboardCenterInfo(centerId);
		
		model.addAttribute("centerInfo", centerInfoVO);
		
		return "/dashboard/patientDashboard";
	}
	
	/**
	 * 환자 대쉬보드 리스트 조회
	 * @param centerId - 센터ID
	 * @return List<PatientDashboardVO> - 환자 대쉬보드 리스트
	 */
	@RequestMapping(value = "/list.ajax")
	@ResponseBody
	public List<PatientDashboardVO> selectPatientDashboardList(
			@RequestParam(value = "centerId", required = false) String centerId) {
		
		// 전달된 센터가 없을 경우 로그인 사용자 센터 지정 처리
		if (StringUtils.isEmpty(centerId)) {
			// TODO::사용자 센터 정보 변경
			centerId = "C999";
		}
		
		return service.selectPatientDashboardList(centerId);
	}
}
