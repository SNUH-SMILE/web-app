package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.ComCdDetailListVO;
import kr.co.hconnect.vo.ComCdDetailVO;
import kr.co.hconnect.vo.ComCdVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 공통코드관리 DAO
 * extends EgovAbstractMapper
 */
@Repository
public class ComCdManagerDao extends EgovAbstractMapper {


	/**
	 * 공통코드 조회 - 공통코드 코드로 조회
	 * @param comCd - 공통코드
	 * @return ComCdVO - 공통코드 VO
	 */
	public ComCdVO selectComCdByCd(String comCd) {
		return selectOne("kr.co.hconnect.sqlmapper.selectComCdByCode", comCd); }

	/**
	 * 공통코드 목록 조회
	 * @param comCdVO - 공통코드 VO
	 * @return List<ComCdVO> 공통코드목록
	 */
	public List<ComCdVO> selectComCdList(ComCdVO comCdVO) {
		return selectList("kr.co.hconnect.sqlmapper.selectComCdList", comCdVO); }
	
	/**
	 * 공통코드상세 리스트조회
	 * @param vo - 세부코드List VO
	 * @return List<ComCdDetailListVO> 공통코드상세 목록
	 */
	public List<ComCdDetailListVO> selectComCdDetailList(ComCdDetailListVO vo) {
		return selectList("kr.co.hconnect.sqlmapper.selectComCdDetailList", vo);
	}

	/**
	 * 세부코드 목록 조회 (세부코드 모달 테이블용)
	 * @param comCdDetailVO - 세부코드 VO
	 * @return List<ComCdDetailVO> - 세부코드 조회 목록
	 */
	public List<ComCdDetailVO> selectComDetailList(ComCdDetailVO comCdDetailVO){
		return selectList("kr.co.hconnect.sqlmapper.selectComDetailList", comCdDetailVO);
	}

	/**
	 * 세부코드 조회 [update/delete 시 존재여부 확인 용]
	 * @param comCdDetailVO - 세부코드 VO
	 * @return ComCdDetailVO - 세부코드 VO
	 */
	public ComCdDetailVO selectComDetailCdByCode(ComCdDetailVO comCdDetailVO) {
		return selectOne("kr.co.hconnect.sqlmapper.selectDetailCdByCode", comCdDetailVO);
	}

	/**
	 * 신규 공통코드 저장
	 * @param comCdVO - 공통코드 VO
	 * @return 저장된 row 수
	 */
	public int insertComCd(ComCdVO comCdVO) {
		return insert("kr.co.hconnect.sqlmapper.insertComCd", comCdVO);
	}


	/**
	 * 공통코드 update
	 * @param comCdVO - 공통코드 VO
	 * @return update된 row 수
	 */
	public int updateComCd(ComCdVO comCdVO) { return update("kr.co.hconnect.sqlmapper.updateComCd", comCdVO); }


	/**
	 * 공통코드 비활성화 (useYn -> N)
	 * @param comCdVO - 공통코드 VO
	 * @return update된 row수
	 */
	public int deleteComCd(ComCdVO comCdVO) { return update("kr.co.hconnect.sqlmapper.deleteComCd", comCdVO); }


	/**
	 * 공통코드 세부코드 신규 생성
	 * @param comCdDetailVO - 세부코드 VO
	 * @return insert된 row count
	 */
	public int insertComCdDetail(ComCdDetailVO comCdDetailVO) {
		return insert("kr.co.hconnect.sqlmapper.insertComCdDetail", comCdDetailVO); }


	/**
	 * 세부코드 수정
	 * @param comCdDetailVO - 세부코드 VO
	 * @return int - 수정된 row count
	 */
	public int updateComCdDetail(ComCdDetailVO comCdDetailVO) {
		return update("kr.co.hconnect.sqlmapper.updateComCdDetail", comCdDetailVO);
	}

	/**
	 * 세부코드 비활성화(종료)
	 * @param comCdDetailVO - 세부코드 VO
	 * @return int - 비활성화된 row count
	 */
	public int deleteComDetail(ComCdDetailVO comCdDetailVO) {
		return update("kr.co.hconnect.sqlmapper.deleteComDetail", comCdDetailVO);
	}

}
