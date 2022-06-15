package kr.co.hconnect.controller;

import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.service.QnaService;
import kr.co.hconnect.vo.QnaListResponseVO;
import kr.co.hconnect.vo.QnaListSearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
     * 문의사항 리스트 조회
     *
     * @param vo 문의사항 리스트 조회 조건
     * @return QnaListResponseVO 문의사항 리스트 조회 결과
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public QnaListResponseVO selectQnaList(@Valid @RequestBody QnaListSearchVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        return qnaService.selectQnaList(vo);
    }
}
