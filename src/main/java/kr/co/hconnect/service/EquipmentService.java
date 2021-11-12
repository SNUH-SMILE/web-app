package kr.co.hconnect.service;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.repository.EquipmentDao;
import kr.co.hconnect.vo.EquipmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 장비
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class EquipmentService extends EgovAbstractServiceImpl {

    private EquipmentDao equipmentDao; //장비 Dao

    /**
     * 생성자
     * @param equipmentDao 장비 Dao
     */
    @Autowired
    public EquipmentService(EquipmentDao equipmentDao) {
        this.equipmentDao = equipmentDao;
    }

    /**
     * 장비 정보 조회
     * @return 장비목록
     */
    public List<EquipmentVO> selectEquipmentList() {
        return equipmentDao.selectEquipmentList();
    }

    /**
     * 장비 정보 저장&업데이트
     * @param vo 장비VO
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveEquipment(EquipmentVO vo) {
        //장비채번Id
        String id = vo.getEquipId();
        //해당 장비Id가 입력인지 수정인지 조회
        int checkResult = equipmentDao.checkEquipment(id);

        // Id 조회해서 있으면 수정 없으면 입력하려고 작성
        //입력
        if(checkResult==0){
            // 장비 정보 입력
            equipmentDao.saveEquipment(vo);

        }
        //수정
        else{
            // 장비 정보 수정
            equipmentDao.updateEquipment(vo);
        }


    }

    /**
     * 장비 정보 삭제
     * @param equipId 장비 Id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteEquipment(String equipId) {
        equipmentDao.deleteEquipment(equipId);
    }

    /**
     * 신규 장비 생성시 아이디 중복 체크
     * @param equipmentId 장비 아이디
     * @return 아이디가 있으면 1 없으면 0
     */
    @Transactional(rollbackFor = Exception.class)
    public int duplicateCheckEquipment(String equipmentId) {

        return equipmentDao.checkEquipment(equipmentId);
    }
}
