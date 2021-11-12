package kr.co.hconnect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.repository.PatientEquipDao;
import kr.co.hconnect.vo.EquipmentVO;
import kr.co.hconnect.vo.PatientEquipListVO;
import kr.co.hconnect.vo.PatientEquipSaveVO;
import kr.co.hconnect.vo.PatientEquipVO;

/**
 * 환자별 사용장비 service
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class PatientEquipService extends EgovAbstractServiceImpl {

	/**
	 * 환자별 사용장비 Dao
	 */
	private final PatientEquipDao dao;
	
	/**
	 * 생서자
	 * @param dao 환자변 사용장비
	 */
	@Autowired
	public PatientEquipService(PatientEquipDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 환자별 사용장비 리스트 조회
	 * @return List<PatientEquipListVO>-환자별 사용장비 리스트
	 */
	public List<PatientEquipListVO> selectPatientEquipList() {
		return dao.selectPatientEquipList();
	}
	
	/**
	 * 환자 장비내역 리스트 조회
	 * @param admissionId 입소내역ID
	 * @return List<PatientEquipVO>-환자 장비내역 리스트
	 */
	public List<PatientEquipVO> selectPatientEquipInfoList(String admissionId) {
		return dao.selectPatientEquipInfoList(admissionId);
	}
	
	/**
	 * 장비 리스트 조회-생활치료센터 기준
	 * @param admissionId 입소ID
	 * @return List<EquipmentVO>-장비 리스트
	 */
	public List<EquipmentVO> selectEquipmentListByCenterIdList(String admissionId) {
		return dao.selectEquipmentListByCenterIdList(admissionId);
	}

	/**
	 * 환자별 사용장비 저장
	 * @param vo PatientEquipSaveVO-환자별 사용장비 저장데이터
	 */
	@Transactional(rollbackFor = Exception.class)
	public void savePatientEquipment(PatientEquipSaveVO vo) {
		// 1. 기존 데이터 삭제
		PatientEquipVO delVO = new PatientEquipVO();
		delVO.setAdmissionId(vo.getAdmissionId());
		delVO.setPatientId(vo.getPatientId());
		
		dao.deletePatientEquipAll(delVO);
		
		// 2. 데이터 저장
		if (vo.getPatientEquipVOs() != null && vo.getPatientEquipVOs().size() > 0) {
			for (PatientEquipVO patientEquipVO : vo.getPatientEquipVOs()) {
				dao.insertPatientEquip(patientEquipVO);
			}
		}
	}
}
