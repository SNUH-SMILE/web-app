package kr.co.hconnect.rest;

import kr.co.hconnect.vo.SampleVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/api/sample")
    public SampleVO doSample(@RequestBody SampleVO sampleVO) {
        return sampleVO;
    }

}
