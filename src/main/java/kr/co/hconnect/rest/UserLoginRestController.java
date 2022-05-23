package kr.co.hconnect.rest;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.domain.UserLoginInfo;
import kr.co.hconnect.domain.UserLoginResponse;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.exception.NotFoundUserInfoException;
import kr.co.hconnect.exception.NotMatchPatientPasswordException;
import kr.co.hconnect.jwt.TokenProvider;
import kr.co.hconnect.service.UserService;
import kr.co.hconnect.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserLoginRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginRestController.class);

    private final TokenProvider tokenProvider;

    /**
     * 사용자 서비스
     */
    private final UserService userService;

    /**
     * 생성자
     * @param tokenProvider Token 관리
     * @param userService 사용자 서비스
     */
    @Autowired
    public UserLoginRestController(TokenProvider tokenProvider, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    /**
     * 로그인 정보 확인
     */
    @RequestMapping(value="/userLogin", method = RequestMethod.POST)
    public UserLoginResponse checkLogin(@Valid @RequestBody UserLoginInfo userLoginInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        UserLoginResponse userLoginResponse = new UserLoginResponse();

        try {
            // 로그인 정보 조회
            UserVO userVO = userService.selectLoginInfo(userLoginInfo);

            // 사용자 로그인 정보 업데이트
            userVO.setRememberYn(userLoginInfo.getRememberYn());
            userService.updateUserLoginInfo(userVO);

            // Token 발행
            String token = tokenProvider.createUserToken(userVO);

            userLoginResponse.setCode(ApiResponseCode.SUCCESS.getCode());
            userLoginResponse.setMessage("로그인 성공");
            userLoginResponse.setToken(token);
        } catch (NotFoundUserInfoException e) {
            userLoginResponse.setCode(ApiResponseCode.NOT_FOUND_USER_INFO.getCode());
            userLoginResponse.setMessage(e.getMessage());
        } catch (NotMatchPatientPasswordException e) {
            userLoginResponse.setCode(ApiResponseCode.NOT_MATCH_PATIENT_PASSWORD.getCode());
            userLoginResponse.setMessage(e.getMessage());
        }

        return userLoginResponse;
    }

}
