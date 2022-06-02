package kr.co.hconnect.service;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.hconnect.repository.ItemDao;
import kr.co.hconnect.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 측정항목
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class ItemService extends EgovAbstractServiceImpl {

    private ItemDao itemDao; //측정항목 Dao

    private final EgovIdGnrService itemIdGnrService; //측정항목 Id 채번 서비스

    /**
     * 생성자
     * @param itemDao 측정항목 Dao
     * @param itemIdGnrService 측정항목 Id 채번 서비스
     */
    @Autowired
    public ItemService(ItemDao itemDao, @Qualifier("itemIdGnrService") EgovIdGnrService itemIdGnrService) {
        this.itemDao = itemDao;
        this.itemIdGnrService = itemIdGnrService;
    }

    /**
     *측정항목 리스트
     * @return 측정항목 목록
     */
    public List<ItemVO> selectItemList(ItemVO vo) {
        return itemDao.selectItemList(vo);
    }

    /**
     *측정항목 저장
     * @param vo 측정항목VO
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertItem(ItemVO vo) {
        try {
            String ItemId = itemIdGnrService.getNextStringId(); //측정항목 다음 Id
            vo.setItemId(ItemId);
        } catch (FdlException e) {
            e.printStackTrace();
        }
        itemDao.insertItem(vo);
    }

    /**
     *측정항목 수정
     * @param vo 측정항목VO
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateItem(ItemVO vo) {
        itemDao.updateItem(vo);
    }

    /**
     *측정항목 삭제
     * @param ItemId 측정항목Id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteItem(String ItemId) {
        itemDao.deleteItem(ItemId);
    }

}
