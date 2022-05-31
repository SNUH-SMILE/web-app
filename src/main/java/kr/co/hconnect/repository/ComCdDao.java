package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.ComCdDetailSearchVO;
import kr.co.hconnect.vo.ComCdDetailVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 공통코드관리 Dao
 */
@Repository
public class ComCdDao extends EgovAbstractMapper {

    /**
     * 공통코드상세 리스트 조회
     *
     * @param vo 공통코드상세 조회조건 VO
     * @return List&lt;ComCdDetailVO&gt; 공통코드상세 리스트
     */
    public List<ComCdDetailVO> selectComCdDetailList(ComCdDetailSearchVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.selectComCdDetailList", vo);
    }

}
