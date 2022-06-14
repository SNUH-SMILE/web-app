package kr.co.hconnect.controller;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.service.AdmissionService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @Before
    public void setMockMvc() {
        mvc = MockMvcBuilders.standaloneSetup(new AdmissionController(admissionService)).build();
    }

    /**
     * 생활치료센터 입소자 리스트 조회 - 정상확인
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
     * 생활치료센터 입소자 신규 등록 - 정상 확인
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
                "    \"personCharge\":\"담당의\",\n" +
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
     * 생활치료센터 입소자 신규 등록 - 이미 존재하는 입소자 확인
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
                "    \"patientNm\":\"junitTest\",\n" +
                "    \"birthDate\":\"19881205\",\n" +
                "    \"sex\":\"M\",\n" +
                "    \"cellPhone\":\"01011112222\",\n" +
                "    \"admissionDate\":\"20220614\",\n" +
                "    \"dschgeSchdldDate\":\"20220628\",\n" +
                "    \"personCharge\":\"담당의\",\n" +
                "    \"centerId\":\"C001\",\n" +
                "    \"room\":\"01\"\n" +
                "}";

        mvc.perform(put("/api/admission/center/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(admissionSaveByCenterVO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.DUPLICATE_PATIENT_INFO.getCode())))
                .andDo(print());
    }

}
