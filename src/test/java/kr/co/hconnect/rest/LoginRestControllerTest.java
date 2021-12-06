package kr.co.hconnect.rest;

import kr.co.hconnect.service.PatientService;
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

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/config/context-*.xml", "/test-context-servlet.xml" })

public class LoginRestControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRestControllerTest.class);

    private static MockMvc mvc;

    /**
     * 로그인 URL
     */
    private static final String loginURL = "/api/login";

    /**
     * 본인인증 URL
     */
    private static final String identityURL = "/api/identity";

    /**
     * 환자관리 서비스
     */
    @Autowired
    private PatientService patientService;

    @Before
    public void setMockMvc() {
        mvc = MockMvcBuilders.standaloneSetup(new LoginRestController(patientService)).build();
    }

    // -- >> Test Script <<
    // -- 정상 데이터
    // -- PATIENT_ID : PTESTSHY99
    // -- LOGIN_ID   : testshy
    // -- PASSWORD   : 1234
    // -- SSN        : 8812051999999
    //
    // INSERT
    // INTO PATIENT ( PATIENT_ID, LOGIN_ID, PASSWORD, PATIENT_NM, SSN
    //     , BIRTH_DATE, SEX, CELL_PHONE, ZIP_CODE, ADDRESS1
    //     , ADDRESS2)
    // VALUES ( 'PTESTSHY99', 'testshy', 'bOFW4fVzdPuNJp89%2FeNG%2FA%3D%3D', 'shy-unittest', 'OQdb%2FlUq81M781GbkM8Vng%3D%3D'
    //              , '1988-12-05', 'M', '01092615960', '111111', '서울시', '헬스커넥트');
    //
    // insert
    // into admission ( ADMISSION_ID, PATIENT_ID, ADMISSION_DATE, DSCHGE_SCHDLD_DATE, DSCHGE_DATE
    //     , QANTN_DIV, PERSON_CHARGE, CENTER_ID, ROOM, REG_ID
    //     , UPD_ID)
    // values ( 'TESTSHY999', 'PTESTSHY99', NOW(), '9999-12-31', null
    //     , '1', 'JUNIT_TEST', 'C999', '01', 'JUNIT'
    //     , 'JUNIT');
    //
    // -- 비정상 데이터 - 다중입소내역
    // -- PATIENT_ID : PTESTSHY98
    // -- LOGIN_ID   : testshy2
    // -- PASSWORD   : 1234
    // -- SSN        : 8812051555555
    //
    // INSERT
    // INTO PATIENT ( PATIENT_ID, LOGIN_ID, PASSWORD, PATIENT_NM, SSN
    //     , BIRTH_DATE, SEX, CELL_PHONE, ZIP_CODE, ADDRESS1
    //     , ADDRESS2)
    // VALUES ( 'PTESTSHY98', 'testshy2', 'bOFW4fVzdPuNJp89%2FeNG%2FA%3D%3D', 'shy-unittest2', 'Q4nqavZTIDVui%2FdhI%2Bafcg%3D%3D'
    //              , '1988-12-05', 'M', '01092619999', '111111', '서울시', '헬스커넥트');
    //
    // insert
    // into admission ( ADMISSION_ID, PATIENT_ID, ADMISSION_DATE, DSCHGE_SCHDLD_DATE, DSCHGE_DATE
    //     , QANTN_DIV, PERSON_CHARGE, CENTER_ID, ROOM, REG_ID
    //     , UPD_ID)
    // values ( 'TESTSHY997', 'PTESTSHY98', NOW(), '9999-12-31', null
    //     , '1', 'JUNIT_TEST', 'C999', '01', 'JUNIT'
    //     , 'JUNIT');
    //
    // insert
    // into admission ( ADMISSION_ID, PATIENT_ID, ADMISSION_DATE, DSCHGE_SCHDLD_DATE, DSCHGE_DATE
    //     , QANTN_DIV, PERSON_CHARGE, CENTER_ID, ROOM, REG_ID
    //     , UPD_ID)
    // values ( 'TESTSHY998', 'PTESTSHY98', NOW(), '9999-12-31', null
    //     , '2', 'JUNIT_TEST', 'C999', '01', 'JUNIT'
    //     , 'JUNIT');
    //
    //
    // DELETE
    // FROM PATIENT
    // WHERE PATIENT_ID = 'PTESTSHY99';
    //
    // DELETE
    // FROM admission
    // WHERE ADMISSION_ID = 'TESTSHY999';
    //
    // DELETE
    // FROM PATIENT
    // WHERE PATIENT_ID = 'PTESTSHY98';
    //
    // DELETE
    // FROM admission
    // WHERE ADMISSION_ID = 'TESTSHY997';
    //
    // DELETE
    // FROM admission
    // WHERE ADMISSION_ID = 'TESTSHY998';

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

        mvc.perform(get(loginURL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is("00")))
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

        mvc.perform(get(loginURL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is("99")))
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

        mvc.perform(get(loginURL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is("99")))
            .andDo(print());
    }

    /**
     * 본인인증 테스트 - 본인 데이터 존재
     */
    @Test
    public void givenIdentityInfo_whenIdentity_thenSuccess() throws Exception {
        String content =
            "{\n" +
            "  \"ssn\": \"8812051999999\"\n" +
            "}";

        mvc.perform(get(identityURL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is("00")))
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
                "  \"ssn\": \"8812059999999\"\n" +
                "}";

        mvc.perform(get(identityURL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is("00")))
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
                "  \"ssn\": \"8812051555555\"\n" +
                "}";

        mvc.perform(get(identityURL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is("99")))
            .andExpect(jsonPath("$.quarantineDiv", is("0")))
            .andExpect(jsonPath("$.registerYn", is("N")))
            .andDo(print());
    }

}
