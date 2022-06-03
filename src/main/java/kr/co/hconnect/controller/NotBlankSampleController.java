package kr.co.hconnect.controller;

import kr.co.hconnect.vo.NotBlankSampleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class NotBlankSampleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotBlankSampleController.class);

    @GetMapping("/api/notBlank")
    public String notBlankSample(@Valid NotBlankSampleVO notBlankSampleVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError allError : bindingResult.getAllErrors()) {
                LOGGER.error(allError.getDefaultMessage());
            }
            return "Validation error!";
        }

        LOGGER.info(notBlankSampleVO.toString());
        return "Hello, @NotBlank annotation";
    }

}
