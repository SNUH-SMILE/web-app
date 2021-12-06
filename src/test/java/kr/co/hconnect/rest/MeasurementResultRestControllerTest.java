package kr.co.hconnect.rest;

import kr.co.hconnect.common.RestControllerExceptionHandler;
import kr.co.hconnect.service.AdmissionService;
import kr.co.hconnect.service.MeasurementResultService;
import kr.co.hconnect.service.ResultService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/config/context-*.xml", "/test-context-servlet.xml" })
@Transactional
public class MeasurementResultRestControllerTest {

    private static MockMvc mvc;
    @Autowired
    private MeasurementResultService measurementResultService;
    @Autowired
    private AdmissionService admissionService;
    @Autowired
    private ResultService resultService;

    @Autowired
    private DataSource dataSource;
    @Before
    public void setup() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("/sql-script/beforeSetPatientForTest.sql"));
        resourceDatabasePopulator.execute(dataSource);
        mvc = MockMvcBuilders.standaloneSetup(
                new MeasurementResultRestController(measurementResultService, admissionService, resultService))
            .setControllerAdvice(new RestControllerExceptionHandler())
            .build();
    }

    /**
     * 신규 알림 여부조회시 신규알림이 있을때
     * @throws Exception mvc.perform
     */
    @Sql(scripts = "/sql-script/beforeSetupNoticeReadYn_N.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    public void givenLoginId_whenRequestedNotice_thenCode_00_ExistYn_Y() throws Exception {
        String jsonString ="{\n" +
            " \"loginId\": \"wtest\"\n" +
            "}";

        mvc.perform(get("/api/main/notice")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.existYn").value("Y"))
            .andDo(print());
    }

    /**
     * 신규 알림 여부조회시 신규알림이 없을때
     * @throws Exception mvc.perform
     */
    @Sql(scripts = "/sql-script/beforeSetupNoticeReadYn_Y.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    public void givenLoginId_whenRequestedNotice_thenCode_00_ExistYn_N() throws Exception{
        String jsonString ="{\n" +
            " \"loginId\": \"wtest\"\n" +
            "}";

        mvc.perform(get("/api/main/notice")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.existYn").value("N"))
            .andDo(print());
    }
    /**
     * 신규 알림 여부조회시 LoginId 가 파라미터로 없을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenNull_whenRequestedNotice_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            "}";

        mvc.perform(get("/api/main/notice")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("사용자 아이디가 누락되었습니다."))
            .andDo(print());
    }
    /**
     * 신규 알림 여부조회시 해당 LoginId에 맞는 AdmissionId 가 없을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenLoginId_whenRequestedNotice_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            " \"loginId\": \"Exception\"\n" +
            "}";

        mvc.perform(get("/api/main/notice")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("내원중인 격리/입소내역이 존재하지 않습니다."))
            .andDo(print());
    }
    /**
     * 체온 조회 리스트가 있을때
     * @throws Exception mvc.perform
     */
    @Sql(scripts = { "/sql-script/beforeSetupBtResult.sql"}
        ,executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    public void givenLoginIdAndResultDate_ResultDate_whenRequestedBt_thenCode_00_List_NotEmpty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"wtest\",\n" +
            "  \"resultDate\":\"20211206\"\n" +
            "}";
        mvc.perform(get("/api/result/bt")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value("체온 상세목록 조회 되었습니다."))
            .andExpect(jsonPath("$.btList").isNotEmpty())
            .andExpect(jsonPath("$.btList[0].bt").value("36"))
            .andDo(print());
    }
    /**
     * 체온 조회 리스트가 없을때
     * @throws Exception mvc.perform
     */
    @Test
    public void givenLoginIdAndResultDate_ResultDate_whenRequestedBt_thenCode_00_List_Empty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"wtest\",\n" +
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
    /**
     * 체온리스트 조회시 아이디 & 측정일자 둘 다 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenNull_whenRequestedBt_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            "}";

        mvc.perform(get("/api/result/bt")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message",anyOf(
                is("사용자 아이디가 누락되었습니다.측정일자가 누락되었습니다."),
                is("측정일자가 누락되었습니다.사용자 아이디가 누락되었습니다.")
            )))
            .andDo(print());
    }
    /**
     * 체온리스트 조회시 아이디가 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenResultDate_whenRequestedBt_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            "  \"resultDate\":\"20211206\"\n" +
            "}";

        mvc.perform(get("/api/result/bt")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("사용자 아이디가 누락되었습니다."))
            .andDo(print());
    }

    /**
     * 체온리스트 조회시 측정일자가 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenLoginId_whenRequestedBt_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            " \"loginId\": \"wtest\"\n" +
            "}";

        mvc.perform(get("/api/result/bt")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("측정일자가 누락되었습니다."))
            .andDo(print());
    }

    /**
     * 체온리스트 조회시 LoginId에 맞는 입소자 아이디가 없을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenWrongLoginId_whenRequestedBt_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            " \"loginId\": \"Exception\",\n" +
            " \"resultDate\":\"20201212\"\n" +
            "}";
        mvc.perform(get("/api/result/bt")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("내원중인 격리/입소내역이 존재하지 않습니다."))
            .andDo(print());
    }

    /**
     * 혈압 조회 리스트가 있을때
     * @throws Exception mvc.perform
     */
    @Sql(scripts = { "/sql-script/beforeSetupBpResult.sql"}
        ,executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    public void givenLoginIdAndResultDate_ResultDate_whenRequestedBp_thenCode_00_List_NotEmpty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"wtest\",\n" +
            "  \"resultDate\":\"20211206\"\n" +
            "}";
        mvc.perform(get("/api/result/bp")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value("혈압 상세목록 조회 되었습니다."))
            .andExpect(jsonPath("$.bpList").isNotEmpty())
            .andExpect(jsonPath("$.bpList[0].dbp").value("50"))
            .andExpect(jsonPath("$.bpList[0].sbp").value("100"))
            .andDo(print());
    }
    /**
     * 혈압 조회 리스트가 없을때
     * @throws Exception mvc.perform
     */
    @Test
    public void givenLoginIdAndResultDate_ResultDate_whenRequestedBp_thenCode_00_List_Empty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"wtest\",\n" +
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
    /**
     * 혈압리스트 조회시 아이디 & 측정일자 둘 다 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenNull_whenRequestedBp_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            "}";

        mvc.perform(get("/api/result/bp")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message",anyOf(
                is("사용자 아이디가 누락되었습니다.측정일자가 누락되었습니다."),
                is("측정일자가 누락되었습니다.사용자 아이디가 누락되었습니다.")
            )))
            .andDo(print());
    }
    /**
     * 혈압 리스트 조회시 아이디가 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenResultDate_whenRequestedBp_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            "  \"resultDate\":\"20211206\"\n" +
            "}";

        mvc.perform(get("/api/result/bp")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("사용자 아이디가 누락되었습니다."))
            .andDo(print());
    }

    /**
     * 혈압 리스트 조회시 측정일자가 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenLoginId_whenRequestedBp_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            " \"loginId\": \"wtest\"\n" +
            "}";

        mvc.perform(get("/api/result/bp")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("측정일자가 누락되었습니다."))
            .andDo(print());
    }

    /**
     * 혈압 리스트 조회시 LoginId에 맞는 입소자 아이디가 없을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenWrongLoginId_whenRequestedBp_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            " \"loginId\": \"Exception\",\n" +
            " \"resultDate\":\"20201212\"\n" +
            "}";
        mvc.perform(get("/api/result/bp")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("내원중인 격리/입소내역이 존재하지 않습니다."))
            .andDo(print());
    }

    /**
     * 심박수 조회 리스트가 있을때
     * @throws Exception mvc.perform
     */
    @Sql(scripts = { "/sql-script/beforeSetupHrResult.sql"}
        ,executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    public void givenLoginIdAndResultDate_ResultDate_whenRequestedHr_thenCode_00_List_NotEmpty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"wtest\",\n" +
            "  \"resultDate\":\"20211206\"\n" +
            "}";
        mvc.perform(get("/api/result/hr")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value("심박수 상세목록 조회 되었습니다."))
            .andExpect(jsonPath("$.hrList").isNotEmpty())
            .andExpect(jsonPath("$.hrList[0].hr").value("95"))
            .andDo(print());
    }
    /**
     * 심박수 조회 리스트가 없을때
     * @throws Exception mvc.perform
     */
    @Test
    public void givenLoginIdAndResultDate_ResultDate_whenRequested_thenCode_00_HrList_Empty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"wtest\",\n" +
            "  \"resultDate\":\"20001201\"\n" +
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
    /**
     * 심박수리스트 조회시 아이디 & 측정일자 둘 다 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenNull_whenRequestedHr_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            "}";

        mvc.perform(get("/api/result/hr")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message",anyOf(
                is("사용자 아이디가 누락되었습니다.측정일자가 누락되었습니다."),
                is("측정일자가 누락되었습니다.사용자 아이디가 누락되었습니다.")
            )))
            .andDo(print());
    }
    /**
     * 심박수 리스트 조회시 아이디가 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenResultDate_whenRequestedHr_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            "  \"resultDate\":\"20211206\"\n" +
            "}";

        mvc.perform(get("/api/result/hr")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("사용자 아이디가 누락되었습니다."))
            .andDo(print());
    }

    /**
     * 심박수 리스트 조회시 측정일자가 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenLoginId_whenRequestedHr_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            " \"loginId\": \"wtest\"\n" +
            "}";

        mvc.perform(get("/api/result/hr")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("측정일자가 누락되었습니다."))
            .andDo(print());
    }

    /**
     * 심박수 리스트 조회시 LoginId에 맞는 입소자 아이디가 없을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenWrongLoginId_whenRequestedHr_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            " \"loginId\": \"Exception\",\n" +
            " \"resultDate\":\"20201212\"\n" +
            "}";
        mvc.perform(get("/api/result/hr")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("내원중인 격리/입소내역이 존재하지 않습니다."))
            .andDo(print());
    }

    /**
     * 산소포화도 조회 리스트가 있을때
     * @throws Exception mvc.perform
     */
    @Sql(scripts = { "/sql-script/beforeSetupSpO2Result.sql"}
        ,executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    public void givenLoginIdAndResultDate_ResultDate_whenRequestedSpO2_thenCode_00_List_NotEmpty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"wtest\",\n" +
            "  \"resultDate\":\"20211206\"\n" +
            "}";
        mvc.perform(get("/api/result/spO2")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value("산소포화도 상세목록 조회 되었습니다."))
            .andExpect(jsonPath("$.spO2List").isNotEmpty())
            .andExpect(jsonPath("$.spO2List[0].spO2").value("55"))
            .andDo(print());
    }
    /**
     * 산소포화도 조회 리스트가 없을때
     * @throws Exception mvc.perform
     */
    @Test
    public void givenLoginIdAndResultDate_ResultDate_whenRequestedSpO2_thenCode_00_List_Empty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"wtest\",\n" +
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
    /**
     * 산소포화도 리스트 조회시 아이디 & 측정일자 둘 다 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenNull_whenRequestedSpO2_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            "}";

        mvc.perform(get("/api/result/spO2")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message",anyOf(
                is("사용자 아이디가 누락되었습니다.측정일자가 누락되었습니다."),
                is("측정일자가 누락되었습니다.사용자 아이디가 누락되었습니다.")
            )))
            .andDo(print());
    }
    /**
     * 산소포화도 리스트 조회시 아이디가 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenResultDate_whenRequestedSpO2_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            "  \"resultDate\":\"20211206\"\n" +
            "}";

        mvc.perform(get("/api/result/spO2")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("사용자 아이디가 누락되었습니다."))
            .andDo(print());
    }

    /**
     * 산소포화도 리스트 조회시 측정일자가 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenLoginId_whenRequestedSpO2_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            " \"loginId\": \"wtest\"\n" +
            "}";

        mvc.perform(get("/api/result/spO2")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("측정일자가 누락되었습니다."))
            .andDo(print());
    }

    /**
     * 산소포화도 리스트 조회시 LoginId에 맞는 입소자 아이디가 없을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenWrongLoginId_whenRequestedSpO2_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            " \"loginId\": \"Exception\",\n" +
            " \"resultDate\":\"20201212\"\n" +
            "}";
        mvc.perform(get("/api/result/spO2")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("내원중인 격리/입소내역이 존재하지 않습니다."))
            .andDo(print());
    }
    /**
     * 걸음 조회 리스트가 있을때
     * @throws Exception mvc.perform
     */
    @Sql(scripts = { "/sql-script/beforeSetupStepResult.sql"}
        ,executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    public void givenLoginIdAndResultDate_ResultDate_whenRequestedStep_thenCode_00_List_NotEmpty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"wtest\",\n" +
            "  \"resultDate\":\"20211206\"\n" +
            "}";
        mvc.perform(get("/api/result/step")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value("걸음 상세목록 조회 되었습니다."))
            .andExpect(jsonPath("$.stepCountList").isNotEmpty())
            .andExpect(jsonPath("$.stepCountList[0].stepCount").value("150"))
            .andExpect(jsonPath("$.stepCountList[0].distance").value("50"))
            .andDo(print());
    }
    /**
     * 걸음 조회 리스트가 없을때
     * @throws Exception mvc.perform
     */
    @Test
    public void givenLoginIdAndResultDate_ResultDate_whenRequestedStep_thenCode_00_List_Empty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"wtest\",\n" +
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
    /**
     * 걸음 리스트 조회시 아이디 & 측정일자 둘 다 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenNull_whenRequestedStep_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            "}";

        mvc.perform(get("/api/result/step")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message",anyOf(
                is("사용자 아이디가 누락되었습니다.측정일자가 누락되었습니다."),
                is("측정일자가 누락되었습니다.사용자 아이디가 누락되었습니다.")
            )))
            .andDo(print());
    }
    /**
     * 걸음 리스트 조회시 아이디가 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenResultDate_whenRequestedStep_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            "  \"resultDate\":\"20211206\"\n" +
            "}";

        mvc.perform(get("/api/result/step")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("사용자 아이디가 누락되었습니다."))
            .andDo(print());
    }

    /**
     * 걸음 리스트 조회시 측정일자가 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenLoginId_whenRequestedStep_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            " \"loginId\": \"wtest\"\n" +
            "}";

        mvc.perform(get("/api/result/step")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("측정일자가 누락되었습니다."))
            .andDo(print());
    }

    /**
     * 걸음 리스트 조회시 LoginId에 맞는 입소자 아이디가 없을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenWrongLoginId_whenRequestedStep_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            " \"loginId\": \"Exception\",\n" +
            " \"resultDate\":\"20201212\"\n" +
            "}";
        mvc.perform(get("/api/result/step")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("내원중인 격리/입소내역이 존재하지 않습니다."))
            .andDo(print());
    }

    /**
     * 수면 조회 리스트가 있을때
     * @throws Exception mvc.perform
     */
    @Sql(scripts = { "/sql-script/beforeSetupSleepResult.sql"}
        ,executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    public void givenLoginIdAndResultDate_ResultDate_whenRequestedSleep_thenCode_00_List_NotEmpty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"wtest\",\n" +
            "  \"resultStartDateTime\":\"2021120621\",\n" +
            "  \"resultEndDateTime\":\"2021120709\"\n" +
            "\n" +
            "}";
        mvc.perform(get("/api/result/sleep")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value("수면시간 상세목록 조회 되었습니다."))
            .andExpect(jsonPath("$.totalSleepTime").value("1"))
            .andExpect(jsonPath("$.sleepTimeList").isNotEmpty())
            .andExpect(jsonPath("$.sleepTimeList[0].sleepType").value("1"))
            .andExpect(jsonPath("$.sleepTimeList[0].sleepStartTime").value("2330"))
            .andExpect(jsonPath("$.sleepTimeList[0].sleepEndTime").value("0030"))
            .andExpect(jsonPath("$.sleepTimeList[1].sleepType").value("2"))
            .andExpect(jsonPath("$.sleepTimeList[1].sleepStartTime").value("0031"))
            .andExpect(jsonPath("$.sleepTimeList[1].sleepEndTime").value("0130"))
            .andDo(print());
    }
    /**
     * 수면 조회 리스트가 없을때
     * @throws Exception mvc.perform
     */
    @Test
    public void givenLoginIdAndResultDate_ResultDate_whenRequestedSleep_thenCode_00_List_Empty() throws Exception{
        String jsonString = "{\n" +
            "  \"loginId\": \"wtest\",\n" +
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
            .andExpect(jsonPath("$.totalSleepTime").value("0"))
            .andExpect(jsonPath("$.sleepTimeList").isEmpty())
            .andDo(print());
    }

    /**
     * 수면 리스트 조회시 아이디 & 측정시작일시 & 측정종료일시가 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenNull_whenRequestedSleep_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            "}";

        mvc.perform(get("/api/result/sleep")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message",anyOf(
                       is("사용자 아이디가 누락되었습니다.측정시작일시가 누락되었습니다.측정종료일시가 누락되었습니다."),
                       is("사용자 아이디가 누락되었습니다.측정종료일시가 누락되었습니다.측정시작일시가 누락되었습니다."),
                       is("측정시작일시가 누락되었습니다.사용자 아이디가 누락되었습니다.측정종료일시가 누락되었습니다."),
                       is("측정시작일시가 누락되었습니다.측정종료일시가 누락되었습니다.사용자 아이디가 누락되었습니다."),
                       is("측정종료일시가 누락되었습니다.사용자 아이디가 누락되었습니다.측정시작일시가 누락되었습니다."),
                       is("측정종료일시가 누락되었습니다.측정시작일시가 누락되었습니다.사용자 아이디가 누락되었습니다.")
            )))
            .andDo(print());
    }

    /**
     * 수면 리스트 조회시 아이디가 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenStartAndEndDateTime_whenRequestedSleep_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            " \"resultStartDateTime\": \"2021120609\",\n" +
            " \"resultEndDateTime\": \"2021120609\"\n" +
            "}";

        mvc.perform(get("/api/result/sleep")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("사용자 아이디가 누락되었습니다."))
            .andDo(print());
    }
    /**
     * 수면 리스트 조회시 측정시작일시 & 종료일시 가 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenLoginId_whenRequestedSleep_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            " \"loginId\": \"wtest\"\n" +
            "}";

        mvc.perform(get("/api/result/sleep")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message",anyOf(
                       is("측정시작일시가 누락되었습니다.측정종료일시가 누락되었습니다."),
                       is("측정종료일시가 누락되었습니다.측정시작일시가 누락되었습니다.")
            )))
            .andDo(print());
    }
    /**
     * 수면 리스트 조회시 측정시작일시 가 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenLoginIdAndEndDateTime_whenRequestedSleep_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            " \"loginId\": \"wtest\",\n" +
            " \"resultEndDateTime\": \"2021120609\"\n" +
            "}";

        mvc.perform(get("/api/result/sleep")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("측정시작일시가 누락되었습니다."))
            .andDo(print());
    }
    /**
     * 수면 리스트 조회시 측정종료일시 가 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenLoginIdAndStartDateTime_whenRequestedSleep_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            " \"loginId\": \"wtest\",\n" +
            " \"resultStartDateTime\": \"2021120609\"\n" +
            "}";

        mvc.perform(get("/api/result/sleep")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("측정종료일시가 누락되었습니다."))
            .andDo(print());
    }
    /**
     * 수면 리스트 조회시 LoginId에 맞는 입소자 아이디가 없을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenWrongLoginId_whenRequestedSleep_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            " \"loginId\": \"Exception\",\n" +
            " \"resultStartDateTime\": \"2021120621\"\n," +
            " \"resultEndDateTime\": \"2021120709\"\n" +
            "}";
        mvc.perform(get("/api/result/sleep")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("내원중인 격리/입소내역이 존재하지 않습니다."))
            .andDo(print());
    }

    /**
     * 메인컨텐츠 측정결과 리스트가 없을때
     * @throws Exception mvc.perform
     */
    @Test
    public void givenLoginId_whenRequested_thenCode_00_MainContent_List_NotNull() throws Exception{
        String jsonString ="{\n" +
            " \"loginId\": \"wtest\"\n" +
            "}";

        mvc.perform(get("/api/main")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value(containsString("메인컨텐츠가 측정결과 목록이 없습니다.")))
            .andExpect(jsonPath("$.patientNm").value("P123456789"))
            .andExpect(jsonPath("$.admissionDate").value("20211205"))
            .andExpect(jsonPath("$.dischargeScheduledDate").value("20221230"))
            .andExpect(jsonPath("$.personCharge").value("헬스커넥트"))
            //체온
            .andExpect(jsonPath("$.todayBpList").isArray())
            //혈압
            .andExpect(jsonPath("$.todayBtList").isArray())
            //심박수
            .andExpect(jsonPath("$.todayHrList").isArray())
            //산소포화도
            .andExpect(jsonPath("$.todaySpO2List").isArray())
            //걸음수
            .andExpect(jsonPath("$.todayStepCountList").isArray())
            //수면
            .andExpect(jsonPath("$.todaySleepTimeList").isArray())
            .andDo(print());
    }


    /**
     * 메인컨텐츠 측정결과 리스트가 있을때
     * @throws Exception mvc.perform
     */
    @Test
    @Sql(scripts = { "/sql-script/beforeSetupTodayResult.sql"}
        ,executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void givenLoginId_whenRequested_thenCode_00_MainContent_List_Null() throws Exception{
        String jsonString ="{\n" +
            " \"loginId\": \"wtest\"\n" +
            "}";

        mvc.perform(get("/api/main")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("00"))
            .andExpect(jsonPath("$.message").value(containsString("메인컨텐츠가 조회 되었습니다.")))
            .andExpect(jsonPath("$.patientNm").value("P123456789"))
            .andExpect(jsonPath("$.admissionDate").value("20211205"))
            .andExpect(jsonPath("$.dischargeScheduledDate").value("20221230"))
            .andExpect(jsonPath("$.personCharge").value("헬스커넥트"))
            //체온
            .andExpect(jsonPath("$.todayBpList").isArray())
            .andExpect(jsonPath("$.todayBpList[0].sbp").value("100"))
            .andExpect(jsonPath("$.todayBpList[0].dbp").value("50"))
            //혈업
            .andExpect(jsonPath("$.todayBtList").isArray())
            .andExpect(jsonPath("$.todayBtList[0].bt").value(36))
            //심박수
            .andExpect(jsonPath("$.todayHrList").isArray())
            .andExpect(jsonPath("$.todayHrList[0].hr").value(95))
            //산소포화도
            .andExpect(jsonPath("$.todaySpO2List").isArray())
            .andExpect(jsonPath("$.todaySpO2List[0].spO2").value(55))
            //걸음수
            .andExpect(jsonPath("$.todayStepCountList").isArray())
            .andExpect(jsonPath("$.todayStepCountList[0].stepCount").value(150))
            .andExpect(jsonPath("$.todayStepCountList[0].distance").value(50))
            //수면
            .andExpect(jsonPath("$.todaySleepTimeList").isArray())
            .andExpect(jsonPath("$.todayTotalSleepTime").value("0159"))
            .andExpect(jsonPath("$.todaySleepTimeList[0].sleepType").value("1"))
            .andExpect(jsonPath("$.todaySleepTimeList[0].sleepStartTime").value("2330"))
            .andExpect(jsonPath("$.todaySleepTimeList[0].sleepEndTime").value("0030"))
            .andExpect(jsonPath("$.todaySleepTimeList[1].sleepType").value("2"))
            .andExpect(jsonPath("$.todaySleepTimeList[1].sleepStartTime").value("0031"))
            .andExpect(jsonPath("$.todaySleepTimeList[1].sleepEndTime").value("0130"))
            .andDo(print());
    }
    /**
     * 메인컨텐츠 조회시 아이디가 누락되었을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenNull_whenRequestedMain_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            "}";

        mvc.perform(get("/api/main")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("사용자 아이디가 누락되었습니다."))
            .andDo(print());
    }
    /**
     * 메인 컨텐츠 조회시 로그인아이디에 맞는 입소자 아이디가 없을때
     * @throws Exception mvc.perform
     */
    @Test()
    public void givenWrongLoginId_whenRequestedMain_thenCode_99() throws Exception {
        String jsonString ="{\n" +
            " \"loginId\": \"Exception\"\n" +
            "}";
        mvc.perform(get("/api/main")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("99"))
            .andExpect(jsonPath("$.message").value("내원중인 격리/입소내역이 존재하지 않습니다."))
            .andDo(print());
    }
}