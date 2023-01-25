package kr.co.hconnect.controller;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.VoValidationGroups;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.jwt.TokenDetailInfo;
import kr.co.hconnect.service.QnaService;
import kr.co.hconnect.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qna")
public class QnaController {

    /**
     * 문의사항 Service
     */
    private final QnaService qnaService;

    /**
     * 생성자
     *
     * @param qnaService 문의사항 Service
     */
    @Autowired
    public QnaController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    /**
     * 문의사항 내역 조회
     *
     * @param questionSeq 문의순번
     * @return ResponseVO&lt;QnaVO&gt; 문의사항 조회 결과
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseVO<QnaVO> selectQna(@RequestParam Integer questionSeq) {
        ResponseVO<QnaVO> responseVO = new ResponseVO<>();

        responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
        responseVO.setMessage("조회 성공");
        responseVO.setResult(qnaService.selectQna(questionSeq));

        return responseVO;
    }

    /**
     * 문의사항 리스트 조회
     *
     * @param vo 문의사항 리스트 조회 조건
     * @return ResponseVO&lt;QnaListResponseVO&gt; 문의사항 리스트 조회 결과
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseVO<QnaListResponseVO> selectQnaList(@RequestBody QnaListSearchVO vo) {
        ResponseVO<QnaListResponseVO> responseVO = new ResponseVO<>();
        responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
        responseVO.setMessage("조회 성공");
        responseVO.setResult(qnaService.selectQnaList(vo));

        return responseVO;
    }

    /**
     * 문의사항 답변 등록
     *
     * @param vo 답변 정보
     * @return ResponseVO&lt;QnaListResponseVO&gt; 문의사항 리스트 조회 결과
     */
    @RequestMapping(value = "/save/reply", method = RequestMethod.PATCH)
    public ResponseVO<QnaListResponseVO> updateQnaByReply(
            @Validated(VoValidationGroups.modify.class) @RequestBody QnaSaveByReplyVO vo
            , BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        vo.setReplyId(tokenDetailInfo.getId());

        qnaService.updateQnaByReply(vo);

        //푸시서비스


        ResponseVO<QnaListResponseVO> responseVO = new ResponseVO<>();
        responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
        responseVO.setMessage("저장 성공");
        responseVO.setResult(qnaService.selectQnaList(vo.getQnaListSearchVO()));

        return responseVO;
    }

    /**
     * 문의사항 답변 삭제
     *
     * @param vo 답변 정보
     * @return ResponseVO&lt;QnaListResponseVO&gt; 문의사항 리스트 조회 결과
     */
    @RequestMapping(value = "/save/reply", method = RequestMethod.DELETE)
    public ResponseVO<QnaListResponseVO> deleteQnaByReply(
            @Validated(VoValidationGroups.delete.class) @RequestBody QnaSaveByReplyVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        qnaService.deleteQnaByReply(vo.getQuestionSeq());

        ResponseVO<QnaListResponseVO> responseVO = new ResponseVO<>();
        responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
        responseVO.setMessage("삭제 성공");
        responseVO.setResult(qnaService.selectQnaList(vo.getQnaListSearchVO()));

        return responseVO;
    }

}
