package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.hconnect.common.ComCd;
import kr.co.hconnect.repository.ComCdManagerDao;
import kr.co.hconnect.vo.ComCdDetailListVO;
import kr.co.hconnect.vo.ComCdDetailVO;
import kr.co.hconnect.vo.ComCdVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 공통코드 관련 서비스
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
@Deprecated
public class ComCdManagerService extends EgovAbstractServiceImpl {

	/**
	 * 공통코드관리서비스 Logger (Slf4j - Logger)
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ComCdManagerService.class);

	/**
	 * 공통코드관리 DAO
	 */
	private final ComCdManagerDao comCdManagerDao;

	/**
	 * 공통코드 ID 생성 서비스
	 */
	private final EgovIdGnrService comCdIdGnrService;

	/**
	 * 생성자
	 * @param comCdManagerDao - 공통코드관리DAO
	 * @param comCdIdGnrService - 공통코드 ID 생성 서비스
	 */
	@Autowired
	public ComCdManagerService(ComCdManagerDao comCdManagerDao, @Qualifier("comCdGnrService") EgovIdGnrService comCdIdGnrService) {
		this.comCdManagerDao = comCdManagerDao;
		this.comCdIdGnrService = comCdIdGnrService;
	}

	/**
	 * 공통코드 조회 - 공통코드 코드로 조회
	 * @param comCd - 공통코드
	 * @return ComCdVO - 공통코드VO
	 */
	public ComCdVO selectComCdByCd(String comCd){
		return comCdManagerDao.selectComCdByCd(comCd);
	}


	/**
	 * 공통코드 명록 조회 
	 * @param comCdVO - 공통코드 VO - 조회 조건 (공통코드, 공통코드명, 사용여부)
	 * @return List<ComCdVO> - 공통코드VO 목록
	 */
	public List<ComCdVO> selectComCdList(ComCdVO comCdVO){
		return comCdManagerDao.selectComCdList(comCdVO);
	}

	/**
	 * 공통코드상세 리스트 조회(selectbox 바인딩용)
	 * @param vo - 세부코드List VO
	 * @return List<ComCdDetailListVO> 공통코드상세 목록
	 */
	public List<ComCdDetailListVO> selectComCdDetailList(ComCdDetailListVO vo) {
		return comCdManagerDao.selectComCdDetailList(vo);
	}

	/**
	 * 신규 공통코드 저장
	 *
	 * @param comCdVO - 공통코드 VO
	 * @return int - affected row
	 */
	@Transactional(rollbackFor = Exception.class)
	public int insertCommonCode(ComCdVO comCdVO) {

		LOGGER.info("Received ComCdVO 정보 {}", comCdVO);

		/*
		 * 공통코드 ID 생성 후 코드 저장하기.
		 * exception - FdlException 또는 DataIntegrityViolationException
		 * FdlException = 공통코드 next ID값 생성 중 에러
		 * */
		try {
			//공통코드 ID 생성
			comCdVO.setComCd(comCdIdGnrService.getNextStringId());
		} catch (FdlException e) {
			LOGGER.error(e.getMessage());
			throw new RuntimeException("공통코드 채번중 예외가 발생 했습니다.", e);
		}

		//공통코드 저장
		return comCdManagerDao.insertComCd(comCdVO);
	}


	/**
	 * 공통코드 수정
	 * @param comCdVO - 공통코드 VO - 공통코드 VO
	 * @return int - affected row
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateCommonCode(ComCdVO comCdVO){

		LOGGER.info("Received ComCdVO 정보 {}", comCdVO);

		ComCdVO rsltComCdVO = this.selectComCdByCd(comCdVO.getComCd());

		if(rsltComCdVO == null){
			throw new RuntimeException("존재하지 않은 공통코드입니다. 다시 시도해주세요.");
		}
		else if(StringUtils.equalsIgnoreCase(rsltComCdVO.getUseYn(), "N")){
			throw new RuntimeException("사용 종료된 공통코드는 수정할 수 없습니다.");
		}

		//공통코드 저장
		return comCdManagerDao.updateComCd(comCdVO);
	}

	/**
	 * 공통코드 삭제 - 종료코드
	 * @param comCdVO - 공통코드 VO
	 * @return int - affected row
	 */
	@Transactional(rollbackFor = Exception.class)
	public int deleteCommonCode(ComCdVO comCdVO){
		LOGGER.info("------------ DELETE COM_CD ----------------");
		ComCdVO rsltComCdVO = this.selectComCdByCd(comCdVO.getComCd());

		if(rsltComCdVO == null){
			throw new RuntimeException("존재하지 않은 공통코드입니다. 다시 시도해주세요.");
		}

		return comCdManagerDao.deleteComCd(comCdVO);
	}
	

