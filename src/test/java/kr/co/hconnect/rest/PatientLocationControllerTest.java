package kr.co.hconnect.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kr.co.hconnect.common.RestControllerExceptionHandler;
import kr.co.hconnect.service.PatientLocationService;
import kr.co.hconnect.vo.PatientLocationInfoResponseVO;
import kr.co.hconnect.vo.PatientLocationResponseVO;
import kr.co.hconnect.vo.PatientLocationVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/config/context-*.xml", "/test-context-servlet.xml" })
public class PatientLocationControllerTest {

    @Autowired
    private PatientLocationService patientLocationService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private RestControllerExceptionHandler restControllerExceptionHandler;

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(new PatientLocationController(patientLocationService, messageSource))
            .setControllerAdvice(restControllerExceptionHandler)
            .build();
    }

    @Test
    public void givenNormalCase_whenAddPatientLocation_thenCorrect() throws Exception {
        PatientLocationVO requestVO = new PatientLocationVO();
        requestVO.setLoginId("heritage124");
        requestVO.setResultDate("20230912");
        requestVO.setResultTime("154500");
        requestVO.setLatitude("41.40338");
        requestVO.setLongitude("2.17403");

        String jsonString = objectMapper.writeValueAsString(requestVO);
        MvcResult result = mockMvc.perform(post("/api/location/setLocationList")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        PatientLocationResponseVO responseVO = objectMapper.readValue(result.getResponse().getContentAsByteArray()
            , PatientLocationResponseVO.class);
        assertNotNull(responseVO);
        assertEquals("00", responseVO.getCode());
        assertEquals("600000", responseVO.getInterval());
    }

    @Test
    public void givenInvalidRequest_whenAddPatientLocation_thenReturn4xx() throws Exception {
        PatientLocationVO requestVO = new PatientLocationVO();
        requestVO.setLoginId("heritage124");
        // requestVO.setResultDate("20230912");
        requestVO.setResultTime("154500");
        requestVO.setLatitude("41.40338");
        requestVO.setLongitude("2.17403");

        String jsonString = objectMapper.writeValueAsString(requestVO);
        mockMvc.perform(post("/api/location/setLocationList")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void givenInvalidRequest_whenGetPatientLocations_thenReturn4xx() throws Exception {
        mockMvc.perform(get("/api/patient-locations")
                .param("admissionId", "0000000145"))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenGetPatientLocations_thenCorrect() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/patient-locations")
                .param("admissionId", "0000000145")
                .param("resultDate", "2023-09-14"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        PatientLocationInfoResponseVO responseVO = objectMapper.readValue(result.getResponse().getContentAsByteArray()
            , PatientLocationInfoResponseVO.class);
        assertNotNull(responseVO);
        assertEquals("00", responseVO.getCode());
    }
}