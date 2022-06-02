package kr.co.hconnect.controller;


import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.VoValidationGroups;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.jwt.TokenDetailInfo;
import kr.co.hconnect.service.ItemService;
import kr.co.hconnect.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

/**
 * 측정항목
 */
@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final MessageSource messageSource;
    /**
     * 측정항목 서비스
     */
    private ItemService itemService;

    /**
     * 생성자
     * @param messageSource MessageSource
     * @param itemService 측정항목 서비스
     */
    @Autowired
    public ItemController(MessageSource messageSource, ItemService itemService) {
        this.messageSource = messageSource;
        this.itemService = itemService;
    }


    // C: 입력, insert + 메서드, add +
    // R: 조회, select + 메서드, get +
    // U: 수정, update + 메서드, modify +
    // D: 삭제, delete + 메서드, remove +
    // C+U: 저장, save + 메서드


    /**
     *측정항목 리스트 조회
     * @return 측정항목 목록
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseVO<List<ItemVO>> selectItemList(@RequestBody ItemVO vo){

        ResponseVO<List<ItemVO>> responseVO = new ResponseVO<>();

        try {
            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("조회성공");
            responseVO.setResult(itemService.selectItemList(vo));
        } catch (RuntimeException e){
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }

        return responseVO;
    }


    /**
    *측정항목 상세 조회
    * @param vo 측정항목VO
    * @return 측정항목
    */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public  ResponseVO<ItemVO> insertItem(@RequestBody ItemVO vo) {

        ResponseVO<ItemVO> responseVO = new ResponseVO<>();

        if (StringUtils.isEmpty(vo.getItemId())) {
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(messageSource.getMessage("validation.null.itemId"
                , null, Locale.getDefault()));
        } else {
            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("조회성공");
            responseVO.setResult(itemService.selectItem(vo));
        }

        return responseVO;
    }
     /**
     * 측정항목 생성
     * @param vo 측정항목VO
     * @return 측정항목 목록
     */
    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public ResponseVO<ItemSaveCompleteVO> insertItem(
          @Validated(VoValidationGroups.add.class) @RequestBody ItemSaveVO vo
        , @RequestAttribute TokenDetailInfo tokenDetailInfo, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        ResponseVO<ItemSaveCompleteVO> responseVO = new ResponseVO<>();

        // 저장정보 구성
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(vo, itemVO);
        itemVO.setRegId(tokenDetailInfo.getId());

        try {
            // 생성
            String itemId = itemService.insertItem(itemVO);

            // 반환정보 구성
            ItemSaveCompleteVO itemSaveCompleteVO = new ItemSaveCompleteVO();
            itemVO = new ItemVO();
            itemVO.setItemId(itemId);
            itemSaveCompleteVO.setData(itemService.selectItem(itemVO));
            itemSaveCompleteVO.setList(itemService.selectItemList(vo.getSearchInfo()));

            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("생성성공");
            responseVO.setResult(itemSaveCompleteVO);
        } catch (FdlException e) {
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }

        return responseVO;
    }
    //
    // /**
    //  * 측정항목 수정
    //  * @param vo 측정항목VO
    //  * @return 측정항목 목록
    //  */
    // @RequestMapping("update.ajax")
    // @ResponseBody
    // public List<ItemVO> updateItem(@RequestBody ItemVO vo, @SessionAttribute SessionVO sessionVO) {
    //
    //     vo.setUpdId(sessionVO.getUserId());
    //     //# 수정
    //     itemService.updateItem(vo);
    //     //# 조회
    //     return itemService.selectItemList();
    // }
    //
    // /**
    //  * 측정항목 삭제
    //  * @param itemId    측정항목Id
    //  * @return 측정항목 목록
    //  */
    // @RequestMapping("delete.ajax")
    // @ResponseBody
    // public List<ItemVO> deleteItem(@RequestParam(name = "itemId",required = false)String itemId){
    //     //# 삭제
    //     itemService.deleteItem(itemId);
    //     //# 조회
    //     return itemService.selectItemList();
    // }
}
