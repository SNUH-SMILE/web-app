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
     * 생활치료센터 정보 조회
     * @param vo 생활치료센터 조회 조건
     * @return List&lt;TreatmentCenterVO&gt; 생활치료센터 리스트
     */
    public TreatmentCenterVO selectTreatmentCenter(TreatmentCenterVO vo) {
        return selectOne("kr.co.hconnect.sqlmapper.selectTreatmentCenter", vo);
    }

    /**
     * 생활치료센터 리스트 조회
     * @param vo 생활치료센터 조회 조건
     * @return List&lt;TreatmentCenterVO&gt; 생활치료센터 리스트
     */
    public List<TreatmentCenterVO> selectTreatmentCenterList(TreatmentCenterVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.selectTreatmentCenterList", vo);
    }

    /**
     *생활치료센터 저장
     * @param vo 생활치료센터 저장 정보
     */
    public void insertTreatmentCenter(TreatmentCenterVO vo) {
       insert("kr.co.hconnect.sqlmapper.insertTreatmentCenter", vo);
    }

    /**
     *생활치료센터 수정
     * @param vo 생활치료센터 수정 정보
     */
    public void updateTreatmentCenter(TreatmentCenterVO vo) {
       update("kr.co.hconnect.sqlmapper.updateTreatmentCenter", vo);
    }

    /**
     *생활치료센터 삭제
     * @param vo 생활치료센터 삭제 정보
     */
    public void deleteTreatmentCenter(TreatmentCenterVO vo) {
       delete("kr.co.hconnect.sqlmapper.deleteTreatmentCenter", vo);
    }

}
