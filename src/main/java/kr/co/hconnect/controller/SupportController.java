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
import kr.co.hconnect.service.SupportService;
import kr.co.hconnect.service.AdmissionService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/support")
public class SupportController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SupportController.class);
    private final MessageSource messageSource;

    private final SupportService supportService;
    /**
     * 격리/입소내역 관리 Service
     */
    private final AdmissionService admissionService;

    @Autowired
    public SupportController(MessageSource messageSource
        ,AdmissionService admissionService
        ,SupportService supportService) {
        this.messageSource = messageSource;
        this.admissionService = admissionService;
        this.supportService = supportService;
    }
    /**
     * 공지사항 알림 리스트
     * @param vo
     * @return
     */
    @RequestMapping(value = "/noticeList", method = RequestMethod.POST)
    public ResponseNoticeVO<List<NoticeSupportListVO>> selectㅜoticeList(@Valid @RequestBody NoticeSupportSearchVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        ResponseNoticeVO<List<NoticeSupportListVO>> responseVO = new ResponseNoticeVO<>();


        try{
            List<NoticeSupportListVO> dt = supportService.selectNoticeList(vo);
            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("조회성공");
            responseVO.setNoticeList(dt);
        } catch(RuntimeException e){
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }

        return responseVO;
    }

    /**
     * 문의내역 저장
     *
         * @param vo
     * @return
         */
    @RequestMapping(value = "/setQuestion", method = RequestMethod.POST)
    public ResponseBaseVO<QnaSaveVO> insertQna(@Validated(VoValidationGroups.add.class) @RequestBody QnaSaveVO vo
        , BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        ResponseBaseVO<QnaSaveVO> responseVO = new ResponseBaseVO<>();

        try{
            supportService.insertQna(vo);
            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("저장 완료");

        }catch (Exception  e){
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }
        return responseVO;
    }

    /**
     * 모바일 문의내역 조회 - 아이디별로
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/questionList", method = RequestMethod.POST)
    public ReponseQuestionListVO<List<QnaListVO>> QnaSearchList(@Validated(VoValidationGroups.add.class) @RequestBody QnaSearchListVO vo
        , BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        ReponseQuestionListVO<List<QnaListVO>> responseVO = new ReponseQuestionListVO<>();

        try{
            List<QnaListVO> dt = supportService.selectQnaList(vo);
            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("조회 완료");
            responseVO.setQuestionList(dt);

        }catch (Exception  e){
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }
        return responseVO;
    }
}
