package kr.co.hconnect.rest;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.jwt.TokenProvider;
import kr.co.hconnect.service.PatientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/config/context-*.xml", "/test-context-servlet.xml" })
@Transactional
public class LoginRestControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRestControllerTest.class);

    private static MockMvc mvc;

    /**
     * 환자관리 서비스
     */
    @Autowired
    private PatientService patientService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private DataSource dataSource;

    @Before
    public void setMockMvc() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("/sql-script/beforeSetLoginRestControllerTest.sql"));
        resourceDatabasePopulator.execute(dataSource);

        mvc = MockMvcBuilders.standaloneSetup(new LoginRestController(patientService, tokenProvider, messageSource)).build();
    }

    /**
     * 로그인 성공 여부 테스트
     */
    @Test
    public void givenLoginInfo_whenLoginTest_ThenSuccess() throws Exception {
        String content =
            "{\n" +
            "    \"loginId\": \"testshy\",\n" +
            "    \"password\": \"1234\"\n" +
            "}";

        mvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
            .andDo(print());


        // // Given
        // HttpUriRequest request = new HttpGet( "https://api.github.com/users/eugenp" );
        //
        // // When
        // HttpResponse response = HttpClientBuilder.create().build().execute( request );
        //
        // // Then
        // GitHubUser resource = RetrieveUtil.retrieveResourceFromResponse(
        //     response, GitHubUser.class);
        // assertThat( "eugenp", Matchers.is( resource.getLogin() ) );
    }

    /**
     * 로그인 실패 테스트 - 패스워드 틀림
     */
    @Test
    public void givenLoginInfo_whenLogin_ThenNotMatchPassword() throws Exception {
        String content =
            "{\n" +
                "    \"loginId\": \"testshy\",\n" +
                "    \"password\": \"testshy1321\"\n" +
                "}";

        mvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.NOT_MATCH_PATIENT_PASSWORD.getCode())))
            .andDo(print());
    }

    /**
     * 로그인 실패 테스트 - 계정정보 없는 경우
     */
    @Test
    public void givenLoginInfo_whenLogin_ThenNotFoundAccount() throws Exception {
        String content =
            "{\n" +
            "    \"loginId\": \"qpaldjfief\",\n" +
            "    \"password\": \"1234\"\n" +
            "}";

        mvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.NOT_FOUND_PATIENT_INFO.getCode())))
            .andDo(print());
    }

    /**
     * 본인인증 테스트 - 본인 데이터 존재
     */
    @Test
    public void givenIdentityInfo_whenIdentity_thenSuccess() throws Exception {
        String content =
            "{\n" +
            "  \"patientNm\": \"shy-unittest\",\n" +
            "  \"birthDate\": \"19881205\",\n" +
            "  \"sex\": \"M\",\n" +
            "  \"cellPhone\": \"01092615960\"\n" +
            "}";

        mvc.perform(post("/api/identity")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
            .andExpect(jsonPath("$.quarantineDiv", is("1")))
            .andExpect(jsonPath("$.registerYn", is("Y")))
            .andDo(print());
    }

    /**
     * 본인인증 테스트 - 본인 데이터 미존재
     */
    @Test
    public void givenIdentityInfo_whenIdentity_thenSuccessNotFound() throws Exception {
        String content =
            "{\n" +
            "  \"patientNm\": \"shy-unittest1111111\",\n" +
            "  \"birthDate\": \"19881205\",\n" +
            "  \"sex\": \"M\",\n" +
            "  \"cellPhone\": \"01092615960\"\n" +
            "}";

        mvc.perform(post("/api/identity")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
            .andExpect(jsonPath("$.quarantineDiv", is("0")))
            .andExpect(jsonPath("$.registerYn", is("N")))
            .andDo(print());
    }

    /**
     * 본인인증 테스트 - 실패케이스 다중입소내역 존재
     */
    @Test
    public void givenIdentityInfo_whenIdentity_thenFailMultiAdmission() throws Exception {
        String content =
            "{\n" +
            "  \"patientNm\": \"shy-unittest2\",\n" +
            "  \"birthDate\": \"19881205\",\n" +
            "  \"sex\": \"M\",\n" +
            "  \"cellPhone\": \"01092619999\"\n" +
            "}";

        mvc.perform(post("/api/identity")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.DUPLICATE_ACTIVE_ADMISSION_INFO.getCode())))
            .andExpect(jsonPath("$.quarantineDiv", is("0")))
            .andExpect(jsonPath("$.registerYn", is("N")))
            .andDo(print());
    }

}
