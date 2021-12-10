package kr.co.hconnect.rest;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.RestControllerExceptionHandler;
import kr.co.hconnect.service.PatientService;
import kr.co.hconnect.service.QantnStatusService;
import org.hamcrest.core.IsNull;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 환자관리 RestController Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/config/context-*.xml", "/test-context-servlet.xml" })
@Transactional
public class PatientRestControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientRestControllerTest.class);

    private static MockMvc mvc;

    /**
     * 환자 관리 Service
     */
    @Autowired
    private PatientService patientService;

    /**
     * 격리상태 관리 Service
     */
    @Autowired
    private QantnStatusService qantnStatusService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MessageSource messageSource;


    @Before
    public void setUp() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("/sql-script/beforeSetPatientRestControllerTest.sql"));
        resourceDatabasePopulator.execute(dataSource);

        mvc = MockMvcBuilders.standaloneSetup(new PatientRestController(patientService, qantnStatusService, messageSource))
            .setControllerAdvice(new RestControllerExceptionHandler())
            .build();
    }

    /**
     * 회원정보 조회 성공
     */
    @Test
    public void givenLoginId_whenPatient_thenSuccess() throws Exception {
        String content =
            "{\n" +
            "  \"loginId\": \"testshy\"\n" +
            "}";

        mvc.perform(get("/api/patient")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
            .andDo(print());
    }

    /**
     * 회원정보 조회 실패 회원정보 없음
     */
    @Test
    public void givenLoginId_whenPatient_thenNotFoundPatient() throws Exception {
        String content =
            "{\n" +
            "  \"loginId\": \"testshy5252\"\n" +
            "}";

        mvc.perform(get("/api/patient")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.NOT_FOUND_PATIENT_INFO.getCode())))
            .andDo(print());
    }

    /**
     * 환자정보 저장 성공
     */
    @Test
    public void givenPatient_whenCreatePatient_thenSuccess() throws Exception {
        String content =
            "{\n" +
            "  \"loginId\"   : \"junitTestPatient\",\n" +
            "  \"password\"  : \"1234\",\n" +
            "  \"patientNm\" : \"회원가입용\",\n" +
            "  \"ssn\"       : \"8812051525252\",\n" +
            "  \"birthDate\" : \"19881205\",\n" +
            "  \"sex\"       : \"M\",\n" +
            "  \"cellPhone\" : \"01012345678\",\n" +
            "  \"guardianCellPhone\" : \"01012345678\",\n" +
            "  \"zipCode\"   : \"12345\",\n" +
            "  \"address1\"  : \"서울시 성북구 종암동\",\n" +
            "  \"address2\"  : \"301호\"\n" +
            "}";

        mvc.perform(post("/api/patient")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
            .andDo(print());
    }

    /**
     * 환자정보 수정 성공
     */
    @Test
    public void givenPatient_whenUpdatePatient_thenSuccess() throws Exception {
        String content =
            "{\n" +
            "  \"loginId\"   : \"testshy\",\n" +
            "  \"cellPhone\" : \"01012345678\",\n" +
            "  \"guardianCellPhone\" : \"01087654321\",\n" +
            "  \"zipCode\"   : \"12345\",\n" +
            "  \"address1\"  : \"서울시 성북구 종암동\",\n" +
            "  \"address2\"  : \"301호\"\n" +
            "}";

        mvc.perform(put("/api/patient")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
            .andDo(print());
    }

    /**
     * 환자정보 수정 - 환자정보가 존재하지 않을 경우
     */
    @Test
    public void givenPatient_whenUpdatePatient_thenNotFoundPatientInfo() throws Exception {
        String content =
            "{\n" +
                "  \"loginId\"   : \"블라블라\",\n" +
                "  \"cellPhone\" : \"01012345678\",\n" +
                "  \"guardianCellPhone\" : \"01087654321\",\n" +
                "  \"zipCode\"   : \"12345\",\n" +
                "  \"address1\"  : \"서울시 성북구 종암동\",\n" +
                "  \"address2\"  : \"301호\"\n" +
                "}";

        mvc.perform(put("/api/patient")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.NOT_FOUND_PATIENT_INFO.getCode())))
            .andDo(print());
    }

    /**
     * 로그인ID 중복 케이스
     */
    @Test
    public void givenLoginId_whenLoginIdDuplicate_thenDuplicate() throws Exception {
        String content =
            "{\n" +
            "  \"loginId\": \"testshy\"\n" +
            "}";

        mvc.perform(get("/api/patient/duplicate")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
            .andExpect(jsonPath("$.dupYn", is("Y")))
            .andDo(print());
    }

    /**
     * 로그인ID 미중복 케이스
     */
    @Test
    public void givenLoginId_whenLoginIdDuplicate_thenNotDuplicate() throws Exception {
        String content =
            "{\n" +
            "  \"loginId\": \"블라블라\"\n" +
            "}";

        mvc.perform(get("/api/patient/duplicate")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
            .andExpect(jsonPath("$.dupYn", is("N")))
            .andDo(print());
    }

    /**
     * 환자 비밀번호 수정 성공
     */
    @Test
    public void givenLoginInfo_whenChangePassword_thenSuccess() throws Exception {
        String content =
            "{\n" +
            "  \"loginId\": \"testshy\",\n" +
            "  \"password\": \"5678\"\n" +
            "}";

        mvc.perform(put("/api/patient/password")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
            .andDo(print());
    }

    /**
     * 환자 비밀번호 수정 실패-환자정보 없음
     */
    @Test
    public void givenLoginInfo_whenChangePassword_thenNotFoundPatientInfo() throws Exception {
        String content =
            "{\n" +
            "  \"loginId\": \"블라블라\",\n" +
            "  \"password\": \"5678\"\n" +
            "}";

        mvc.perform(put("/api/patient/password")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.NOT_FOUND_PATIENT_INFO.getCode())))
            .andDo(print());
    }

    /**
     * 로그인ID 찾기 - 성공
     */
    @Test
    public void givenSearchLoginInfo_whenSelectPatient_thenSuccess() throws Exception {
        String content =
            "{\n" +
            "  \"patientNm\": \"shy-unittest\",\n" +
            "  \"cellPhone\": \"01092615960\"\n" +
            "}";

        mvc.perform(get("/api/patients/findById")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
            .andExpect(jsonPath("$.loginId", is("testshy")))
            .andDo(print());
    }

    /**
     * 로그인ID 찾기 - 실패 환자정보 없음
     */
    @Test
    public void givenSearchLoginInfo_whenSelectPatient_thenNotFoundPatientInfo() throws Exception {
        String content =
            "{\n" +
            "  \"patientNm\": \"블라블라\",\n" +
            "  \"cellPhone\": \"01092615960\"\n" +
            "}";

        mvc.perform(get("/api/patients/findById")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.NOT_FOUND_PATIENT_INFO.getCode())))
            .andExpect(jsonPath("$.message", is("환자정보가 존재하지 않습니다")))
            .andDo(print());
    }

    /**
     * 로그인ID 찾기 - 동일한 환자정보 존재
     */
    @Test
    public void givenSearchLoginInfo_whenSelectPatient_thenDuplicatePatientInfo() throws Exception {
        String content =
            "{\n" +
            "  \"patientNm\": \"shy-DUP\",\n" +
            "  \"cellPhone\": \"01092615960\"\n" +
            "}";

        mvc.perform(get("/api/patients/findById")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.DUPLICATE_PATIENT_INFO.getCode())))
            .andExpect(jsonPath("$.message", is("동일한 환자정보가 존재합니다")))
            .andDo(print());
    }

    /**
     * 개인정보 존재여부 확인 - 존재
     */
    @Test
    public void givenSearchExistLoginInfo_whenCheckExistLoginInfo_thenExistsLoginInfo() throws Exception {
        String content =
            "{\n" +
            "  \"loginId\": \"testshy\",\n" +
            "  \"patientNm\": \"shy-unittest\",\n" +
            "  \"cellPhone\": \"01092615960\"\n" +
            "}";

        mvc.perform(get("/api/patients/find")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
            .andExpect(jsonPath("$.existYn", is("Y")))
            .andDo(print());
    }

    /**
     * 개인정보 존재여부 확인 - 없음
     */
    @Test
    public void givenSearchExistLoginInfo_whenCheckExistLoginInfo_thenNotExistsLoginInfo() throws Exception {
        String content =
            "{\n" +
            "  \"loginId\": \"testshy\",\n" +
            "  \"patientNm\": \"shy-unittest\",\n" +
            "  \"cellPhone\": \"01092610000\"\n" +
            "}";

        mvc.perform(get("/api/patients/find")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
            .andExpect(jsonPath("$.existYn", is("N")))
            .andDo(print());
    }

    /**
     * 격리 상태 조회 - 정상조회
     */
    @Test
    public void givenLoginId_whenSelectQuarantine_thenSuccess() throws Exception {
        String content =
                "{\n" +
                "  \"loginId\": \"testshy\"\n" +
                "}";

        mvc.perform(get("/api/quarantineStatus")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
            .andExpect(jsonPath("$.quarantineStatusDiv", is("1")))
            .andDo(print());
    }

    /**
     * 격리 상태 조회 - 격리상태 데이터 없음
     */
    @Test
    public void givenLoginId_whenSelectQuarantine_thenNotFoundQuarantineStatus() throws Exception {
        String content =
                "{\n" +
                "  \"loginId\": \"testshydup1\"\n" +
                "}";

        mvc.perform(get("/api/quarantineStatus")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.NOT_FOUND_QUARANTINE_INFO.getCode())))
            .andExpect(jsonPath("$.quarantineStatusDiv", is(IsNull.nullValue())))
            .andDo(print());
    }

    /**
     * 격리상태 저장 성공
     */
    @Test
    public void givenSaveQuarantineStatusInfo_whenSaveQuarantine_thenSuccess() throws Exception {
        String content =
                "{\n" +
                "  \"loginId\": \"testshy\",\n" +
                "  \"quarantineStatusDiv\": \"0\"\n" +
                "}";

        mvc.perform(post("/api/quarantineStatus")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
            .andDo(print());
    }

    /**
     * 격리상태 저장 실패 - 입소내역 없음
     */
    @Test
    public void givenSaveQuarantineStatusInfo_whenSaveQuarantine_thenNotFoundAdmissionInfo() throws Exception {
        String content =
            "{\n" +
                "  \"loginId\": \"testshydup2\",\n" +
                "  \"quarantineStatusDiv\": \"0\"\n" +
                "}";

        mvc.perform(post("/api/quarantineStatus")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.NOT_FOUND_ADMISSION_INFO.getCode())))
            .andDo(print());
    }
}