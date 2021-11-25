package kr.co.hconnect.rest;

import kr.co.hconnect.vo.SampleVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SampleController {

    @GetMapping("/sample")
    public SampleVO doSample(@RequestBody SampleVO sampleVO) {
        return sampleVO;
    }

}
