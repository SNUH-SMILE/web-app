package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.ItemVO;
import kr.co.hconnect.vo.UserVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 측정항목
 */
@Repository
public class ItemDao extends EgovAbstractMapper {

    /**
     *측정항목 리스트 조회
     * @return 측정항목 목록
     */
    public List<ItemVO> selectItemList(ItemVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.selectItemList", vo);
    }

    /**
     *측정항목 상세 조회
     * @param vo 측정항목 조회 조건
     * @return 측정항목
     */
    public ItemVO selectItem(ItemVO vo) {
        return selectOne("kr.co.hconnect.sqlmapper.selectItemList", vo);
    }

    /**
     *측정항목 저장
     * @param vo 측정항목 VO
     */
    public void insertItem(ItemVO vo) {
        insert("kr.co.hconnect.sqlmapper.insertItem",vo);
    }

    /**
     *측정항목 수정
     * @param vo 측정항목 VO
     */
    public void updateItem(ItemVO vo) {
        update("kr.co.hconnect.sqlmapper.updateItem",vo);
    }

    /**
     *측정항목 삭제
     * @param ItemId 측정항목 Id
     */
    public void deleteItem(String ItemId) {
        update("kr.co.hconnect.sqlmapper.deleteItem",ItemId);
    }

}
