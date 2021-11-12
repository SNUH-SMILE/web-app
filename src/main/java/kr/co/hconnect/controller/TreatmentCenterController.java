package kr.co.hconnect.controller;


import kr.co.hconnect.common.ComCd;
import kr.co.hconnect.service.ComCdManagerService;
import kr.co.hconnect.service.TreatmentCenterService;
import kr.co.hconnect.vo.ComCdDetailListVO;
import kr.co.hconnect.vo.SessionVO;
import kr.co.hconnect.vo.TreatmentCenterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 생활치료센터
 */
@Controller
@RequestMapping("/treatmentCenter")
public class TreatmentCenterController {

    private final TreatmentCenterService treatmentCenterService;       // 생활치료센터 서비스
    private final ComCdManagerService comCdService;     // 공통코드 서비스

    /**
     * 생성자
     * @param treatmentCenterService 생활치료센터 서비스
     * @param comCdService 공통코드 서비스
     */
    @Autowired
    public TreatmentCenterController(TreatmentCenterService treatmentCenterService, ComCdManagerService comCdService) {
        this.treatmentCenterService = treatmentCenterService;
        this.comCdService = comCdService;
    }
    
    // C: 입력, insert + 메서드, add +
    // R: 조회, select + 메서드, get +
    // U: 수정, update + 메서드, modify +
    // D: 삭제, delete + 메서드, remove +
    // C+U: 저장, save + 메서드

    /**
     * 생활치료센터 페이지 호출
     * @param model
     * @return 생활치료센터 페이지, 병원목록
     */
    @RequestMapping("/home.do")
    public String treatmentCenterControllerHome(Model model){
        //# 병원목록 조회
        List<ComCdDetailListVO> comCdDetailListVOs = comCdService.selectComCdDetailList(ComCd.HOSPITAL_CD);
        model.addAttribute("voComCdList",comCdDetailListVOs);
        return "sys/treatmentCenter";
    }

    /**
     * 생활치료센터 리스트 검색
     * @return 생활치료센터 목록
     */
    @RequestMapping("/list.ajax")
    @ResponseBody
    public List<TreatmentCenterVO> selectTreatmentCenterList() {
        //# 1.
        // ModelAndView mav = new ModelAndView("jsonView");
        // List<TreatmentCenterVO> resultList = service.selectTreatmentCenterList();
        // mav.addObject("resultList",resultList);
        // return mav;

        //# 2.
        return treatmentCenterService.selectTreatmentCenterList();
    }

    /**
     * 생활치료센터 입력
     * @param vo 생활치료센터VO
     * @return 생활치료센터 목록
     */
    @RequestMapping(value = "/insert.ajax")
    @ResponseBody
    public List<TreatmentCenterVO> insertTreatmentCenter(@RequestBody TreatmentCenterVO vo
        , @SessionAttribute SessionVO sessionVO) {

        vo.setRegId(sessionVO.getUserId());

        //# 입력
        treatmentCenterService.insertTreatmentCenter(vo);
        //# 조회
        return treatmentCenterService.selectTreatmentCenterList();
    }

    /**
     * 생활치료센터 수정
     * @param vo 생활치료센터VO
     * @return 생활치료센터 목록
     */
    @RequestMapping(value = "/update.ajax")
    @ResponseBody
    public List<TreatmentCenterVO> updateTreatmentCenter(@RequestBody TreatmentCenterVO vo
        , @SessionAttribute SessionVO sessionVO) {

        vo.setUpdId(sessionVO.getUserId());

        //# 수정
        treatmentCenterService.updateTreatmentCenter(vo);

        //# 목록조회
        return treatmentCenterService.selectTreatmentCenterList();
    }

    /**
     *  생활치료센터 삭제
     * @param centerId 생활치료센터 ID
     * @return 생활치료센터 목록
     */
    @RequestMapping(value = "/delete.ajax")
    @ResponseBody
    public List<TreatmentCenterVO> deleteTreatmentCenter(@RequestParam(name="centerId") String centerId){
        //# 삭제
        treatmentCenterService.deleteTreatmentCenter(centerId);
        //# 목록조회
        return treatmentCenterService.selectTreatmentCenterList();
    }

}
