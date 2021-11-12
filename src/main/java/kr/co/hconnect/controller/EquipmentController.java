package kr.co.hconnect.controller;
/**
 * 장비페이지
 */
import kr.co.hconnect.service.EquipmentService;
import kr.co.hconnect.service.TreatmentCenterService;
import kr.co.hconnect.vo.EquipmentVO;
import kr.co.hconnect.vo.SessionVO;
import kr.co.hconnect.vo.TreatmentCenterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {

    private EquipmentService equipmentService;    //장비 서비스
    private TreatmentCenterService treatmentCenterService;  //생활치료센터 서비스
    /**
     * 생성자
     * @param equipmentService 장비 서비스
     * @param treatmentCenterService 생활치료센터 서비스
     */
    @Autowired
    public EquipmentController(EquipmentService equipmentService, TreatmentCenterService treatmentCenterService) {
        this.equipmentService = equipmentService;
        this.treatmentCenterService = treatmentCenterService;
    }

    /**
     * 장비페이지호출
     * @return 장비 페이지
     */
    @RequestMapping("/home.do")
    public String equipmentHome(){
        return "sys/equipment";
    }

    /**
     * 장비 & 생활치료센터 리스트 조회
     * @return 장비목록, 생활치료센터 목록
     */
    @RequestMapping("/list.ajax")
    public ModelAndView selectEquipmentList(){
        ModelAndView mav = new ModelAndView("jsonView");

        //장비 List
        List<EquipmentVO> resultList = equipmentService.selectEquipmentList();
        mav.addObject("resultList",resultList);

        //생활치료 센터 모달 List
        List<TreatmentCenterVO> modalResultList = treatmentCenterService.selectTreatmentCenterList();
        mav.addObject("modalResultList",modalResultList);

        return mav;
    }

    /**
     * 장비 저장(입력 & 수정)
     * @param vo 장비VO
     * @return 장비목록
     */
    @RequestMapping("save.ajax")
    @ResponseBody
    public List<EquipmentVO> saveEquipment(@RequestBody EquipmentVO vo, @SessionAttribute SessionVO sessionVO) {

        vo.setRegId(sessionVO.getUserId());
        vo.setUpdId(sessionVO.getUserId());
        //# 입력&수정
        equipmentService.saveEquipment(vo);
        //#조회
        return equipmentService.selectEquipmentList();
    }

    /**
     * 신규 장비 생성시 아이디 중복 체크
     * @param equipmentId 장비Id
     * @return 아이디가 있으면 1 없으면 0
     */
    @RequestMapping("duplicateCheck.ajax")
    @ResponseBody
    public int duplicateCheck(@RequestParam(name="equipmentId")String equipmentId){

        return equipmentService.duplicateCheckEquipment(equipmentId);
    }

    /**
     * 장비 삭제
     * @param equipId 장비Id
     * @return 장비목록
     */
    @RequestMapping("delete.ajax")
    @ResponseBody
    public List<EquipmentVO> deleteEquipment(@RequestParam(name = "equipId",required = false)String equipId){
        //#삭제
        equipmentService.deleteEquipment(equipId);
        //#조회
        return equipmentService.selectEquipmentList();
    }
}
