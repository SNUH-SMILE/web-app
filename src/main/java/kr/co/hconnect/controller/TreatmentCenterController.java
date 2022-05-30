package kr.co.hconnect.controller;


import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.jwt.TokenDetailInfo;
import kr.co.hconnect.service.TreatmentCenterService;
import kr.co.hconnect.vo.ResponseVO;
import kr.co.hconnect.vo.TreatmentCenterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 생활치료센터
 */
@RestController
@RequestMapping("/api/treatmentCenter")
public class TreatmentCenterController {

    /**
     * 생활치료센터 서비스
     */
    private final TreatmentCenterService treatmentCenterService;

    /**
     * 생성자
     * @param treatmentCenterService 생활치료센터 서비스
     */
    @Autowired
    public TreatmentCenterController(TreatmentCenterService treatmentCenterService) {
        this.treatmentCenterService = treatmentCenterService;
    }
    
    // C: 입력, insert + 메서드, add +
    // R: 조회, select + 메서드, get +
    // U: 수정, update + 메서드, modify +
    // D: 삭제, delete + 메서드, remove +
    // C+U: 저장, save + 메서드

    /**
     * 생활치료센터 리스트 검색
     * @return 생활치료센터 목록
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO<List<TreatmentCenterVO>> selectTreatmentCenterList(@Valid @RequestBody TreatmentCenterVO vo
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        ResponseVO<List<TreatmentCenterVO>> responseVO = new ResponseVO<>();

        try {
            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("조회성공");
            responseVO.setResult(treatmentCenterService.selectTreatmentCenterList(vo));
        } catch (RuntimeException e) {
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }

        return responseVO;
    }

    /**
     * 생활치료센터 입력
     * @param vo 생활치료센터VO
     * @return 생활치료센터 목록
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public List<TreatmentCenterVO> insertTreatmentCenter(@RequestBody TreatmentCenterVO vo
            , @RequestAttribute TokenDetailInfo tokenDetailInfo) {

        vo.setRegId(tokenDetailInfo.getId());

        //# 입력
        treatmentCenterService.insertTreatmentCenter(vo);
        //# 조회
        return treatmentCenterService.selectTreatmentCenterList(vo);
    }

    /**
     * 생활치료센터 수정
     * @param vo 생활치료센터VO
     * @return 생활치료센터 목록
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public List<TreatmentCenterVO> updateTreatmentCenter(@RequestBody TreatmentCenterVO vo
            , @RequestAttribute TokenDetailInfo tokenDetailInfo) {

        vo.setUpdId(tokenDetailInfo.getId());

        //# 수정
        treatmentCenterService.updateTreatmentCenter(vo);

        //# 목록조회
        return treatmentCenterService.selectTreatmentCenterList(vo);
    }

    /**
     *  생활치료센터 삭제
     * @param centerId 생활치료센터 ID
     * @return 생활치료센터 목록
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public List<TreatmentCenterVO> deleteTreatmentCenter(@RequestParam(name="centerId") String centerId){
        //# 삭제
        treatmentCenterService.deleteTreatmentCenter(centerId);
        //# 목록조회
        return treatmentCenterService.selectTreatmentCenterList(null);
    }

}
