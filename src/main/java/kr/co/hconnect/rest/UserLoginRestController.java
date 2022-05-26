package kr.co.hconnect.rest;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.TokenStatus;
import kr.co.hconnect.common.TokenType;
import kr.co.hconnect.domain.LoginSuccessInfo;
import kr.co.hconnect.domain.TokenHistory;
import kr.co.hconnect.domain.TokenStatusInfo;
import kr.co.hconnect.domain.UserLoginInfo;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.exception.NotFoundUserInfoException;
import kr.co.hconnect.exception.NotMatchPatientPasswordException;
import kr.co.hconnect.jwt.TokenDetailInfo;
import kr.co.hconnect.jwt.TokenProvider;
import kr.co.hconnect.service.TokenHistoryService;
import kr.co.hconnect.service.UserService;
import kr.co.hconnect.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserLoginRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginRestController.class);

    /**
     * 사용자 서비스
     */
    private final UserService userService;

    /**
     * JWT 토큰 관리
     */
    private final TokenProvider tokenProvider;

    /**
     * 토큰 발급 이력 서비스
     */
    private final TokenHistoryService tokenHistoryService;

    /**
     * 생성자
     * @param userService 사용자 서비스
     * @param tokenProvider JWT 토큰 관리
     * @param tokenHistoryService 토큰 발급 이력 서비스
     */
    @Autowired
    public UserLoginRestController(UserService userService, TokenProvider tokenProvider, TokenHistoryService tokenHistoryService) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.tokenHistoryService = tokenHistoryService;
    }

    /**
     * 로그인 정보 확인
     */
    @RequestMapping(value="/userLogin", method = RequestMethod.POST)
    public LoginSuccessInfo checkLogin(@Valid @RequestBody UserLoginInfo userLoginInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        LoginSuccessInfo loginSuccessInfo = new LoginSuccessInfo();

        try {
            // 로그인 정보 조회
            UserVO userVO = userService.selectLoginInfo(userLoginInfo);

            // 사용자 로그인 정보 업데이트
            userVO.setRememberYn(userLoginInfo.getRememberYn());
            userService.updateUserLoginInfo(userVO);

            // Token 발행
            String token = tokenProvider.createToken(TokenType.WEB, userVO.getUserId(), userVO.getUserNm());

            loginSuccessInfo.setCode(ApiResponseCode.SUCCESS.getCode());
            loginSuccessInfo.setMessage("로그인 성공");
            loginSuccessInfo.setToken(token);
        } catch (NotFoundUserInfoException e) {
            loginSuccessInfo.setCode(ApiResponseCode.NOT_FOUND_USER_INFO.getCode());
            loginSuccessInfo.setMessage(e.getMessage());
        } catch (NotMatchPatientPasswordException e) {
            loginSuccessInfo.setCode(ApiResponseCode.NOT_MATCH_PATIENT_PASSWORD.getCode());
            loginSuccessInfo.setMessage(e.getMessage());
        }

        return loginSuccessInfo;
    }

    /**
     * 토큰 상태 확인
     *
     * @param tokenInfo 토큰 정보
     * @return TokenStatusInfo 토큰 정보
     */
    @RequestMapping(value = "/tokenStatus", method = RequestMethod.GET)
    public TokenStatusInfo checkTokenStatus(@Valid @RequestBody TokenStatusInfo tokenInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        TokenDetailInfo tokenDetailInfo = tokenProvider.validateToken(tokenInfo.getToken());
        tokenInfo.setRememberYn("N");

        // 토큰 상태 정보
        switch (tokenDetailInfo.getTokenStatus()) {
            case OK:
                tokenInfo.setTokenStatus(ApiResponseCode.SUCCESS.getCode());
                break;
            case EXPIRED:
                tokenInfo.setTokenStatus(ApiResponseCode.EXPIRED_TOKEN.getCode());
                break;
            case ILLEGAL:
                tokenInfo.setTokenStatus(ApiResponseCode.ILLEGAL_TOKEN.getCode());
                break;
            case INVALID:
                tokenInfo.setTokenStatus(ApiResponseCode.INVALID_TOKEN.getCode());
                break;
        }

        // 로그인 유지여부 확인
        if (tokenDetailInfo.getTokenStatus().equals(TokenStatus.OK) || tokenDetailInfo.getTokenStatus().equals(TokenStatus.EXPIRED)) {
            String userId = tokenDetailInfo.getId();

            if (StringUtils.isEmpty(userId)) {
                TokenHistory tokenHistory = tokenHistoryService.selectTokenHistory(tokenInfo.getToken());

                if (tokenHistory != null) {
                    userId = tokenHistory.getId();
                }
            }

            if (StringUtils.isEmpty(userId)) {
                UserVO userVO = userService.selectUserInfo(userId);

                if (userVO != null) {
                    tokenInfo.setRememberYn(userVO.getRememberYn());
                }
            }
        }

        return tokenInfo;
    }

    /**
     * 토큰 재발급
     * @param tokenInfo 기존 토큰 정보
     * @return 신규 토큰 정보
     */
    @RequestMapping(value = "/tokenReissue", method = RequestMethod.POST)
    public LoginSuccessInfo tokenReissue (@Valid @RequestBody TokenStatusInfo tokenInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        String token = tokenProvider.createToken(tokenInfo.getToken(), TokenType.WEB);

        LoginSuccessInfo loginSuccessInfo = new LoginSuccessInfo();
        if (!StringUtils.isEmpty(token)) {
            loginSuccessInfo.setCode(ApiResponseCode.SUCCESS.getCode());
            loginSuccessInfo.setMessage("신규 토큰 발급 성공");
            loginSuccessInfo.setToken(token);
        } else {
            loginSuccessInfo.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            loginSuccessInfo.setMessage("토큰 발급 실패");
        }

        return loginSuccessInfo;
    }

}
