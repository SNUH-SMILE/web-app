package kr.co.hconnect.controller;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.service.ComCdService;
import kr.co.hconnect.vo.ComCdDetailSearchVO;
import kr.co.hconnect.vo.ComCdDetailVO;
import kr.co.hconnect.vo.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/comCd")
public class ComCdController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComCdController.class);

    /**
     * 공통코드관리 Service
     */
    private final ComCdService comCdService;

    /**
     * 생성자
     *
     * @param comCdService 공통코드관리 Service
     */
    public ComCdController(ComCdService comCdService) {
        this.comCdService = comCdService;
    }

    /**
     * 공통코드상세 리스트 조회
     * @param vo 공통코드상세 조회조건 VO
     */
    @RequestMapping(value = "/detail/list", method = RequestMethod.POST)
    public ResponseVO<List<ComCdDetailVO>> selectComCdDetailList(@Valid @RequestBody ComCdDetailSearchVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        ResponseVO<List<ComCdDetailVO>> responseVO = new ResponseVO<>();

        responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
        responseVO.setResult(comCdService.selectComCdDetailList(vo));

        return responseVO;
    }
}
