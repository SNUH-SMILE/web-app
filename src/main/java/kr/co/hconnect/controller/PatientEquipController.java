package kr.co.hconnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import kr.co.hconnect.service.AdmissionService;
import kr.co.hconnect.service.PatientEquipService;
import kr.co.hconnect.vo.AdmissionInfoVO;
import kr.co.hconnect.vo.AdmissionVO;
import kr.co.hconnect.vo.EquipmentVO;
import kr.co.hconnect.vo.PatientEquipListVO;
import kr.co.hconnect.vo.PatientEquipSaveVO;
import kr.co.hconnect.vo.PatientEquipVO;
import kr.co.hconnect.vo.SessionVO;

@Controller
@RequestMapping("/patientEquip")
public class PatientEquipController {

	/**
	 * 환자별 장비 관리 서비스
	 */
	private final PatientEquipService service;
	
	/**
	 * 입소내역 관리 서비스
	 */
	private final AdmissionService admissionService;
	
	/**
	 * 생성자
	 * @param service 환자별 장비 관리 서비스
	 * @param admissionService 입소내역 관리 서비스
	 */
	@Autowired
	public PatientEquipController(PatientEquipService service, AdmissionService admissionService) {
		this.service = service;
		this.admissionService = admissionService;
	}
	
	/**
	 * 환자별 사용장비 화면 호출
	 */
	@RequestMapping(value = "/patientEquip.do")
	public String callPatientEquip() {
		return "admission/patientEquip";
	}
	
	/**
	 * 환자별 사용장비 리스트 조회
	 * @return List<PatientEquipListVO>-환자별 사용장비 리스트
	 */
	@RequestMapping(value = "/patientEquipList.ajax")
	@ResponseBody
	public List<PatientEquipListVO> selectPatientEquipInfoList() {
		return service.selectPatientEquipList();
	}
	
	/**
	 * 입소내역에 따른 환자 장비정보 조회
	 * @param vo AdmissionVO
	 * @return
	 */
	@RequestMapping(value = "/patientEquipInfoList.ajax")
	public ModelAndView selectPatientEquipInfoList(@ModelAttribute AdmissionVO vo) {
		// 입소내역 정보
		AdmissionInfoVO admissionInfoVO = admissionService.selectAdmissionInfo(vo.getAdmissionId());
		// 환자 장비매핑 정보
		List<PatientEquipVO> patientEquipVOs = service.selectPatientEquipInfoList(vo.getAdmissionId());
		// 장비리스트(센터기준)
		List<EquipmentVO> equipmentVOs = service.selectEquipmentListByCenterIdList(vo.getAdmissionId());
			
		ModelAndView mv = new ModelAndView("jsonView");
		mv.addObject("admissionInfoVO", admissionInfoVO);
		mv.addObject("patientEquipVOs", patientEquipVOs);
		mv.addObject("equipmentVOs", equipmentVOs);
		
		return mv;
	}
	
	/**
	 * 환자별 장비 내역 저장
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/save.ajax")
	public ModelAndView savePatientEquip(@ModelAttribute PatientEquipSaveVO vo
			, @SessionAttribute SessionVO sessionVO) {
		// selectPatientEquipInfoList 조회를 위한 VO 설정
		AdmissionVO admissionVO = new AdmissionVO();
		admissionVO.setAdmissionId(vo.getAdmissionId());
		admissionVO.setCenterId(vo.getCenterId());
		
		// 사용자 정보 추가
		for (PatientEquipVO patientEquipVO : vo.getPatientEquipVOs()) {
			patientEquipVO.setRegId(sessionVO.getUserId());
			patientEquipVO.setUpdId(sessionVO.getUserId());
		}
		
		// 환자별 사용장비 저장
		service.savePatientEquipment(vo);
		
		// 환자별 장비정보 리스트 조회
		List<PatientEquipListVO> patientEquipListVOs = this.selectPatientEquipInfoList();
		
		ModelAndView mv = this.selectPatientEquipInfoList(admissionVO);
		mv.addObject("patientEquipListVOs", patientEquipListVOs);
		
		
		return mv;
	}
	
}
