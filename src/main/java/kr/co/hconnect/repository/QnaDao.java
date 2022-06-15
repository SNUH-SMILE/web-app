package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.QnaListSearchVO;
import kr.co.hconnect.vo.QnaVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 문의사항 Dao
 */
@Repository
public class QnaDao extends EgovAbstractMapper {

    /**
     * 페이지네이션 쿼리 전체 로우 카운트 조회
     *
     * @return Total Record Count
     */
    public int selectFoundRowsByQna() {
        return selectOne("kr.co.hconnect.sqlmapper.selectFoundRowsByQna");
    }

    /**
     * 문의사항 내역 조회
     *
     * @param questionSeq 문의순번
     * @return QnaVO 문의사항 내역
     */
    public QnaVO selectQna(int questionSeq) {
        return selectOne("kr.co.hconnect.sqlmapper.selectQna", questionSeq);
    }

    /**
     * 문의사항 리스트 조회
     *
     * @param vo 문의사항 리스트 조회 조건
     * @return List&lt;QnaVO&gt; 문의사항 리스트
     */
    public List<QnaVO> selectQnaList(QnaListSearchVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.selectQnaList", vo);
    }

}
