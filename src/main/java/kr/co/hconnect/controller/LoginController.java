package kr.co.hconnect.controller;

import kr.co.hconnect.service.UserService;
import kr.co.hconnect.vo.LoginVO;
import kr.co.hconnect.vo.SessionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 로그인 컨트롤러
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	/**
	 * 사용자 서비스
	 */
	private final UserService service;

	/**
	 * 생성자
	 * @param service 사용자 서비스
	 */
	@Autowired
	public LoginController(UserService service) {
		this.service = service;
	}

	/**
	 * 로그인 페이지 호출
	 */
	@RequestMapping(value="/login.do")
	public String callLogin() {
		return "login";
	}
	
	/**
	 * 로그인 정보 확인
	 */
	@RequestMapping(value="/checkLogin.ajax")
	public ModelAndView checkLogin(@RequestParam(value = "userId") String userId
		, @RequestParam(value = "password") String password
		, HttpServletRequest request) {

		// 로그인 정보 조회
		LoginVO loginVO = service.selectLoginInfo(userId, password);
		
		// 로그인 사용자 정보 세션에 설정
		if (loginVO.getUserVO() != null) {
			SessionVO sessionVO = new SessionVO();
			sessionVO.setUserId(loginVO.getUserVO().getUserId());
			sessionVO.setUserNm(loginVO.getUserVO().getUserNm());
			sessionVO.setCenterId(loginVO.getUserVO().getCenterId());
			request.getSession().setAttribute("sessionVO", sessionVO);
		}
		
		ModelAndView mv = new ModelAndView("jsonView");
		mv.addObject("loginFailMessage", loginVO.getFailMessage());	// 로그인 실패 사유
		return mv;
	}

	/**
	 * 로그아웃 실행
	 */
	@RequestMapping(value = "/logout.do")
	public String doLogout(HttpServletRequest request) {
		// 세션 초기화 및 로그인 페이지 표시
		request.getSession().invalidate();
		return "login";
	}

}
