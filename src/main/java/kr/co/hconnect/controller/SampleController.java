package kr.co.hconnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 샘플 대쉬보드 컨트롤러
 */
@Controller
@RequestMapping("/sample")
public class SampleController {
    /**
     * 샘플 대쉬보드 페이지 호출
     * @return 측정항목 페이지
     */
    @RequestMapping("/home.do")
    public String itemHome(){
        return "sample/sample";
    }
}
