package kr.co.hconnect.common;

import kr.co.hconnect.rest.SampleRestController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class JsonConverterTest {

    private static MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(new SampleRestController()).build();
    }

    /**
     * REST API를 서비스의 VO 속성과 사용하는 클라이언트 VO 속성 명칭이 다른 경우 @JsonProperty를 이용해 변환처리 테스트
     */
    @Test
    public void givenDifferentNameVO_whenSerializing_thenCorrect() throws Exception {
        String jsonString = "{\n" +
            "  \"f1\": \"Hello, REST API\",\n" +
            "  \"f2\": \"20211225\",\n" +
            "  \"f3\": \"105700\"\n" +
            "}";

        mvc.perform(get("/api/sample")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.f1").value("Hello, REST API"))
            .andExpect(jsonPath("$.f2").value("20211225"))
            .andExpect(jsonPath("$.f3").value("105700"))
            .andDo(print());
    }

}
