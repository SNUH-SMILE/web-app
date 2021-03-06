package kr.co.hconnect.controller;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.RestControllerExceptionHandler;
import kr.co.hconnect.common.TokenStatus;
import kr.co.hconnect.common.TokenType;
import kr.co.hconnect.jwt.TokenDetailInfo;
import kr.co.hconnect.service.AdmissionService;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/config/context-*.xml", "/test-context-servlet.xml" })
@Transactional
public class AdmissionControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdmissionControllerTest.class);

    private static MockMvc mvc;

    @Autowired
    private AdmissionService admissionService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RestControllerExceptionHandler restControllerExceptionHandler;

    @Autowired
    private MessageSource messageSource;

    @Before
    public void setMockMvc() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("/sql-script/beforeSetAdmissionControllerTest.sql"));
        resourceDatabasePopulator.execute(dataSource);

        TokenDetailInfo tokenDetailInfo = new TokenDetailInfo();
        tokenDetailInfo.setTokenStatus(TokenStatus.OK);
        tokenDetailInfo.setTokenType(TokenType.WEB);
        tokenDetailInfo.setId("admin");

        mvc = MockMvcBuilders
                .standaloneSetup(new AdmissionController(admissionService))
                .defaultRequest(get("/").requestAttr("tokenDetailInfo", tokenDetailInfo))
                .setControllerAdvice(restControllerExceptionHandler)
                .build();
    }

    /**
     * ???????????? ?????? ?????? - ????????????
     */
    @Test
    public void selectAdmissionInfo_thenSuccess() throws Exception {

        mvc.perform(get("/api/admission/info")
                        .param("admissionId", "JUNITADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                .andDo(print());
    }

    /**
     * ???????????? ?????? ?????? - ??????????????? ?????? ??????
     */
    @Test
    public void selectAdmissionInfo_thenNotFoundAdmissionInfoException() throws Exception {
        mvc.perform(get("/api/admission/info")
                        .param("admissionId", "????????????"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.NOT_FOUND_ADMISSION_INFO.getCode())))
                .andDo(print());
    }

    /**
     * ?????????????????? ????????? ????????? ?????? - ????????????
     */
    @Test
    public void selectAdmissionListByCenter_thenSuccess() throws Exception {
        String admissionListSearchByCenterVO
                = "{\n" +
                "    \"centerId\":\"C001\",\n" +
                "    \"patientId\":\"\",\n" +
                "    \"patientNm\":\"\",\n" +
                "\n" +
                "    \"currentPageNo\":\"1\",\n" +
                "    \"recordCountPerPage\":\"15\",\n" +
                "    \"pageSize\":\"10\",\n" +
                "    \"totalRecordCount\":\"\",\n" +
                "    \"orderBy\":\"patientNm\",\n" +
                "    \"orderDir\":\"DESC\"\n" +
                "}";

        mvc.perform(post("/api/admission/center/list")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(admissionListSearchByCenterVO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                .andDo(print());
    }

    /**
     * ?????????????????? ????????? ????????? ?????? - ??????ID ?????? ????????? ??????
     */
    @Test
    public void selectAdmissionListByCenter_thenCenterIdCheck() throws Exception {
        String admissionListSearchByCenterVO
                = "{\n" +
                "    \"centerId\":\"\",\n" +
                "    \"patientId\":\"\",\n" +
                "    \"patientNm\":\"\",\n" +
                "\n" +
                "    \"currentPageNo\":\"1\",\n" +
                "    \"recordCountPerPage\":\"15\",\n" +
                "    \"pageSize\":\"10\",\n" +
                "    \"totalRecordCount\":\"\",\n" +
                "    \"orderBy\":\"patientNm\",\n" +
                "    \"orderDir\":\"DESC\"\n" +
                "}";

        mvc.perform(post("/api/admission/center/list")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(admissionListSearchByCenterVO))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode())))
                .andDo(print());
    }

    /**
     * ?????????????????? ????????? ?????? ?????? - ?????? ??????
     */
    @Test
    public void insertAdmission_thenSuccess() throws Exception {
        String admissionSaveByCenterVO =
                "{\n" +
                "    \"admissionListSearchByCenterVO\":{\n" +
                "        \"centerId\":\"C001\",\n" +
                "        \"patientId\":\"\",\n" +
                "        \"patientNm\":\"\",\n" +
                "\n" +
                "        \"currentPageNo\":\"1\",\n" +
                "        \"recordCountPerPage\":\"15\",\n" +
                "        \"pageSize\":\"10\",\n" +
                "        \"totalRecordCount\":\"\",\n" +
                "        \"orderBy\":\"\",\n" +
                "        \"orderDir\":\"\"\n" +
                "    },\n" +
                "    \"admissionId\":\"\",\n" +
                "    \"patientId\":\"\",\n" +
                "    \"patientNm\":\"junitTest\",\n" +
                "    \"birthDate\":\"19881205\",\n" +
                "    \"sex\":\"M\",\n" +
                "    \"cellPhone\":\"01011112222\",\n" +
                "    \"admissionDate\":\"20220614\",\n" +
                "    \"dschgeSchdldDate\":\"20220628\",\n" +
                "    \"personCharge\":\"?????????\",\n" +
                "    \"centerId\":\"C001\",\n" +
                "    \"room\":\"01\"\n" +
                "}";

        mvc.perform(put("/api/admission/center/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(admissionSaveByCenterVO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                .andDo(print());
    }

    /**
     * ?????????????????? ????????? ?????? ?????? - ?????? ???????????? ????????? ??????
     */
    @Test
    public void insertAdmission_thenFailDuplicateActiveAdmission() throws Exception {
        String admissionSaveByCenterVO =
                "{\n" +
                "    \"admissionListSearchByCenterVO\":{\n" +
                "        \"centerId\":\"C001\",\n" +
                "        \"patientId\":\"\",\n" +
                "        \"patientNm\":\"\",\n" +
                "\n" +
                "        \"currentPageNo\":\"1\",\n" +
                "        \"recordCountPerPage\":\"15\",\n" +
                "        \"pageSize\":\"10\",\n" +
                "        \"totalRecordCount\":\"\",\n" +
                "        \"orderBy\":\"\",\n" +
                "        \"orderDir\":\"\"\n" +
                "    },\n" +
                "    \"admissionId\":\"\",\n" +
                "    \"patientId\":\"\",\n" +
                "    \"patientNm\":\"JUNIT-DUP-TEST\",\n" +
                "    \"birthDate\":\"19881205\",\n" +
                "    \"sex\":\"M\",\n" +
                "    \"cellPhone\":\"01011221122\",\n" +
                "    \"admissionDate\":\"20220614\",\n" +
                "    \"dschgeSchdldDate\":\"20220628\",\n" +
                "    \"personCharge\":\"?????????\",\n" +
                "    \"centerId\":\"C001\",\n" +
                "    \"room\":\"01\"\n" +
                "}";

        mvc.perform(put("/api/admission/center/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(admissionSaveByCenterVO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.DUPLICATE_ACTIVE_ADMISSION_INFO.getCode())))
                .andDo(print());
    }

    /**
     * ?????????????????? ????????? ?????? (?????????) - ????????????
     */
    @Test
    public void updateAdmission_thenSuccess() throws Exception {
        String admissionSaveByCenterVO =
                "{\n" +
                        "    \"admissionListSearchByCenterVO\":{\n" +
                        "        \"centerId\":\"C001\",\n" +
                        "        \"patientId\":\"\",\n" +
                        "        \"patientNm\":\"\",\n" +
                        "\n" +
                        "        \"currentPageNo\":\"1\",\n" +
                        "        \"recordCountPerPage\":\"15\",\n" +
                        "        \"pageSize\":\"10\",\n" +
                        "        \"totalRecordCount\":\"\",\n" +
                        "        \"orderBy\":\"\",\n" +
                        "        \"orderDir\":\"\"\n" +
                        "    },\n" +
                        "    \"admissionId\":\"JUNITADMIN\",\n" +
                        "    \"patientId\":\"JUNITTEST1\",\n" +
                        "    \"patientNm\":\"JUNIT-DUP-TEST\",\n" +
                        "    \"birthDate\":\"19881205\",\n" +
                        "    \"sex\":\"M\",\n" +
                        "    \"cellPhone\":\"01011221122\",\n" +
                        "    \"admissionDate\":\"20220614\",\n" +
                        "    \"dschgeSchdldDate\":\"20220628\",\n" +
                        "    \"personCharge\":\"?????????2\",\n" +
                        "    \"centerId\":\"C001\",\n" +
                        "    \"room\":\"01\"\n" +
                        "}";

        mvc.perform(patch("/api/admission/center/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(admissionSaveByCenterVO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                .andExpect(jsonPath("$.result.admissionId", is("JUNITADMIN")))
                .andDo(print());
    }

    /**
     * ?????????????????? ????????? ?????? - ?????? ???????????? ?????? ??????
     */
    @Test
    public void updateAdmission_thenDuplicatePatientInfoException() throws Exception {
        String admissionSaveByCenterVO =
                "{\n" +
                        "    \"admissionListSearchByCenterVO\":{\n" +
                        "        \"centerId\":\"C001\",\n" +
                        "        \"patientId\":\"\",\n" +
                        "        \"patientNm\":\"\",\n" +
                        "\n" +
                        "        \"currentPageNo\":\"1\",\n" +
                        "        \"recordCountPerPage\":\"15\",\n" +
                        "        \"pageSize\":\"10\",\n" +
                        "        \"totalRecordCount\":\"\",\n" +
                        "        \"orderBy\":\"\",\n" +
                        "        \"orderDir\":\"\"\n" +
                        "    },\n" +
                        "    \"admissionId\":\"JUNITADMIN\",\n" +
                        "    \"patientId\":\"JUNITTEST1\",\n" +
                        "    \"patientNm\":\"JUNIT-DUP-TEST2\",\n" +
                        "    \"birthDate\":\"19881205\",\n" +
                        "    \"sex\":\"M\",\n" +
                        "    \"cellPhone\":\"01011221122\",\n" +
                        "    \"admissionDate\":\"20220614\",\n" +
                        "    \"dschgeSchdldDate\":\"20220628\",\n" +
                        "    \"personCharge\":\"?????????\",\n" +
                        "    \"centerId\":\"C001\",\n" +
                        "    \"room\":\"01\"\n" +
                        "}";

        mvc.perform(patch("/api/admission/center/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(admissionSaveByCenterVO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.DUPLICATE_PATIENT_INFO.getCode())))
                .andDo(print());
    }

    /**
     * ?????????????????? ????????? ?????? - ??????????????? ?????? ????????? ??????
     */
    @Test
    public void updateAdmission_thenNotFoundAdmissionInfo() throws Exception {
        String admissionSaveByCenterVO =
                "{\n" +
                        "    \"admissionListSearchByCenterVO\":{\n" +
                        "        \"centerId\":\"C001\",\n" +
                        "        \"patientId\":\"\",\n" +
                        "        \"patientNm\":\"\",\n" +
                        "\n" +
                        "        \"currentPageNo\":\"1\",\n" +
                        "        \"recordCountPerPage\":\"15\",\n" +
                        "        \"pageSize\":\"10\",\n" +
                        "        \"totalRecordCount\":\"\",\n" +
                        "        \"orderBy\":\"\",\n" +
                        "        \"orderDir\":\"\"\n" +
                        "    },\n" +
                        "    \"admissionId\":\"????????????\",\n" +
                        "    \"patientId\":\"JUNITTEST1\",\n" +
                        "    \"patientNm\":\"JUNIT-DUP-TEST\",\n" +
                        "    \"birthDate\":\"19881205\",\n" +
                        "    \"sex\":\"M\",\n" +
                        "    \"cellPhone\":\"01011221122\",\n" +
                        "    \"admissionDate\":\"20220614\",\n" +
                        "    \"dschgeSchdldDate\":\"20220628\",\n" +
                        "    \"personCharge\":\"?????????\",\n" +
                        "    \"centerId\":\"C001\",\n" +
                        "    \"room\":\"01\"\n" +
                        "}";

        mvc.perform(patch("/api/admission/center/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(admissionSaveByCenterVO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.NOT_FOUND_ADMISSION_INFO.getCode())))
                .andDo(print());
    }

    /**
     * ?????????????????? ????????? ???????????? - ????????????
     */
    @Test
    public void updateAdmissionDischargeByCenter_thenSuccess() throws Exception {
        String dschgeDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String data =
                "{\n" +
                        "    \"admissionListSearchByCenterVO\":{\n" +
                        "        \"centerId\":\"C001\",\n" +
                        "        \"patientId\":\"\",\n" +
                        "        \"patientNm\":\"\",\n" +
                        "\n" +
                        "        \"currentPageNo\":\"1\",\n" +
                        "        \"recordCountPerPage\":\"15\",\n" +
                        "        \"pageSize\":\"10\",\n" +
                        "        \"totalRecordCount\":\"\",\n" +
                        "        \"orderBy\":\"\",\n" +
                        "        \"orderDir\":\"\"\n" +
                        "    },\n" +
                        "    \"admissionId\":\"JUNITDISCH\",\n" +
                        "    \"dschgeDate\":\"" + dschgeDate + "\"\n" +
                        "}";

        mvc.perform(patch("/api/admission/center/discharge")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                .andDo(print());
    }

    /**
     * ?????????????????? ????????? ???????????? - ?????????(?????????)??? ???????????? ???????????? ??????
     */
    @Test
    public void updateAdmissionDischargeByCenter_thenCheckDschgeDate() throws Exception {
        String dschgeDate = LocalDate.now().plusDays(-2).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String data =
                "{\n" +
                        "    \"admissionListSearchByCenterVO\":{\n" +
                        "        \"centerId\":\"C001\",\n" +
                        "        \"patientId\":\"\",\n" +
                        "        \"patientNm\":\"\",\n" +
                        "\n" +
                        "        \"currentPageNo\":\"1\",\n" +
                        "        \"recordCountPerPage\":\"15\",\n" +
                        "        \"pageSize\":\"10\",\n" +
                        "        \"totalRecordCount\":\"\",\n" +
                        "        \"orderBy\":\"\",\n" +
                        "        \"orderDir\":\"\"\n" +
                        "    },\n" +
                        "    \"admissionId\":\"JUNITDISCH\",\n" +
                        "    \"dschgeDate\":\"" + dschgeDate + "\"\n" +
                        "}";

        mvc.perform(patch("/api/admission/center/discharge")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode())))
                .andExpect(jsonPath("$.message", is(messageSource.getMessage("message.admission.past.dschgeDate"
                        , null, Locale.getDefault()))))
                .andDo(print());
    }


}
