package kr.co.hconnect.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.RestControllerExceptionHandler;
import kr.co.hconnect.common.TokenStatus;
import kr.co.hconnect.common.TokenType;
import kr.co.hconnect.jwt.TokenDetailInfo;
import kr.co.hconnect.service.ComCdService;
import org.junit.Assert;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/config/context-*.xml", "/test-context-servlet.xml" })
@Transactional
public class ComCdControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComCdControllerTest.class);

    private static MockMvc mvc;

    /**
     * 공통코드관리 Service
     */
    @Autowired
    private ComCdService comCdService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private RestControllerExceptionHandler restControllerExceptionHandler;

    @Before
    public void setMockMvc() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("/sql-script/beforeSetComCdControllerTest.sql"));
        resourceDatabasePopulator.execute(dataSource);

        TokenDetailInfo tokenDetailInfo = new TokenDetailInfo();
        tokenDetailInfo.setTokenStatus(TokenStatus.OK);
        tokenDetailInfo.setTokenType(TokenType.WEB);
        tokenDetailInfo.setId("admin");

        mvc = MockMvcBuilders
                .standaloneSetup(new ComCdController(comCdService))
                .defaultRequest(get("/").requestAttr("tokenDetailInfo", tokenDetailInfo))
                .setControllerAdvice(restControllerExceptionHandler)
                .build();
    }

    /**
     * 공통코드 리스트 조회 - 정상확인
     */
    @Test
    public void selectComCdList_thenSuccess() throws Exception {
        String data =
                "{\n" +
                "    \"comCd\":\"\",\n" +
                "    \"comCdNm\":\"\",\n" +
                "    \"useYn\":\"\"\n" +
                "}";

        mvc.perform(post("/api/comCd/list")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                .andDo(print());
    }

    /**
     * 공통코드상세 리스트 조회 - 정상확인
     */
    @Test
    public void selectComCdDetailList_thenSuccess() throws Exception {
        String data =
                "{\n" +
                "    \"comCd\":\"CD001\",\n" +
                "    \"detailCd\":\"\",\n" +
                "    \"useYn\":\"\"\n" +
                "}";

        mvc.perform(post("/api/comCd/detail/list")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                .andDo(print());
    }

    /**
     * 공통코드상세 리스트 조회 - 공통코드 누락 확인
     */
    @Test
    public void selectComCdDetailList_thenCheckComCd() throws Exception {
        String data =
                "{\n" +
                        "    \"comCd\":\"\",\n" +
                        "    \"detailCd\":\"\",\n" +
                        "    \"useYn\":\"\"\n" +
                        "}";

        mvc.perform(post("/api/comCd/detail/list")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode())))
                .andExpect(jsonPath("$.message", containsString("validation.null.comCd")))
                .andDo(print());
    }

    /**
     * 공통 코드 및 공통코드 상세 저장 - 정상 확인
     */
    @Test
    public void insertComCdAndComCdDetail_thenSuccess() throws Exception {
        String comCdSaveData =
                "{\n" +
                "    \"comCdSearchVO\": {\n" +
                "        \"comCd\":\"\",\n" +
                "        \"comCdNm\":\"JUNIT-TEST\",\n" +
                "        \"useYn\":\"\"\n" +
                "    },\n" +
                "    \"comCdVOList\": [\n" +
                "        {\n" +
                "            \"cudFlag\":\"C\",\n" +
                "            \"comCd\":\"\",\n" +
                "            \"comCdNm\":\"JUNIT-TEST\",\n" +
                "            \"comCdDiv\":\"COM\",\n" +
                "            \"useYn\":\"Y\",\n" +
                "            \"remark\":\"JUNIT-TEST\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        // 1. 공통코드(COM_CD) 신규 생성
        MvcResult resultComCd = mvc.perform(post("/api/comCd/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(comCdSaveData))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                .andDo(print())
                .andReturn();

        DocumentContext documentContext = JsonPath.parse(resultComCd.getResponse().getContentAsString());

        Assert.assertThat(documentContext.read("$.result.length()"), not(0));

        String comCd = documentContext.read("$.result[0].comCd");

        // 2. 공통코드세부(COM_CD_DETAIL) 생성
        String comCdDetailSaveData =
                "{\n" +
                "    \"comCdDetailSearchVO\": {\n" +
                "        \"comCd\":\"" + comCd + "\",\n" +
                "        \"detailCd\":\"\",\n" +
                "        \"useYn\":\"\"\n" +
                "    },\n" +
                "    \"comCdDetailVOList\": [\n" +
                "        {\n" +
                "            \"cudFlag\":\"C\",\n" +
                "            \"comCd\":\"" + comCd + "\",\n" +
                "            \"detailCd\":\"1\",\n" +
                "            \"detailCdNm\":\"1\",\n" +
                "            \"useYn\":\"Y\",\n" +
                "            \"property1\":\"\",\n" +
                "            \"property2\":\"\",\n" +
                "            \"property3\":\"\",\n" +
                "            \"property4\":\"\",\n" +
                "            \"property5\":\"\",\n" +
                "            \"remark\":\"\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"cudFlag\":\"C\",\n" +
                "            \"comCd\":\"" + comCd + "\",\n" +
                "            \"detailCd\":\"2\",\n" +
                "            \"detailCdNm\":\"2\",\n" +
                "            \"useYn\":\"Y\",\n" +
                "            \"property1\":\"2\",\n" +
                "            \"property2\":\"2\",\n" +
                "            \"property3\":\"2\",\n" +
                "            \"property4\":\"2\",\n" +
                "            \"property5\":\"2\",\n" +
                "            \"remark\":\"2\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"cudFlag\":\"C\",\n" +
                "            \"comCd\":\"" + comCd + "\",\n" +
                "            \"detailCd\":\"3\",\n" +
                "            \"detailCdNm\":\"3\",\n" +
                "            \"useYn\":\"Y\",\n" +
                "            \"property1\":\"1\",\n" +
                "            \"property2\":\"1\",\n" +
                "            \"property3\":\"1\",\n" +
                "            \"property4\":\"1\",\n" +
                "            \"property5\":\"1\",\n" +
                "            \"remark\":\"1\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        mvc.perform(post("/api/comCd/detail/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(comCdDetailSaveData))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                // 신규 추가된 공통코드상세 데이터 건수 확인
                .andExpect(jsonPath("$.result.length()", is(3)))
                .andDo(print())
                .andReturn();
    }

    /**
     * 공통코드 상세 저장 - 중복 데이터 체크
     */
    @Test
    public void insertComCdDetail_thenDupDetailCd() throws Exception {
        String comCdDetailSaveData =
                "{\n" +
                "    \"comCdDetailSearchVO\": {\n" +
                "        \"comCd\":\"JUINT\",\n" +
                "        \"detailCd\":\"\",\n" +
                "        \"useYn\":\"\"\n" +
                "    },\n" +
                "    \"comCdDetailVOList\": [\n" +
                "        {\n" +
                "            \"cudFlag\":\"C\",\n" +
                "            \"comCd\":\"JUINT\",\n" +
                "            \"detailCd\":\"JUNIT-DUP-TEST\",\n" +
                "            \"detailCdNm\":\"1\",\n" +
                "            \"useYn\":\"Y\",\n" +
                "            \"property1\":\"1\",\n" +
                "            \"property2\":\"1\",\n" +
                "            \"property3\":\"1\",\n" +
                "            \"property4\":\"1\",\n" +
                "            \"property5\":\"1\",\n" +
                "            \"remark\":\"1\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        mvc.perform(post("/api/comCd/detail/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(comCdDetailSaveData))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode())))
                .andExpect(jsonPath("$.message", is(messageSource.getMessage("message.duplicate.detailCd"
                        , new Object[] { "JUNIT-DUP-TEST" }, Locale.getDefault()))))
                .andDo(print())
                .andReturn();
    }

}
