package kr.co.hconnect.controller;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.RestControllerExceptionHandler;
import kr.co.hconnect.service.PatientDashboardService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/config/context-*.xml", "/test-context-servlet.xml" })
@Transactional
public class PatientDashboardControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientDashboardControllerTest.class);

    private static MockMvc mvc;

    /**
     * 환자 대시보드 Service
     */
    @Autowired
    private PatientDashboardService patientDashboardService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private RestControllerExceptionHandler restControllerExceptionHandler;

    @Before
    public void setMockMvc() {
        mvc = MockMvcBuilders
                .standaloneSetup(new PatientDashboardController(patientDashboardService, messageSource))
                .setControllerAdvice(restControllerExceptionHandler)
                .build();
    }

    /**
     * 자택격리자 환자 대시보드 현황 조회 - 정상확인
     */
    @Test
    public void selectPatientStatusDashboardByQuarantine_thenSuccess() throws Exception {
        String data =
                "{\n" +
                        "    \"qantnDiv\":\"1\",\n" +
                        "    \"centerId\":\"\"\n" +
                        "}";

        mvc.perform(post("/api/patientDashboard/status/list")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                .andDo(print());
    }

    /**
     * 생활치료센터 환자 대시보드 현황 조회 - 정상확인
     */
    @Test
    public void selectPatientStatusDashboardByCenter_thenSuccess() throws Exception {
        String data =
                "{\n" +
                "    \"qantnDiv\":\"2\",\n" +
                "    \"centerId\":\"C001\"\n" +
                "}";

        mvc.perform(post("/api/patientDashboard/status/list")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                .andDo(print());
    }

    /**
     * 대시보드 현황 조회 - 격리/입소 구분 입력여부 확인
     */
    @Test
    public void selectPatientStatusDashboard_thenCheckQantnDiv() throws Exception {
        String data =
                "{\n" +
                "    \"qantnDiv\":\"\",\n" +
                "    \"centerId\":\"C001\"\n" +
                "}";

        mvc.perform(post("/api/patientDashboard/status/list")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode())))
                .andExpect(jsonPath("$.message", containsString("validation.null.qantnDiv")))
                .andDo(print());
    }

}
