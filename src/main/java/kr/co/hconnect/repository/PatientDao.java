package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.domain.*;
import kr.co.hconnect.vo.PatientVO;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 환자정보 관리 Dao
 */
@Repository
public class PatientDao extends EgovAbstractMapper {

	/**
	 * 환자정보 조회-로그인ID 기준
	 * @param loginId 로그인ID
	 * @return Patient
	 */
	public Patient selectPatientByLoginId(String loginId) {
		return selectOne("kr.co.hconnect.sqlmapper.selectPatientByLoginId", loginId);
	}

	/**
	 * 환자정보 조회-본인인증 확인 정보 기준
	 * @param identityInfo 본인인증 확인 정보 (성명, 생년월일, 성별, 휴대폰)
	 * @return Patient
	 */
	public Patient selectPatientByIdentityInfo(IdentityInfo identityInfo) {
		return selectOne("kr.co.hconnect.sqlmapper.selectPatientByIdentityInfo", identityInfo);
	}

	/**
	 * 환자정보 조회-아이디 검색 조건 정보 기준
	 * @param searchLoginIdInfo 아이디 검색 조건 정보
	 * @return List&ltPatient&gt
	 */
	public List<Patient> selectPatientListBySearchLoginIdInfo(SearchLoginIdInfo searchLoginIdInfo) {
		return selectList("kr.co.hconnect.sqlmapper.selectPatientBySearchLoginIdInfo", searchLoginIdInfo);
	}

    /**
     * 환자정보 조회-개인정보 확인 검색 조건 기준
     *
     * @param searchExistLoginInfo 개인정보 확인 검색 조건
     * @return List&ltPatient&gt
     */
    public List<Patient> selectPatientBySearchExistLoginInfo(SearchExistLoginInfo searchExistLoginInfo) {
        return selectList("kr.co.hconnect.sqlmapper.selectPatientBySearchExistLoginInfo", searchExistLoginInfo);
    }

    /**
     * 본인인증 내역 확인
     *
     * @param identityInfo 본인인증 확인 정보 (성명, 생년월일, 성별, 휴대폰)
     * @return IdentityResult
     */
    public IdentityResult selectIdentityInfo(IdentityInfo identityInfo) throws TooManyResultsException {
        return selectOne("kr.co.hconnect.sqlmapper.selectIdentityInfo", identityInfo);
    }

    /**
     * 환자 추가정보 업데이트 - 기존 생성된 환자정보 추가내역
     *
     * @param patient 환자정보
     * @return affectedRow
     */
    public int createPatientInfo(Patient patient) {
        return update("kr.co.hconnect.sqlmapper.addPatientInfo", patient);
    }

    /**
     * 환자 정보 업데이트
     *
     * @param patient 환자정보
     * @return affectedRow
     */
    public int updatePatientInfo(Patient patient) {
        return update("kr.co.hconnect.sqlmapper.updatePatientInfo", patient);
    }

	/**
	 * 환자 비밀번호 변경-LoginId 기준
	 * @param loginInfo 로그인 구성정보
	 * @return affectedRow
	 */
	public int updatePatientPasswordByLoginId(LoginInfo loginInfo) {
		return update("kr.co.hconnect.sqlmapper.updatePatientPasswordByLoginId", loginInfo);
	}

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
