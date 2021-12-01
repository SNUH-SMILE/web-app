package kr.co.hconnect.rest;

import kr.co.hconnect.vo.SampleVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SampleRestController {

    @GetMapping("/api/sample")
    public SampleVO doSample(@Valid @RequestBody SampleVO sampleVO) {
        return sampleVO;
    }
}
