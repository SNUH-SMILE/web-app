package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.EquipmentVO;
import kr.co.hconnect.vo.UserVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 장비
 */
@Repository
public class EquipmentDao extends EgovAbstractMapper {

    /**
     *장비 리스트 조회
     * @return
     */
    public List<EquipmentVO> selectEquipmentList() {
        return selectList("kr.co.hconnect.sqlmapper.selectEquipmentList");
    }

    /**
     *장비 저장
     * @param vo 장비VO
     */
    public void saveEquipment(EquipmentVO vo) {
        insert("kr.co.hconnect.sqlmapper.insertEquipment",vo);
    }

    /**
     *장비 수정
     * @param vo 장비VO
     */
    public void updateEquipment(EquipmentVO vo) {
        update("kr.co.hconnect.sqlmapper.updateEquipment",vo);
    }

    /**
     *장비 삭제
     * @param equipId 장비Id
     */
    public void deleteEquipment(String equipId) {
        delete("kr.co.hconnect.sqlmapper.deleteEquipment",equipId);
    }


    /**
     * 장비 중복체크(입력&수정전에 체크용)
     * @param equipId 장비Id
     * @return count 로 반환
     */
    public int checkEquipment(String equipId) {
        return selectOne("kr.co.hconnect.sqlmapper.checkEquipmentId",equipId);
    }
}
