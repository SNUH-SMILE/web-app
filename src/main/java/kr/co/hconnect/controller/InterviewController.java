package kr.co.hconnect.controller;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.domain.BaseResponse;
import kr.co.hconnect.domain.SaveInformaionInfo;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.service.InterviewService;
import kr.co.hconnect.vo.InterviewListResponseByCenterVO;
import kr.co.hconnect.vo.InterviewListSearchVO;
import kr.co.hconnect.vo.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping("/api/interview")
public class InterviewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdmissionController.class);


    private final InterviewService interviewService;
    private final MessageSource messageSource;
    /**
     * 생성자
     * @param interviewService
     */
    @Autowired
    public InterviewController(InterviewService interviewService, MessageSource messageSource) {
        this.interviewService = interviewService;
        this.messageSource = messageSource;
    }

    /**
     * 문진 id별 조회
     * @param vo 문진 id별 조회 vo InterviewListSearchVO
     * @param bindingResult
     * @return responseVO<InterviewListResponseByCenterVO>
     */
    @RequestMapping(value = "/interviewList", method = RequestMethod.POST)
    public ResponseVO<InterviewListResponseByCenterVO> selectInterviewListByCentor(
        @Valid @RequestBody InterviewListSearchVO vo, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        InterviewListResponseByCenterVO interviewListResponseByCenterVO =  interviewService.selectInterview(vo);

        ResponseVO<InterviewListResponseByCenterVO> responseVO = new ResponseVO<>();
        responseVO.setResult(interviewListResponseByCenterVO);
        responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
        responseVO.setMessage("조회 성공");

        return responseVO;
    }

    /**
     * 문진 정보 / 결과 등록
     * @param saveInformaionInfo
     * @param result
     * @return BaseResponse
     * @throws FdlException
     */
    @RequestMapping(value ="/setInterview", method = RequestMethod.POST)
    public BaseResponse saveInterview(@Valid @RequestBody SaveInformaionInfo saveInformaionInfo, BindingResult result) throws FdlException {
        if (result.hasErrors()) {
            throw new InvalidRequestArgumentException(result);
        }
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ApiResponseCode.SUCCESS.getCode());
        baseResponse.setMessage(messageSource.getMessage("message.success.saveQuarantine", null, Locale.getDefault()));
        interviewService.insertInterview(saveInformaionInfo);


        return baseResponse;
    }
}
