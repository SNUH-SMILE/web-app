package kr.co.hconnect.repository;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.PatientVO;

/**
 * 환자정보 Dao
 */
@Repository
public class PatientDao extends EgovAbstractMapper {

	/**
	 * 환자 생성 
	 * @param vo PatientVO
	 * @return affectedRow
	 */
	public int insertPatient(PatientVO vo) {
		return insert("kr.co.hconnect.sqlmapper.insertPatient", vo);
	}
	
	/**
	 * 환자 수정 
	 * @param vo PatientVO
	 * @return affectedRow
	 */
	public int updatePatient(PatientVO vo) {
		return update("kr.co.hconnect.sqlmapper.updatePatient", vo);
	}
	
}
