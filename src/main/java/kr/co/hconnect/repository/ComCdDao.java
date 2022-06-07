package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.ComCdDetailSearchVO;
import kr.co.hconnect.vo.ComCdDetailVO;
import kr.co.hconnect.vo.ComCdSearchVO;
import kr.co.hconnect.vo.ComCdVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 공통코드관리 Dao
 */
@Repository
public class ComCdDao extends EgovAbstractMapper {

    /**
     * 공통코드 정보 조회
     *
     * @param vo 공통코드 조회 조건 VO
     * @return ComCdVo 공통코드 정보 VO
     */
    public ComCdVO selectComCd(ComCdSearchVO vo) {
        return selectOne("kr.co.hconnect.sqlmapper.selectComCd", vo);
    }

    /**
     * 공통코드 리스트 조회
     * 
     * @param vo 공통코드 조회 조건 VO
     * @return List&lt;ComCdVO&gt; 공통코드 리스트
     */
    public List<ComCdVO> selectComCdList(ComCdSearchVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.selectComCdList", vo);
    }

    /**
     * 공통코드상세 조회
     * 
     * @param vo 공통코드상세 조회조건 VO
     * @return ComCdDetailVO 공통코드상세 정보
     */
    public ComCdDetailVO selectComCdDetail(ComCdDetailSearchVO vo) {
        return selectOne("kr.co.hconnect.sqlmapper.selectComCdDetail", vo);
    }

    /**
     * 공통코드상세 리스트 조회
     *
     * @param vo 공통코드상세 조회조건 VO
     * @return List&lt;ComCdDetailVO&gt; 공통코드상세 리스트
     */
    public List<ComCdDetailVO> selectComCdDetailList(ComCdDetailSearchVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.selectComCdDetailList", vo);
    }

    /**
     * 공통코드 생성
     *
     * @param vo 공통코드 저장 정보
     * @return 적용결과 count
     */
    public int insertComCd(ComCdVO vo) {
        return insert("kr.co.hconnect.sqlmapper.insertComCd", vo);
    }

    /**
     * 공통코드 수정
     *
     * @param vo 공통코드 저장 정보
     * @return 적용결과 count
     */
    public int updateComCd(ComCdVO vo) {
        return update("kr.co.hconnect.sqlmapper.updateComCd", vo);
    }

    /**
     * 공통코드상세 생성
     *
     * @param vo 공통코드상세 저장 정보
     * @return 적용결과 count
     */
    public int insertComCdDetail(ComCdDetailVO vo) {
        return insert("kr.co.hconnect.sqlmapper.insertComCdDetail", vo);
    }

    /**
     * 공통코드상세 수정
     *
     * @param vo 공통코드상세 저장 정보
     * @return 적용결과 count
     */
    public int updateComCdDetail(ComCdDetailVO vo) {
        return update("kr.co.hconnect.sqlmapper.updateComCdDetail", vo);
    }

}
