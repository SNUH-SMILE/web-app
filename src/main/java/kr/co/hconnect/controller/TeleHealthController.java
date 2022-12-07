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
import kr.co.hconnect.service.TeleHealthService;
import kr.co.hconnect.service.AdmissionService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/teleHealth")
public class TeleHealthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeleHealthController.class);

    private final MessageSource messageSource;

    private final TeleHealthService teleHealthService;

    @Autowired
    public TeleHealthController(MessageSource messageSource, TeleHealthService teleHealthService) {
        this.messageSource = messageSource;
        this.teleHealthService = teleHealthService;
    }

    @RequestMapping(value = "/getTeleHealth", method = RequestMethod.POST)
    public ResponseBaseVO<TeleHealthConnectVO> selectConnection(@Validated(VoValidationGroups.add.class) @RequestBody TeleHealthConnectVO vo
        , BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        ResponseBaseVO<TeleHealthConnectVO> responseVO = new ResponseBaseVO<>();
        try{
            vo.setLoginId(tokenDetailInfo.getId());
            TeleHealthConnectVO dt = teleHealthService.selectConnection(vo);
            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("저장 완료");
            responseVO.setResult(dt);

        }catch (Exception  e){
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }
        return responseVO;
    }
    /**
     *
     */
    @RequestMapping(value = "/getSubScriberToken", method = RequestMethod.POST)
    public ResponseBaseVO<TeleHealthConnectVO> getsubToken(@Validated(VoValidationGroups.add.class) @RequestBody TeleHealthConnectVO vo
        , BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        ResponseBaseVO<TeleHealthConnectVO> responseVO = new ResponseBaseVO<>();
        try{

            TeleHealthConnectVO dt = teleHealthService.getSubscriberToken(vo);
            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("구독토큰발급완료");
            responseVO.setResult(dt);

        }catch (Exception  e){
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }
        return responseVO;
    }

}
