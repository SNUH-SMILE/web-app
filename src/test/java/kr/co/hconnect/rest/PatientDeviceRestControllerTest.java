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
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static org.hamcrest.CoreMatchers.containsString;
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

    @AfterTransaction
    public void tearDownAfterTx() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("/sql-script/afterSetPatientDeviceRestControllerTest.sql"));
        resourceDatabasePopulator.execute(dataSource);
    }

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
                "      \"deviceId\": \"device003\",\n" +
                "      \"deviceNm\": \"디바이스3\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        mvc.perform(post("/api/device")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
            // 사용이력 데이터 확인
            .andExpect(jsonPath("$.deviceUseHistoryList.length()", is(3)))
            // 장비내역 확인
            .andExpect(jsonPath("$.deviceUseHistoryList[0].deviceId", is("device001")))
            .andExpect(jsonPath("$.deviceUseHistoryList[1].deviceId", is("device002")))
            .andExpect(jsonPath("$.deviceUseHistoryList[2].deviceId", is("device003")))
            .andDo(print());
    }

    /**
     * 환자별 장비추가 - 데이터 사이즈 초과 확인
     */
    @Test
    public void givenSavePatientDeviceInfoOverSizeParam_whenAddDevice_thenSizeCheckedFail() throws Exception {
        String content =
            "{\n" +
                "  \"loginId\": \"testshy51\",\n" +
                "  \"devices\": [\n" +
                "    {\n" +
                "      \"deviceId\": \"123456789012345678901\",\n" +
                "      \"deviceNm\": \"123456789012345678901234567890123456789012345678901\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"deviceId\": \"device002\",\n" +
                "      \"deviceNm\": \"디바이스3\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"deviceId\": \"device003\",\n" +
                "      \"deviceNm\": \"디바이스3\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        mvc.perform(post("/api/device")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.code", is(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode())))
            .andExpect(jsonPath("$.message", containsString("{validation.size.deviceId}")))
            .andExpect(jsonPath("$.message", containsString("{validation.size.deviceNm}")))
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
                "      \"deviceId\": \"device003\",\n" +
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
                "      \"deviceId\": \"device003\",\n" +
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
                "      \"deviceId\": \"device003\",\n" +
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