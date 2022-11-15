package kr.co.hconnect.controller;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.VoValidationGroups;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.jwt.TokenDetailInfo;
import kr.co.hconnect.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import kr.co.hconnect.service.HealthService;
import kr.co.hconnect.service.AdmissionService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthController.class);

    private final MessageSource messageSource;

    private final HealthService healthService;

    @Autowired
    public HealthController(MessageSource messageSource, HealthService healthService) {
        this.messageSource = messageSource;
        this.healthService = healthService;
    }


    /**
     * 몸운동상태 저장
     * @param vo
     * @param bindingResult
     * @param tokenDetailInfo
     * @return
     */
    @RequestMapping(value = "/setBodyStatus", method = RequestMethod.POST)
    public ResponseBaseVO<HealthVO> inserthelth(@Validated(VoValidationGroups.add.class) @RequestBody HealthSaveVO vo
        , BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        ResponseBaseVO<HealthVO> responseVO = new ResponseBaseVO<>();

        HealthVO entity = new HealthVO();
        entity.setHealthDate(vo.getResultDate());
        entity.setStatus(vo.getResultStatus());
        entity.setRegId(vo.getLoginId());

        try{
            healthService.inserthelth(entity);
            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("저장 완료");

        }catch (Exception  e){
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }
        return responseVO;
    }

    /**
     * 몸운동상태 조회
     * @param vo
     * @param bindingResult
     * @param tokenDetailInfo
     * @return
     */
    @RequestMapping(value = "/getBodyStatus", method = RequestMethod.POST)
    public ResponseBaseVO<HealthResponseVO> selectHhelth(@Validated(VoValidationGroups.add.class) @RequestBody HealthSearchVO vo
        , BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        ResponseBaseVO<HealthResponseVO> responseVO = new ResponseBaseVO<>();

        HealthResponseVO entity = new HealthResponseVO();

        try{
            entity = healthService.selectHelth(vo);
            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("저장 완료");
            responseVO.setResult(entity);

        }catch (Exception  e){
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }
        return responseVO;
    }
}
