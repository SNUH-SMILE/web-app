package kr.co.hconnect.rest;

import kr.co.hconnect.service.AdmissionService;
import kr.co.hconnect.service.MeasurementResultService;
import kr.co.hconnect.service.ResultService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/config/context-*.xml", "/test-context-servlet.xml" })
public class MeasurementResultRestControllerTest {
    private static MockMvc mvc;
    @Autowired
    private MeasurementResultService measurementResultService;
    @Autowired
    private AdmissionService admissionService;
    @Autowired
    private ResultService resultService;
    @Before
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(
            new MeasurementResultRestController(measurementResultService,admissionService,resultService)).build();
    }
    @Test
    public void givenLoginId_whenRequested_thenCode_00_ExistYn_Y() throws Exception{
        String jsonString ="{\n" +
            " \"loginId\": \"test\"\n" +
            "}";

        mvc.perform(get("/api/main/notice")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.existYn").value("Y"))
            .andDo(print());
    }

    @Test
    public void givenLoginId_whenRequested_thenCode_00_ExistYn_N() throws Exception{
        String jsonString ="{\n" +
            " \"loginId\": \"test\"\n" +
            "}";

        mvc.perform(get("/api/main/notice")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.existYn").value("N"))
            .andDo(print());
    }


    // @Rule
    // public ExpectedException expectedException = ExpectedException.none();
    // @Test(expected = NestedServletException.class)
    // @Test(expected = NestedServletException.class)
    // @Test(expected = NotFoundAdmissionInfoException.class)
    @Test
    public void givenLoginId_whenRequested_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            " \"loginId\": \"Exception\"\n" +
            "}";
        // expectedException.expect();
        // expectedException.NotFoundAdmissionInfoException("fdsfaf");
        mvc.perform(get("/api/main/notice")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(jsonPath("$.code").value("99"))
            .andDo(print());
    }

    @Test
    public void givenLoginId_ResultDate_whenRequested_thenCode_00_BtList_NotEmpty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"test\",\n" +
            "  \"resultDate\":\"20211129\"\n" +
            "}";
        mvc.perform(get("/api/result/bt")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value("체온 상세목록 조회 되었습니다."))
            .andExpect(jsonPath("$.btList").isNotEmpty())
            .andDo(print());
    }
    @Test
    public void givenLoginId_ResultDate_whenRequested_thenCode_00_BtList_Empty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"test\",\n" +
            "  \"resultDate\":\"20001130\"\n" +
            "}";
        mvc.perform(get("/api/result/bt")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value("체온 상세목록이 없습니다."))
            .andExpect(jsonPath("$.btList").isEmpty())
            .andDo(print());
    }


    @Test
    public void givenLoginId_ResultDate_whenRequested_thenCode_00_BpList_NotEmpty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"test\",\n" +
            "  \"resultDate\":\"20211129\"\n" +
            "}";
        mvc.perform(get("/api/result/bp")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value("혈압 상세목록 조회 되었습니다."))
            .andExpect(jsonPath("$.bpList").isNotEmpty())
            .andDo(print());
    }
    @Test
    public void givenLoginId_ResultDate_whenRequested_thenCode_00_BpList_Empty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"test\",\n" +
            "  \"resultDate\":\"20001130\"\n" +
            "}";
        mvc.perform(get("/api/result/bp")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value("혈압 상세목록이 없습니다."))
            .andExpect(jsonPath("$.bpList").isEmpty())
            .andDo(print());
    }

    @Test
    public void givenLoginId_ResultDate_whenRequested_thenCode_00_HrList_NotEmpty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"test\",\n" +
            "  \"resultDate\":\"20211129\"\n" +
            "}";
        mvc.perform(get("/api/result/hr")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value("심박수 상세목록 조회 되었습니다."))
            .andExpect(jsonPath("$.hrList").isNotEmpty())
            .andDo(print());
    }
    @Test
    public void givenLoginId_ResultDate_whenRequested_thenCode_00_HrList_Empty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"test\",\n" +
            "  \"resultDate\":\"20001130\"\n" +
            "}";
        mvc.perform(get("/api/result/hr")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value("심박수 상세목록이 없습니다."))
            .andExpect(jsonPath("$.hrList").isEmpty())
            .andDo(print());
    }

    @Test
    public void givenLoginId_ResultDate_whenRequested_thenCode_00_SpO2List_NotEmpty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"test\",\n" +
            "  \"resultDate\":\"20211129\"\n" +
            "}";
        mvc.perform(get("/api/result/spO2")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value("산소포화도 상세목록 조회 되었습니다."))
            .andExpect(jsonPath("$.spO2List").isNotEmpty())
            .andDo(print());
    }
    @Test
    public void givenLoginId_ResultDate_whenRequested_thenCode_00_SpO2List_Empty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"test\",\n" +
            "  \"resultDate\":\"20001130\"\n" +
            "}";
        mvc.perform(get("/api/result/spO2")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value("산소포화도 상세목록이 없습니다."))
            .andExpect(jsonPath("$.spO2List").isEmpty())
            .andDo(print());
    }

    @Test
    public void givenLoginId_ResultDate_whenRequested_thenCode_00_StepList_NotEmpty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"test\",\n" +
            "  \"resultDate\":\"20211129\"\n" +
            "}";
        mvc.perform(get("/api/result/step")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value("걸음 상세목록 조회 되었습니다."))
            .andExpect(jsonPath("$.stepCountList").isNotEmpty())
            .andDo(print());
    }
    @Test
    public void givenLoginId_ResultDate_whenRequested_thenCode_00_StepList_Empty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"test\",\n" +
            "  \"resultDate\":\"20001130\"\n" +
            "}";
        mvc.perform(get("/api/result/step")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value("걸음 상세목록이 없습니다."))
            .andExpect(jsonPath("$.stepCountList").isEmpty())
            .andDo(print());
    }

    @Test
    public void givenLoginId_ResultDate_whenRequested_thenCode_00_SleepList_NotEmpty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"test\",\n" +
            "  \"resultStartDateTime\":\"2021113021\",\n" +
            "  \"resultEndDateTime\":\"2021120109\"\n" +
            "\n" +
            "}";
        mvc.perform(get("/api/result/sleep")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value("수면시간 상세목록 조회 되었습니다."))
            .andExpect(jsonPath("$.sleepTimeList").isNotEmpty())
            .andDo(print());
    }
    @Test
    public void givenLoginId_ResultDate_whenRequested_thenCode_00_SleepList_Empty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"test\",\n" +
            "  \"resultStartDateTime\":\"1999113021\",\n" +
            "  \"resultEndDateTime\":\"1999120109\"\n" +
            "\n" +
            "}";
        mvc.perform(get("/api/result/sleep")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value("수면시간 상세목록이 없습니다."))
            .andExpect(jsonPath("$.sleepTimeList").isEmpty())
            .andDo(print());
    }
}