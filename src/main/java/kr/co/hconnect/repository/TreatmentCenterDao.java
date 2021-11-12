package kr.co.hconnect.repository;
/**
 * 생활치료센터
 */

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.TreatmentCenterVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TreatmentCenterDao extends EgovAbstractMapper {

    /**
     *생활치료센터 리스트 조회
     * @return 생활치료센터 목록
     */
    public List<TreatmentCenterVO> selectTreatmentCenterList(){
        return selectList("kr.co.hconnect.sqlmapper.selectTreatmentCenterList");
    }

    /**
     *생활치료센터 저장
     * @param vo 생활치료센터VO
     */
    public void insertTreatmentCenter(TreatmentCenterVO vo) {
       insert("kr.co.hconnect.sqlmapper.insertTreatmentCenter",vo);
    }

    /**
     *생활치료센터 수정
     * @param vo 생활치료센터VO
     */
    public void updateTreatmentCenter(TreatmentCenterVO vo) {
       update("kr.co.hconnect.sqlmapper.updateTreatmentCenter",vo);
    }

    /**
     *생활치료센터 삭제
     * @param centerId 생활치료센터Id
     */
    public void deleteTreatmentCenter(String centerId) {
       delete("kr.co.hconnect.sqlmapper.deleteTreatmentCenter",centerId);
    }

}
