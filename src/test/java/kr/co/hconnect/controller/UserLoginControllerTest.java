package kr.co.hconnect.controller;

import com.jayway.jsonpath.JsonPath;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.jwt.JwtFilter;
import kr.co.hconnect.jwt.TokenProvider;
import kr.co.hconnect.service.TokenHistoryService;
import kr.co.hconnect.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Autowired
    private JwtFilter jwtFilter;

    private String testToken;
    public String getTestToken() {
        return testToken;
    }

    public void setTestToken(String testToken) {
        this.testToken = testToken;
    }

    @Before
    public void setMockMvc() throws Exception {
        mvc = MockMvcBuilders
                .standaloneSetup(new UserLoginController(userService, tokenProvider, tokenHistoryService))
                .addFilters(jwtFilter)
                .build();

        // 테스트용 토큰 발급
        String data =
                "{\n" +
                        "  \"loginId\": \"admin\",\n" +
                        "  \"password\": \"adminpass12#$\",\n" +
                        "  \"rememberYn\": \"N\"\n" +
                        "}";

        MvcResult result = mvc.perform(post("/api/userLogin")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andReturn();

        // 발행된 토큰 정보 추출
        String response = result.getResponse().getContentAsString();
        setTestToken(JsonPath.parse(response).read("$.result"));
    }

    /**
     * 로그인 및 발급 토근 상태 정상 확인
     */
    @Test
    public void checkLoginAndCheckTokenStatus_thenSuccess() throws Exception {
        String data =
                "{\n" +
                "  \"loginId\": \"admin\",\n" +
                "  \"password\": \"adminpass12#$\",\n" +
                "  \"rememberYn\": \"N\"\n" +
                "}";
    
        // 1. 로그인 성공 확인
        MvcResult result = mvc.perform(post("/api/userLogin")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(data))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                        .andDo(print())
                        .andReturn();
        
        // 1-1. 발행된 토큰 정보 추출
        String response = result.getResponse().getContentAsString();
        String token = JsonPath.parse(response).read("$.result");
        
        String tokenData =
                "{\n" +
                "  \"token\": \"" + token + "\"\n" +
                "}";
        
        // 2. 토큰 상태 확인
        mvc.perform(post("/api/tokenStatus")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(tokenData))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenStatus", is("00")))
                .andDo(print());
    }

    /**
     * 로그인 - 비밀번호 불일치 확인
     */
    @Test
    public void checkLogin_NotMatchPassword() throws Exception {
        String data =
                "{\n" +
                        "  \"loginId\": \"admin\",\n" +
                        "  \"password\": \"password\",\n" +
                        "  \"rememberYn\": \"N\"\n" +
                        "}";

        mvc.perform(post("/api/userLogin")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.NOT_MATCH_PATIENT_PASSWORD.getCode())))
                .andDo(print())
                .andReturn();
    }

    /**
     * 로그인 - 사용자 정보가 없는 경우
     */
    @Test
    public void checkLogin_NotFoundUser() throws Exception {
        String data =
                "{\n" +
                        "  \"loginId\": \"loginId\",\n" +
                        "  \"password\": \"password\",\n" +
                        "  \"rememberYn\": \"N\"\n" +
                        "}";

        mvc.perform(post("/api/userLogin")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.NOT_FOUND_USER_INFO.getCode())))
                .andDo(print())
                .andReturn();
    }

    /**
     * 토큰 상태 확인 - 정상
     */
    @Test
    public void checkToken_thenSuccess() throws Exception {
        String tokenData =
                "{\n" +
                        "  \"token\":\"" + getTestToken() + "\"\n" +
                        "}";

        mvc.perform(post("/api/tokenStatus")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(tokenData))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenStatus", is(ApiResponseCode.SUCCESS.getCode())))
                .andDo(print());
    }

    /**
     * 토큰 상태 확인 - 유효하지 않은 토큰정보
     */
    @Test
    public void checkToken_statusIllegal() throws Exception {
        String tokenData =
                "{\n" +
                        "  \"token\": \"123\"\n" +
                        "}";

        mvc.perform(post("/api/tokenStatus")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(tokenData))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenStatus", is(ApiResponseCode.ILLEGAL_TOKEN.getCode())))
                .andDo(print());
    }

    /**
     * 토큰 재발급 - 정상
     */
    @Test
    public void tokenReissue_thenSuccess() throws Exception {
        String tokenData =
                "{\n" +
                        "  \"token\":\"" + getTestToken() + "\"\n" +
                        "}";

        MvcResult result = mvc.perform(post("/api/tokenReissue")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(tokenData))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                        .andDo(print())
                        .andReturn();

        // 발행된 토큰 정보 추출
        String response = result.getResponse().getContentAsString();
        setTestToken(JsonPath.parse(response).read("$.result"));
    }

    /**
     * 토큰 재발급 - 실패
     */
    @Test
    public void tokenReissue_fail() throws Exception {
        String tokenData =
                "{\n" +
                "  \"token\":\"123\"\n" +
                "}";

        mvc.perform(post("/api/tokenReissue")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(tokenData))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode())))
            .andDo(print());

    }

    /**
     * 로그아웃 - 정상처리
     */
    @Test
    public void logout_thenSuccess() throws Exception {
        // 테스트용 토큰 발급
        String data =
                "{\n" +
                        "  \"loginId\": \"test\",\n" +
                        "  \"password\": \"1234\",\n" +
                        "  \"rememberYn\": \"N\"\n" +
                        "}";

        MvcResult result = mvc.perform(post("/api/userLogin")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andReturn();

        // 발행된 토큰 정보 추출
        String response = result.getResponse().getContentAsString();
        String token = JsonPath.parse(response).read("$.result");

        mvc.perform(get("/api/userLogout")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                .andDo(print());
    }

}
