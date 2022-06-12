package kr.co.hconnect.rest;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.RestControllerExceptionHandler;
import kr.co.hconnect.common.TokenType;
import kr.co.hconnect.controller.UserLoginController;
import kr.co.hconnect.jwt.TokenProvider;
import kr.co.hconnect.service.TokenHistoryService;
import kr.co.hconnect.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/config/context-*.xml", "/test-context-servlet.xml" })
@Transactional
public class UserLoginControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginControllerTest.class);

    private static MockMvc mvc;

    /**
     * 사용자 서비스
     */
    @Autowired
    private UserService userService;

    /**
     * JWT 토큰 관리
     */
    @Autowired
    private TokenProvider tokenProvider;

    /**
     * 토큰 발급 이력 서비스
     */
    @Autowired
    private TokenHistoryService tokenHistoryService;

    @Before
    public void setMockMvc() {
        mvc = MockMvcBuilders.standaloneSetup(new UserLoginController(userService, tokenProvider, tokenHistoryService))
                .setControllerAdvice(new RestControllerExceptionHandler())
                .build();
    }

    /**
     * 로그인 성공여부 테스트
     */
    @Test
    public void checkLogin_ThenSuccess() throws Exception {
        String userLoginInfoVO =
                "{\n" +
                "  \"loginId\": \"admin\",\n" +
                "  \"password\": \"adminpass12#$\",\n" +
                "  \"rememberYn\": \"N\"\n" +
                "}";

        mvc.perform(post("/api/userLogin")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(userLoginInfoVO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                .andDo(print());
    }

    /**
     * 로그인 실패 테스트-사용자 정보 없음
     */
    @Test
    public void checkLogin_ThenNotFoundUserInfo() throws Exception {
        String userLoginInfoVO =
                "{\n" +
                        "  \"loginId\": \"notFoundUser\",\n" +
                        "  \"password\": \"adminpass12#$\",\n" +
                        "  \"rememberYn\": \"N\"\n" +
                        "}";

        mvc.perform(post("/api/userLogin")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(userLoginInfoVO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.NOT_FOUND_USER_INFO.getCode())))
                .andDo(print());
    }

    /**
     * 로그인 실패 테스트-사용자 ID null
     */
    @Test
    public void checkLogin_ThenInvalidId() throws Exception {
        String userLoginInfoVO =
                "{\n" +
                        "  \"loginId\": \"\",\n" +
                        "  \"password\": \"adminpass12#$\",\n" +
                        "  \"rememberYn\": \"N\"\n" +
                        "}";

        mvc.perform(post("/api/userLogin")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(userLoginInfoVO))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode())))
                .andDo(print());
    }

    /**
     * 토큰 상태 확인-정상
     */
    @Test
    public void checkTokenStatus_thenSuccess() throws Exception {
        String token = tokenProvider.createToken(TokenType.WEB, "admin", "admin");
        String tokenStatusInfo =
                "{\n" +
                "   \"token\":\"" + token + "\"" +
                "}";


        mvc.perform(post("/api/tokenStatus")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(tokenStatusInfo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenStatus", is(ApiResponseCode.SUCCESS.getCode())))
                .andDo(print());
    }

    /**
     * 토큰 상태 확인-유효하지 않은 상태
     */
    @Test
    public void checkTokenStatus_thenIllegal() throws Exception {
        String tokenStatusInfo =
                "{\n" +
                "   \"token\":\"123123123.123123123.123123123\"" +
                "}";


        mvc.perform(post("/api/tokenStatus")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(tokenStatusInfo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenStatus", is(ApiResponseCode.ILLEGAL_TOKEN.getCode())))
                .andDo(print());
    }

}
