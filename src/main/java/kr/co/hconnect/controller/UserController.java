package kr.co.hconnect.controller;

import kr.co.hconnect.service.TreatmentCenterService;
import kr.co.hconnect.service.UserService;
import kr.co.hconnect.vo.SessionVO;
import kr.co.hconnect.vo.TreatmentCenterVO;
import kr.co.hconnect.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 유저
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;  //유저 서비스
    private final TreatmentCenterService treatmentCenterService; //생활치료센터 서비스

    /**
     * 생성자
     * @param UserService 유저서비스
     * @param treatmentCenterService 생활치료센터 서비스
     */
    @Autowired
    public UserController(UserService UserService, TreatmentCenterService treatmentCenterService) {
        this.userService = UserService;
        this.treatmentCenterService = treatmentCenterService;
    }

    /**
     * 유저페이지 호출
     * @return 유저 페이지
     */
    @RequestMapping("/home.do")
    public String userHome(){
        return "sys/user";
    }

    /**
     *유저 리스트 검색
     * @return 유저 목록
     */
    @RequestMapping("/list.ajax")
    public ModelAndView selectUserList(){
        ModelAndView mav = new ModelAndView("jsonView");
        //사용자 List
        List<UserVO> resultList = userService.selectUserList();
        mav.addObject("resultList",resultList);
        //생활치료 센터 List(모달)
        TreatmentCenterVO vo = new TreatmentCenterVO();
        List<TreatmentCenterVO> modalResultList = treatmentCenterService.selectTreatmentCenterList(vo);
        mav.addObject("modalResultList",modalResultList);
        return mav;
    }

    /**
     * 유저입력
     * @param vo 유저VO
     * @return 유저 목록
     */
    @RequestMapping("insert.ajax")
    @ResponseBody
    public List<UserVO> insertUser(@RequestBody UserVO vo, @SessionAttribute SessionVO sessionVO) {

        vo.setRegId(sessionVO.getUserId());
        //# 입력
        userService.insertUser(vo);
        //# 조회
        return userService.selectUserList();
    }

    /**
     * 유저 업데이트
     * @param vo 유저Vo
     * @return 유저 목록
     */
    @RequestMapping("update.ajax")
    @ResponseBody
    public List<UserVO> updateUser(@RequestBody UserVO vo, @SessionAttribute SessionVO sessionVO) {

        vo.setUpdId(sessionVO.getUserId());
        //# 수정
        userService.updateUser(vo);
        //# 조회
        return userService.selectUserList();
    }

    /**
     * 유저 삭제
     * @param userId 유저Id
     * @return 유저 목록
     */
    @RequestMapping("delete.ajax")
    @ResponseBody
    public List<UserVO> userDeleteAjax(@RequestParam(name = "userId",required = false)String userId){
        //# 삭제
        userService.deleteUser(userId);
        //# 조회
        return userService.selectUserList();
    }
}
