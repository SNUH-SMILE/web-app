package kr.co.hconnect.controller;


import kr.co.hconnect.service.ItemService;
import kr.co.hconnect.vo.ItemVO;
import kr.co.hconnect.vo.SessionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 측정항목
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    private ItemService itemService;  //측정항목서비스
    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * 측정항목 페이지 호출
     * @return 측정항목 페이지
     */
    @RequestMapping("/home.do")
    public String itemHome(){
        return "sys/item";
    }


    /**
     *측정항목 리스트 조회
     * @return 측정항목 목록
     */
    @RequestMapping("/list.ajax")
    @ResponseBody
    public List<ItemVO> selectItemList(){
        return itemService.selectItemList();
    }


    /**
     *측정항목 생성
     * @param vo 측정항목VO
     * @return 측정항목 목록
     */
    @RequestMapping("insert.ajax")
    @ResponseBody
    public List<ItemVO> insertItem(@RequestBody ItemVO vo, @SessionAttribute SessionVO sessionVO) {

        vo.setRegId(sessionVO.getUserId());
        //# 입력
        itemService.insertItem(vo);
        //# 조회
        return itemService.selectItemList();
    }

    /**
     * 측정항목 수정
     * @param vo 측정항목VO
     * @return 측정항목 목록
     */
    @RequestMapping("update.ajax")
    @ResponseBody
    public List<ItemVO> updateItem(@RequestBody ItemVO vo, @SessionAttribute SessionVO sessionVO) {

        vo.setUpdId(sessionVO.getUserId());
        //# 수정
        itemService.updateItem(vo);
        //# 조회
        return itemService.selectItemList();
    }

    /**
     * 측정항목 삭제
     * @param itemId    측정항목Id
     * @return 측정항목 목록
     */
    @RequestMapping("delete.ajax")
    @ResponseBody
    public List<ItemVO> deleteItem(@RequestParam(name = "itemId",required = false)String itemId){
        //# 삭제
        itemService.deleteItem(itemId);
        //# 조회
        return itemService.selectItemList();
    }
}
