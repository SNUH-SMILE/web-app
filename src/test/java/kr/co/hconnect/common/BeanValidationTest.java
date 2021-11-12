package kr.co.hconnect.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/config/context-*.xml", "/test-context-servlet.xml" })
public class BeanValidationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanValidationTest.class);

    private static MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(new BeanValidationController()).build();
    }

    @Test
    public void testSample1() throws Exception {
        SampleVO sampleVO = new SampleVO();
        sampleVO.setField1("field1");

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(sampleVO);

        mvc.perform(get("/validator/sample1.do")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().is4xxClientError())
            .andDo(print());
    }

    @Test
    public void testSample2() throws Exception {
        SampleVO sampleVO = new SampleVO();
        sampleVO.setField1("field1");

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(sampleVO);

        mvc.perform(get("/validator/sample2.do")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Controller
    static class BeanValidationController {

        @ResponseBody
        @GetMapping("/validator/sample1.do")
        public String sample1(@Valid @RequestBody SampleVO sampleVO) {
            LOGGER.info("field1={}, field2={}", sampleVO.getField1(), sampleVO.getField2());
            return "sample1.do";
        }

        @ResponseBody
        @GetMapping("/validator/sample2.do")
        public String sample2(@Valid @RequestBody SampleVO sampleVO, BindingResult result) {
            if (result.hasErrors()) {
                for (ObjectError error : result.getAllErrors()) {
                    LOGGER.info(error.toString());
                }
            }

            LOGGER.info("field1={}, field2={}", sampleVO.getField1(), sampleVO.getField2());
            return "sample2.do";
        }

    }

    static class SampleVO {
        private String field1;
        @NotNull
        private String field2;

        public String getField1() {
            return field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public String getField2() {
            return field2;
        }

        public void setField2(String field2) {
            this.field2 = field2;
        }
    }

}