	/**
	 * 공통코드상세 목록 조회
	 * @param comCd 공통코드 열거자
	 * @return 공통코드상세VO 목록
	 */
	public List<ComCdDetailListVO> selectComCdDetailList(ComCd comCd) {
		ComCdDetailListVO comCdDetailVO = new ComCdDetailListVO();
		comCdDetailVO.setComCd(comCd.getDbValue());
		comCdDetailVO.setUseYn("Y");
		return selectComCdDetailList(comCdDetailVO);
	}

	/**
	 * 세부코드 목록 조회 - 세부코드 모달용 조회
	 * @param comCdDetailVO - 세부코드 VO
	 * @return List<ComCdDetailVO> - 세부코드VO list
	 */
	public List<ComCdDetailVO> selectComDetailList(ComCdDetailVO comCdDetailVO){
		return comCdManagerDao.selectComDetailList(comCdDetailVO);
	}

	/**
	 * 세부코드 CODE로 조회
	 * @param comCdDetailVO - 세부코드 VO
	 * @return ComCdDetailVO - 세부코드 VO
	 */
	public ComCdDetailVO selectComDetailCdByCode(ComCdDetailVO comCdDetailVO){
		return comCdManagerDao.selectComDetailCdByCode(comCdDetailVO);
	}

	/**
	 * 세부코드 신규 등록
	 * @param comCdDetailVO - 세부코드 VO
	 * @return int - affected row
	 */
	@Transactional(rollbackFor = Exception.class)
	public int insertComCdDetail(ComCdDetailVO comCdDetailVO) {

		List<ComCdDetailVO> duplicateComCdDetailVO = comCdManagerDao.selectComDetailList(comCdDetailVO);

		if(duplicateComCdDetailVO.size() > 0){
			throw new RuntimeException("중복된 세부코드입니다. 다른 코드로 다시 시도해주세요.");
		}

		return comCdManagerDao.insertComCdDetail(comCdDetailVO);
	}


	/**
	 * 세부코드 수정
	 * @param comCdDetailVO - 세부코드 VO
	 * @return int - affected row
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateComCdDetail(ComCdDetailVO comCdDetailVO) {

		ComCdDetailVO selectedComCdDetailVO = this.selectComDetailCdByCode(comCdDetailVO);

		if(selectedComCdDetailVO == null){
			LOGGER.error("존재하지 않은 세부코드 에러.");
			throw new RuntimeException("존재하지 않은 코드입니다. 올바른 세부코드로 다시 시도해주세요.");
		}
		else if(StringUtils.equalsIgnoreCase(selectedComCdDetailVO.getUseYn(), "N")){
			LOGGER.error("사용 종료된 세부코드 수정 요청. 수정 불가 에러.");
			throw new RuntimeException("사용 종료된 세부코드는 수정할 수 없습니다.");
		}

		return comCdManagerDao.updateComCdDetail(comCdDetailVO);
	}

	/**
	 * 세부코드 비활성화 (종료코드)
	 * @param comCdDetailVO - 세부코드 VO
	 * @return int - affected row
	 */
	@Transactional(rollbackFor = Exception.class)
	public int deleteCommonDetail(ComCdDetailVO comCdDetailVO) {
		List<ComCdDetailVO> duplicateComCdDetailVO = comCdManagerDao.selectComDetailList(comCdDetailVO);

		if(duplicateComCdDetailVO.size() == 0){
			throw new RuntimeException("존재하지 않은 코드입니다. 올바른 세부코드로 다시 시도해주세요.");
		}

		return comCdManagerDao.deleteComDetail(comCdDetailVO);
	}
}
