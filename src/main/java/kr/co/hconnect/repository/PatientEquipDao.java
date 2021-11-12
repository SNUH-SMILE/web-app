package kr.co.hconnect.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.EquipmentVO;
import kr.co.hconnect.vo.PatientEquipListVO;
import kr.co.hconnect.vo.PatientEquipVO;

/**
 * 환자별 사용장비 Dao
 */
@Repository
public class PatientEquipDao extends EgovAbstractMapper {

	/**
	 * 환자별 사용장비 리스트 조회
	 * @return List<PatientEquipListVO>-환자별 사용장비 리스트
	 */
	public List<PatientEquipListVO> selectPatientEquipList() {
		return selectList("kr.co.hconnect.sqlmapper.selectPatientEquipList");
	}
	
	/**
	 * 환자 장비내역 리스트 조회
	 * @param admissionId 입소내역ID
	 * @return List<PatientEquipVO>-환자 장비내역 리스트
	 */
	public List<PatientEquipVO> selectPatientEquipInfoList(String admissionId) {
		return selectList("kr.co.hconnect.sqlmapper.selectPatientEquipInfoList", admissionId);
	}
		
	/**
	 * 장비 리스트 조회-생활치료센터 기준
	 * @param admissionId 입소ID
	 * @return List<EquipmentVO>-장비 리스트
	 */
	public List<EquipmentVO> selectEquipmentListByCenterIdList(String admissionId) {
		return selectList("kr.co.hconnect.sqlmapper.selectEquipmentListByCenterIdList", admissionId);
	}
	
	/**
	 * 환자별 사용장비 저장
	 * @param vo PatientEquipVO-환자별 사용장비
	 * @return affectedRow
	 */
	public int insertPatientEquip(PatientEquipVO vo) {
		return insert("kr.co.hconnect.sqlmapper.insertPatientEquip", vo);
	}
	
	/**
	 * 환자별 사용장비 전체 삭제-입소내역ID, 환자ID기준
	 * @param vo PatientEquipVO-환자별 사용장비
	 * @return affectedRow
	 */
	public int deletePatientEquipAll(PatientEquipVO vo) {
		return delete("kr.co.hconnect.sqlmapper.deletePatientEquipAll", vo);
	}
}
