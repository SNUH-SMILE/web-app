package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 격리/입소내역 Dao
 */
@Repository
public class AdmissionDao extends EgovAbstractMapper {

	/**
	 * 로그인ID 기준 격리/입소내역(내원중) 리스트 조회
	 *
	 * @param loginId 로그인ID
	 * @return List&lt;AdmissionVO&gt; 격리/입소내역 리스트
	 */
	public List<AdmissionVO> selectActiveAdmissionListByLoginId(String loginId) {
		return selectList("kr.co.hconnect.sqlmapper.selectActiveAdmissionListByLoginId", loginId);
	}
    /**
     * 로그인ID 기준 종료된것 포함 격리/입소내역(내원중) 리스트 조회
     *
     * @param loginId 로그인ID
     * @return List&lt;AdmissionVO&gt; 격리/입소내역 리스트
     */
    public List<AdmissionVO> selectAdmissionListByLoginId(String loginId) {
        return selectList("kr.co.hconnect.sqlmapper.selectAdmissionListByLoginId", loginId);
    }

	/**
	 * 환자ID 기준 격리/입소내역(내원중) 리스트 조회
	 * @param patientId 환자ID
	 * @return List&lt;AdmissionVO&gt; 격리/입소내역 리스트
	 */
	public List<AdmissionVO> selectActiveAdmissionListByPatientId(String patientId) {
		return selectList("kr.co.hconnect.sqlmapper.selectActiveAdmissionListByPatientId", patientId);
	}

	/**
	 * 페이지네이션 쿼리 전체 로우 카운트 조회
	 * @return Total Record Count
	 */
	public int selectFoundRowsByAdmission() {
		return selectOne("kr.co.hconnect.sqlmapper.selectFoundRowsByAdmission");
	}

	/**
	 * 생활치료센터 입소자 리스트 조회
	 *
	 * @param vo 입소자 리스트 조회조건
	 * @return List<AdmissionByCenterVO> 입소자 리스트
	 */
	public List<AdmissionByCenterVO> selectAdmissionListByCenter(AdmissionListSearchByCenterVO vo) {
		return selectList("kr.co.hconnect.sqlmapper.selectAdmissionListByCenter", vo);
	}

	/**
	 * 입소내역 정보 조회
	 * @param admissionId 격리/입소내역ID
	 * @return affectedRow
	 */
	public AdmissionInfoVO selectAdmissionInfo(String admissionId) {
		return selectOne("kr.co.hconnect.sqlmapper.selectAdmissionInfo", admissionId);
	}
	
	/**
	 * 입소내역 생성
	 * @param vo AdmissionVO-입소내역VO
	 * @return affectedRow
	 */
	public int insertAdmission(AdmissionVO vo) {
		return insert("kr.co.hconnect.sqlmapper.insertAdmission", vo);
	}
	
	/**
	 * 입소내역 수정
	 * @param vo AdmissionVO-입소내역VO
	 * @return affectedRow
	 */
	public int updateAdmission(AdmissionVO vo) {
		return update("kr.co.hconnect.sqlmapper.updateAdmission", vo);
	}
	
	/**
	 * 퇴실처리
	 * @param vo AdmissionVO-입소내역VO
	 * @return affectedRow
	 */
	public int updateAdmissionDischarge(AdmissionVO vo) {
		return update("kr.co.hconnect.sqlmapper.updateAdmissionDischarge", vo);
	}

    /**
     * 퇴실처리취소
     * @param vo AdmissionVO-입소내역VO
     * @return affectedRow
     */
    public int updateAdmissionDischargeCancle(AdmissionVO vo) {
        return update("kr.co.hconnect.sqlmapper.updateAdmissionDischargeCancle", vo);
    }


	/**
	 * 자가격리자 리스트 조회
	 *
	 * @param vo 자가격리자 리스트 조회조건
	 * @return List<AdmissionByQuarantineVO> 자가격리자 리스트
	 */
	public List<AdmissionByQuarantineVO> selectAdmissionListByQuarantine(AdmissionListSearchByQuarantineVO vo) {
		return selectList("kr.co.hconnect.sqlmapper.selectAdmissionListByQuarantine", vo);
	}


    /**
     * 추론 오류 log
     * @param admissionId 격리/입소내역ID
     * @return affectedRow
     */
    public List<InferenceErrorLogVO> selectInferenctLogInfo(String admissionId) {
        return selectList("kr.co.hconnect.sqlmapper.selectInferenctLogInfo", admissionId);
    }


    /**
     * 자가격리자 입소일조회
     * @param admissionId
     * @return
     */
    public AdmissionVO selectAdmissionDateList(String admissionId) {
        return selectOne("kr.co.hconnect.sqlmapper.selectAdmissionDate", admissionId);
    }

    /**
     * 가민 동기화 및 배터리 확인 알람 리스트
     * @return
     */
    public List<AdmissionVO> selectGamineList() {
        return selectList("kr.co.hconnect.sqlmapper.selectGamineList");
    }
}



