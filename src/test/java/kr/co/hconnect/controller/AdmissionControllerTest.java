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

        mvc.perform(post("/api/admission/list/center")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(admissionListSearchByCenterVO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                .andDo(print());
    }

}
