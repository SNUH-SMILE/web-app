package kr.co.hconnect.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.RestControllerExceptionHandler;
import kr.co.hconnect.common.TokenStatus;
import kr.co.hconnect.common.TokenType;
import kr.co.hconnect.jwt.TokenDetailInfo;
import kr.co.hconnect.service.ItemService;
import kr.co.hconnect.vo.ItemSaveVO;
import kr.co.hconnect.vo.ItemVO;
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
import java.util.Locale;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/config/context-*.xml", "/test-context-servlet.xml" })
@Transactional
public class ItemControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemControllerTest.class);

    private static MockMvc mvc;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RestControllerExceptionHandler restControllerExceptionHandler;

    /**
     * 측정항목 서비스
     */
    @Autowired
    private ItemService itemService;

    @Before
    public void setMockMvc() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("/sql-script/beforeSetItemControllerTest.sql"));
        resourceDatabasePopulator.execute(dataSource);

        TokenDetailInfo tokenDetailInfo = new TokenDetailInfo();
        tokenDetailInfo.setTokenStatus(TokenStatus.OK);
        tokenDetailInfo.setTokenType(TokenType.WEB);
        tokenDetailInfo.setId("admin");

        mvc = MockMvcBuilders
                .standaloneSetup(new ItemController(messageSource, itemService))
                .defaultRequest(get("/").requestAttr("tokenDetailInfo", tokenDetailInfo))
                .setControllerAdvice(restControllerExceptionHandler)
                .build();
    }

    /**
     * 측정항목 리스트 조회 - 정상확인
     */
    @Test
    public void selectItemList_thenSuccess() throws Exception {
        String data =
                "{\n" +
                        "   \"itemId\":\"\",\n" +
                        "   \"itemNm\":\"\"\n" +
                        "}";

        mvc.perform(post("/api/item/list")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                .andDo(print());
    }

    /**
     * 측정항목 상세 조회 - 정상 확인(I0001-체온 데이터 확인)
     */
    @Test
    public void selectItem_thenSuccess() throws Exception {
        String data =
                "{\n" +
                        "   \"itemId\":\"I0001\"\n" +
                        "}";

        mvc.perform(post("/api/item/info")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                .andExpect(jsonPath("$.result.itemId", is("I0001")))
                .andDo(print());
    }

    /**
     * 측정항목 상세 조회 - ItemId 조회조건 누락 확인
     */
    @Test
    public void selectItem_thenCheckItemId() throws Exception {
        String data =
                "{\n" +
                        "   \"itemId\":\"\"\n" +
                        "}";

        mvc.perform(post("/api/item/info")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode())))
                .andExpect(jsonPath("$.message", is(messageSource.getMessage("validation.null.itemId"
                        , null, Locale.getDefault()))))
                .andDo(print());
    }

    /**
     * 신규 아이템 생성 - 정상확인
     */
    @Test
    public void insertItem_thenSuccess() throws Exception {
        String data =
                "{\n" +
                        "   \"searchInfo\":{\n" +
                        "      \"itemId\":\"\",\n" +
                        "      \"itemNm\":\"\"\n" +
                        "   },\n" +
                        "   \"itemNm\":\"JUNIT-ITEM\",\n" +
                        "   \"unit\":\"J\",\n" +
                        "   \"refFrom\":\"10\",\n" +
                        "   \"refTo\":\"20\"\n" +
                        "}";

        mvc.perform(put("/api/item/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                // 신규 저장된 ITEM_NM 명칭 동일여부 확인
                .andExpect(jsonPath("$.result.data.itemNm", is("JUNIT-ITEM")))
                .andDo(print());
    }

    /**
     * 신규 아이템 생성 - 아이템 명칭 누락 확인
     */
    @Test
    public void insertItem_thenCheckItemNm() throws Exception {
        String data =
                "{\n" +
                        "   \"searchInfo\":{\n" +
                        "      \"itemId\":\"\",\n" +
                        "      \"itemNm\":\"\"\n" +
                        "   },\n" +
                        "   \"itemNm\":\"\",\n" +
                        "   \"unit\":\"J\",\n" +
                        "   \"refFrom\":\"null\",\n" +
                        "   \"refTo\":\"20\"\n" +
                        "}";

        mvc.perform(put("/api/item/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode())))
                .andExpect(jsonPath("$.message", containsString("validation.null.itemNm")))
                .andDo(print());
    }

    /**
     * 신규 아이템 생성 - 아이템 명칭 누락 확인
     */
    @Test
    public void insertItem_thenCheckUnit() throws Exception {
        String data =
                "{\n" +
                "   \"searchInfo\":{\n" +
                "      \"itemId\":\"\",\n" +
                "      \"itemNm\":\"\"\n" +
                "   },\n" +
                "   \"itemNm\":\"JUNIT-ITEM\",\n" +
                "   \"unit\":\"\",\n" +
                "   \"refFrom\":\"null\",\n" +
                "   \"refTo\":\"20\"\n" +
                "}";

        mvc.perform(put("/api/item/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode())))
                .andExpect(jsonPath("$.message", containsString("validation.null.itemUnit")))
                .andDo(print());
    }

    /**
     * 아이템 수정 - 정상 확인 (T-001 테스트용 임시 항목)
     */
    @Test
    public void updateItem_thenSuccess() throws Exception {
        ItemSaveVO itemSaveVO = new ItemSaveVO();
        itemSaveVO.setSearchInfo(new ItemVO());
        itemSaveVO.setItemId("T-001");
        itemSaveVO.setItemNm("UPDATE001");
        itemSaveVO.setUnit("UU");
        itemSaveVO.setRefFrom(5);
        itemSaveVO.setRefTo(10);

        String data = new ObjectMapper().writeValueAsString(itemSaveVO);

        mvc.perform(patch("/api/item/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                .andExpect(jsonPath("$.result.data.itemId", is(itemSaveVO.getItemId()) ))
                .andExpect(jsonPath("$.result.data.itemNm", is(itemSaveVO.getItemNm()) ))
                .andExpect(jsonPath("$.result.data.unit", is(itemSaveVO.getUnit()) ))
                .andDo(print());
    }

    /**
     * 아이템 수정 - 아이템명 누락 확인
     */
    @Test
    public void updateItem_thenCheckItemNm() throws Exception {
        ItemSaveVO itemSaveVO = new ItemSaveVO();
        itemSaveVO.setSearchInfo(new ItemVO());
        itemSaveVO.setItemId("T-001");
        itemSaveVO.setItemNm("");
        itemSaveVO.setUnit("UU");
        itemSaveVO.setRefFrom(5);
        itemSaveVO.setRefTo(10);

        String data = new ObjectMapper().writeValueAsString(itemSaveVO);

        mvc.perform(patch("/api/item/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode())))
                .andExpect(jsonPath("$.message", containsString("validation.null.itemNm")))
                .andDo(print());
    }

    /**
     * 아이템 수정 - 아이템명 길이 확인
     */
    @Test
    public void updateItem_thenCheckItemNmLength() throws Exception {
        ItemSaveVO itemSaveVO = new ItemSaveVO();
        itemSaveVO.setSearchInfo(new ItemVO());
        itemSaveVO.setItemId("T-001");
        itemSaveVO.setItemNm("아이템 명길 이확인아이템 명길 이확인아이템 명길 이확인아이템 명길 이확인아이템 명길 이확인1");
        itemSaveVO.setUnit("UU");
        itemSaveVO.setRefFrom(5);
        itemSaveVO.setRefTo(10);

        String data = new ObjectMapper().writeValueAsString(itemSaveVO);

        mvc.perform(patch("/api/item/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode())))
                .andExpect(jsonPath("$.message", containsString("validation.size.itemNm")))
                .andDo(print());
    }

    /**
     * 아이템 삭제 - 정상 확인
     */
    @Test
    public void deleteItem_thenSuccess() throws Exception {
        ItemSaveVO itemSaveVO = new ItemSaveVO();
        itemSaveVO.setSearchInfo(new ItemVO());
        itemSaveVO.setItemId("T-002");

        String data = new ObjectMapper().writeValueAsString(itemSaveVO);

        mvc.perform(delete("/api/item/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(ApiResponseCode.SUCCESS.getCode())))
                .andDo(print());
    }
}
