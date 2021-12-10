package kr.co.hconnect.rest;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.RestControllerExceptionHandler;
import kr.co.hconnect.service.AdmissionService;
import kr.co.hconnect.service.PatientDeviceService;
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
public class PatientDeviceRestControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientDeviceRestControllerTest.class);

    private static MockMvc mvc;

    /**
     * 환자별 장비 Service
     */
    @Autowired
    private PatientDeviceService patientDeviceService;

    /**
     * 격리/입소내역 관리 Service
     */
    @Autowired
    private AdmissionService admissionService;

    @Autowired
    private DataSource dataSource;
    @Autowired
    private MessageSource messageSource;

    @Before
    public void setUp() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("/sql-script/beforeSetPatientDeviceRestControllerTest.sql"));
        resourceDatabasePopulator.execute(dataSource);

        mvc = MockMvcBuilders.standaloneSetup(
                new PatientDeviceRestController(patientDeviceService, admissionService,messageSource))
            .setControllerAdvice(new RestControllerExceptionHandler())
            .build();
    }

    // -- >> Test Script <<
    // -- 환자 내원정보 생성
    // -- PATIENT_ID : PTESTSHY51
    // -- LOGIN_ID   : testshy51
    // -- PASSWORD   : 1234
    // -- SSN        : 8812051999951
    //
    // INSERT
    // INTO PATIENT ( PATIENT_ID, LOGIN_ID, PASSWORD, PATIENT_NM, SSN
    //     , BIRTH_DATE, SEX, CELL_PHONE, ZIP_CODE, ADDRESS1
    //     , ADDRESS2)
    // VALUES ( 'PTESTSHY51', 'testshy51', 'bOFW4fVzdPuNJp89%2FeNG%2FA%3D%3D', 'shy-unittest', 'LKfBXMrRmzsxJiZtK7jH6Q%3D%3D'
    //              , '1988-12-05', 'M', '01092615960', '111111', '서울시', '헬스커넥트');
    //
    // insert
    // into admission ( ADMISSION_ID, PATIENT_ID, ADMISSION_DATE, DSCHGE_SCHDLD_DATE, DSCHGE_DATE
    //     , QANTN_DIV, PERSON_CHARGE, CENTER_ID, ROOM, REG_ID
    //     , UPD_ID)
    // values ( 'TESTSHY951', 'PTESTSHY51', NOW(), '9999-12-31', null
    //     , '1', 'JUNIT_TEST', 'C999', '01', 'JUNIT'
    //     , 'JUNIT');
    //
    // delete from patient where PATIENT_ID = 'PTESTSHY51';
    // delete from admission where ADMISSION_ID = 'TESTSHY951';
    // delete from patient_device where ADMISSION_ID = 'TESTSHY951';
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
     * 환자별 장비추가 - 정상추가
     */
    @Test
    public void givenSavePatientDeviceInfo_whenAddDevice_thenSuccess() throws Exception {
        String content =
            "{\n" +
                "  \"loginId\": \"testshy51\",\n" +
                "  \"devices\": [\n" +
                "    {\n" +
                "      \"deviceId\": \"device001\",\n" +
                "      \"deviceNm\": \"디바이스1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"deviceId\": \"device002\",\n" +
                "      \"deviceNm\": \"디바이스3\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"deviceId\": \"device002\",\n" +
                "      \"deviceNm\": \"디바이스3\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        mvc.perform(post("/api/device")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
            .andDo(print());
    }

    /**
     * 환자별 장비추가 - 격리/입소내역이 없는 환자
     */
    @Test
    public void givenSavePatientDeviceInfo_whenAddDevice_thenFailNotFoundAdmissionInfo() throws Exception {
        String content =
            "{\n" +
                "  \"loginId\": \"testshy5252\",\n" +
                "  \"devices\": [\n" +
                "    {\n" +
                "      \"deviceId\": \"device001\",\n" +
                "      \"deviceNm\": \"디바이스1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"deviceId\": \"device002\",\n" +
                "      \"deviceNm\": \"디바이스3\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"deviceId\": \"device002\",\n" +
                "      \"deviceNm\": \"디바이스3\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        mvc.perform(post("/api/device")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.NOT_FOUND_ADMISSION_INFO.getCode())))
            .andDo(print());
    }

    /**
     * 환자별 장비추가 - 다중입소내역 환자
     */
    @Test
    public void givenSavePatientDeviceInfo_whenAddDevice_thenFailDuplicateAdmissionInfo() throws Exception {
        String content =
            "{\n" +
                "  \"loginId\": \"testshy2\",\n" +
                "  \"devices\": [\n" +
                "    {\n" +
                "      \"deviceId\": \"device001\",\n" +
                "      \"deviceNm\": \"디바이스1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"deviceId\": \"device002\",\n" +
                "      \"deviceNm\": \"디바이스3\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"deviceId\": \"device002\",\n" +
                "      \"deviceNm\": \"디바이스3\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        mvc.perform(post("/api/device")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.DUPLICATE_ACTIVE_ADMISSION_INFO.getCode())))
            .andDo(print());
    }

    /**
     * 환자별 장비추가 - 디바이스 아이디 누락
     */
    @Test
    public void givenSavePatientDeviceInfo_whenAddDevice_thenFailDeviceId() throws Exception {
        String content =
            "{\n" +
                "  \"loginId\": \"testshy51\",\n" +
                "  \"devices\": [\n" +
                "    {\n" +
                "      \"deviceId\": null,\n" +
                "      \"deviceNm\": \"디바이스1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"deviceId\": \"device002\",\n" +
                "      \"deviceNm\": \"디바이스3\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"deviceId\": \"device002\",\n" +
                "      \"deviceNm\": \"디바이스3\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        mvc.perform(post("/api/device")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(jsonPath("$.code", is(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode())))
            .andDo(print());
    }
}