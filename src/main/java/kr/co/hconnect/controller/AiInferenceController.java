package kr.co.hconnect.controller;

import jdk.nashorn.internal.codegen.ApplySpecialization;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.VoValidationGroups;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.jwt.TokenDetailInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import kr.co.hconnect.service.AiInferenceService;
import kr.co.hconnect.service.AdmissionService;
import kr.co.hconnect.vo.*;


@RestController
@RequestMapping("/api/AiInference")
public class AiInferenceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplySpecialization.class);

    private final AiInferenceService aiService;

    @Autowired
    public AiInferenceController(AiInferenceService aiService) {
        this.aiService = aiService;
    }

    @RequestMapping(value = "/setInference", method = RequestMethod.POST)
    public ResponseVO<AiInferenceVO> insertInference(@Validated(VoValidationGroups.add.class) @RequestBody AiInferenceVO vo
        , BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        ResponseVO<AiInferenceVO> responseVO = new ResponseVO<>();

        try{
            String drugSeq = aiService.insertInference(vo);
            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("저장 완료");

        }catch (Exception  e){
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }
        return responseVO;
    }



}
