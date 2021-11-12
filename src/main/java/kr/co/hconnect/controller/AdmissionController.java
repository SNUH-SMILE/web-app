package kr.co.hconnect.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.hconnect.service.AdmissionService;
import kr.co.hconnect.service.ComCdManagerService;
import kr.co.hconnect.service.TreatmentCenterService;
import kr.co.hconnect.vo.AdmissionInfoVO;
import kr.co.hconnect.vo.AdmissionListVO;
import kr.co.hconnect.vo.AdmissionVO;
import kr.co.hconnect.vo.ComCdDetailListVO;
import kr.co.hconnect.vo.PatientVO;
import kr.co.hconnect.vo.SessionVO;
import kr.co.hconnect.vo.TreatmentCenterVO;

/**
 * 입소내역 관리 컨트롤러
 */
@Controller
@RequestMapping("/admission")
public class AdmissionController {
	
	/**
	 * 입소내역 서비스
	 */
	private final AdmissionService service;
	
	/**
	 * 생활치료센터 서비스
	 */
	private final TreatmentCenterService serviceTreatmentCenter;
	
	/**
	 * 공통코드관리 서비스
	 */
	private final ComCdManagerService serviceComCd;
	
	/**
	 * 생성자
	 * @param service 입소내역관리 서비스
	 * @param serviceTreatmentCenter 생활치료센터 서비스
	 * @param serviceComCd 공통코드 관리 서비스
	 */
	@Autowired
	public AdmissionController(AdmissionService service
			, TreatmentCenterService serviceTreatmentCenter
			, ComCdManagerService serviceComCd) {
		this.service = service;
		this.serviceTreatmentCenter = serviceTreatmentCenter;
		this.serviceComCd = serviceComCd;
	}

	/**
	 * 입소내역 정보 관리 화면 호출
	 */
	@RequestMapping(value="/admission.do", method = RequestMethod.GET)
	public String callAdmission() {
		return "admission/admission";
	}
	
	/**
	 * 생활치료센터 리스트 조회
	 * @return List<TreatmentCenterVO>-생활치료센터 리스트
	 */
	@RequestMapping(value = "/treatmentCenterList.ajax")
	@ResponseBody
	public List<TreatmentCenterVO> selectTreatmentCenterList() {
		return serviceTreatmentCenter.selectTreatmentCenterList();
	}
	
	/**
	 * 위치 정보 리스트 조회
	 * @param vo 공통코드상세 리스트  VO
	 * @return List<ComCdDetailListVO> 위치 정보 리스트
	 */
	@RequestMapping(value = "/locationList.ajax")
	@ResponseBody
	public List<ComCdDetailListVO> selectLocationList(@RequestParam(value = "centerId") String centerId) {
		ComCdDetailListVO vo = new ComCdDetailListVO();
		// TODO::공통코드 확정 후 enum변경
		vo.setComCd("ROOM");
		vo.setUseYn("Y");
		vo.setProperty1(centerId);
		
		return serviceComCd.selectComCdDetailList(vo);
	}
	
	/**
	 * 입소내역 리스트 조회
	 * @return List<AdmissionListVO> 입소내역 리스트
	 */
	@RequestMapping(value = "/list.ajax")
	@ResponseBody
	public List<AdmissionListVO> selectAdmissionList() {
		return service.selectAdmissionList();
	}
	
	/**
	 * 입소내역 정보 조회
	 * @param admissionId 입소내역ID
	 * @return ModelAndView(AdmissionInfoVO-입소내역 정보, List<ComCdDetailListVO>-위치정보) 입소내역 정보
	 */
	@RequestMapping(value = "/info.ajax")
	public ModelAndView selectAdmissionInfo(@RequestParam(value="admissionId") String admissionId) {
		// 입소내역 정보
		AdmissionInfoVO admissionInfoVO = service.selectAdmissionInfo(admissionId);
		
		// 위치정보
		List<ComCdDetailListVO> cdDetailListVO = null;
		// 저장된 위치정보가 존재 한 경우 위치 리스트 정보 조회
		if (admissionInfoVO.getLocation() != null && !admissionInfoVO.getLocation().isEmpty()) {
			cdDetailListVO = selectLocationList(admissionInfoVO.getCenterId());
		}
		
		ModelAndView mv = new ModelAndView("jsonView");
		mv.addObject("admissionInfoVO", admissionInfoVO);
		mv.addObject("locationVO", cdDetailListVO);
		
		return mv;
	}
	
	/**
	 * 입소내역 저장
	 * @param patientVO	환자VO
	 * @param admissionVO 입소내역VO
	 * @return ModelAndView(AdmissionInfoVO-입소내역 정보, List<ComCdDetailListVO>-위치정보) 입소내역 정보
	 * @throws FdlException
	 */
	@RequestMapping(value = "/save.ajax", method = RequestMethod.POST)
	public ModelAndView saveAdmission(@ModelAttribute PatientVO patientVO
									, @ModelAttribute AdmissionVO admissionVO
									, @SessionAttribute SessionVO sessionVO) throws FdlException {
		
		patientVO.setRegId(sessionVO.getUserId());
		patientVO.setUpdId(sessionVO.getUserId());
		admissionVO.setRegId(sessionVO.getUserId());
		admissionVO.setUpdId(sessionVO.getUserId());
		
		String admissionId = service.saveAdmission(patientVO, admissionVO);
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		// 입소내역 정보 조회
		if (!StringUtils.isEmpty(admissionId)) {
			List<AdmissionListVO> admissionListVO = this.selectAdmissionList();
			ModelAndView admissionInfo = this.selectAdmissionInfo(admissionId);
			
			mv.addObject("admissionListVO", admissionListVO);
			mv.addObject("admissionInfoVO", admissionInfo.getModel().get("admissionInfoVO"));
			mv.addObject("locationVO", admissionInfo.getModel().get("locationVO"));
		} 
		
		return mv;
	}
	
	/**
	 * 퇴실처리
	 * @param admissionVO
	 * @return List<AdmissionListVO> 입소내역 리스트
	 */
	@RequestMapping(value = "/discharge.ajax", method = RequestMethod.POST)
	@ResponseBody
	public List<AdmissionListVO> updateAdmissionDischarge(@ModelAttribute AdmissionVO admissionVO) {
		admissionVO.setUpdId("dev");
		
		service.updateAdmissionDischarge(admissionVO);
		
		return this.selectAdmissionList();
	}
	
}
