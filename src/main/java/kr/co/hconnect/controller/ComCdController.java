package kr.co.hconnect.controller;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.exception.DuplicateDetailCdException;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.jwt.TokenDetailInfo;
import kr.co.hconnect.service.ComCdService;
import kr.co.hconnect.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
     * 공통코드 리스트 조회
     *
     * @param vo 공통코드 조회 조건 VO
     * @return ResponseVO&lt;List&lt;ComCdVO&gt;&gt;
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseVO<List<ComCdVO>> selectComCdList(@RequestBody ComCdSearchVO vo) {
        ResponseVO<List<ComCdVO>> responseVO = new ResponseVO<>();

        responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
        responseVO.setResult(comCdService.selectComCdList(vo));

        return responseVO;
    }

    /**
     * 공통코드상세 리스트 조회
     * @param vo 공통코드상세 조회조건 VO
     * @return ResponseVO&lt;List&lt;ComCdDetailVO&gt;&gt;
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

    /**
     * 공통코드 저장/수정
     * 
     * @param vo 공통코드 저장 정보
     * @return 공통코드 저장 완료 정보
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVO<List<ComCdVO>> saveComCd(@Valid @RequestBody ComCdSaveVO vo, BindingResult bindingResult
            , @RequestAttribute TokenDetailInfo tokenDetailInfo) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        for (ComCdVO comCdVO : vo.getComCdVOList()) {
            comCdVO.setRegId(tokenDetailInfo.getId());
            comCdVO.setUpdId(tokenDetailInfo.getId());
        }

        ResponseVO<List<ComCdVO>> responseVO = new ResponseVO<>();

        try {
            comCdService.saveComCd(vo.getComCdVOList());

            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("공통코드 저장 완료");
            responseVO.setResult(comCdService.selectComCdList(vo.getComCdSearchVO()));
        } catch (FdlException e) {
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        } catch (NullPointerException e) {
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }

        return responseVO;
    }

    /**
     * 공통코드상세 저장/수정
     *
     * @param vo 공통코드상세 저장 정보
     * @return 공통코드상세 저장 완료 정보
     */
    @RequestMapping(value = "/detail/save", method = RequestMethod.POST)
    public ResponseVO<List<ComCdDetailVO>> saveComCdDetail(@Valid @RequestBody ComCdDetailSaveVO vo, BindingResult bindingResult
            , @RequestAttribute TokenDetailInfo tokenDetailInfo) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        for (ComCdDetailVO comCdDetailVO : vo.getComCdDetailVOList()) {
            comCdDetailVO.setRegId(tokenDetailInfo.getId());
            comCdDetailVO.setUpdId(tokenDetailInfo.getId());
        }

        ResponseVO<List<ComCdDetailVO>> responseVO = new ResponseVO<>();

        try {
            comCdService.saveComCdDetail(vo.getComCdDetailVOList());

            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("공통코드상세 저장 완료");
            responseVO.setResult(comCdService.selectComCdDetailList(vo.getComCdDetailSearchVO()));
        } catch (DuplicateDetailCdException e) {
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }

        return responseVO;
    }

    /**
     * 공통코드상세 순서 업데이트
     *
     * @param comCdDetailSortChangeVOList 공통코드상세 순서 변경 정보
     * @return 공통코드상세 순서 변경 완료 정보
     */
    @RequestMapping(value = "/detail/sort", method = RequestMethod.PATCH)
    public ResponseVO<List<ComCdDetailVO>> updateComCdDetailSort(
              @Valid @RequestBody List<ComCdDetailSortChangeVO> comCdDetailSortChangeVOList
            , BindingResult bindingResult
            , @RequestAttribute TokenDetailInfo tokenDetailInfo) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        for (ComCdDetailSortChangeVO vo : comCdDetailSortChangeVOList) {
            vo.setUpdId(tokenDetailInfo.getId());
        }

        ResponseVO<List<ComCdDetailVO>> responseVO = new ResponseVO<>();

        try {
            List<ComCdDetailVO> comCdDetailVOList = comCdService.updateComCdDetailSort(comCdDetailSortChangeVOList);

            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("공통코드상세 정렬순서 변경 완료");
            responseVO.setResult(comCdDetailVOList);
        } catch (NullPointerException e) {
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }

        return responseVO;
    }
}
