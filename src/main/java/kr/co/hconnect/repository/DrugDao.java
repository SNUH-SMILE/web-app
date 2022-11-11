package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.ItemVO;
import org.springframework.stereotype.Repository;
import kr.co.hconnect.vo.*;

import javax.validation.Valid;
import java.util.List;

@Repository
public class DrugDao extends EgovAbstractMapper {

    /**
     * 복약정보
     * @param vo 복약정보
     */

    public void insertDrug(DrugVO vo) {
        insert("kr.co.hconnect.sqlmapper.insertDrug", vo);
    }

    /**
     * 복약 알람정보
     * @param vo 복약 알람
     */
    public void insertDrugAlarm(DrugAlarmVO vo) {
        insert("kr.co.hconnect.sqlmapper.insertDrugAlarm", vo);
    }
    /**
     * 복약 결과정보
     * @param vo 복약 알람
     */
    public void insertDrugDose(DrugDoseVO vo) {
        insert("kr.co.hconnect.sqlmapper.insertDrugDose", vo);
    }

    /**
     *복약 정보 상세조회
     * @param vo 측정항목 조회 조건
     * @return 측정항목
     */
    public DrugVO selectDrug(DrugAlarmVO vo) {
        return selectOne("kr.co.hconnect.sqlmapper.selectDrug", vo);
    }

    public DrugVO selectAlarmDrug(DrugAlarmVO vo) {
        return selectOne("kr.co.hconnect.sqlmapper.selectAlarmDrug", vo);
    }

    public List<DrugTimeListVO> selectTimeList(DrugSearchVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.selectTimeList", vo);
    }

    public List<DrugNoticeListVO> selectAlarmList(DrugSearchVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.selectAlarmList", vo);
    }


}
