package kr.co.hconnect.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.hconnect.repository.AdmissionDao;
import kr.co.hconnect.repository.ComCdManagerDao;
import kr.co.hconnect.repository.PatientDao;
import kr.co.hconnect.vo.AdmissionInfoVO;
import kr.co.hconnect.vo.AdmissionListVO;
import kr.co.hconnect.vo.AdmissionVO;
import kr.co.hconnect.vo.ComCdDetailVO;
import kr.co.hconnect.vo.PatientVO;

/**
 * 입소내역 관리 서비스
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class AdmissionService extends EgovAbstractServiceImpl {

	/**
	 * 입소내역 dao
	 */
	private final AdmissionDao dao;
	
	/**
	 * 환자 dao
	 */
	private final PatientDao patientDao;
	
	/**
	 * 환자ID 채번 서비스
	 */
	private final EgovIdGnrService patientIdGnrService;         
	
	/**
	 * 환자ID 채번 서비스 
	 */
    private final EgovIdGnrService admissionIdGnrService;

	// SLF4J (Simple Logging Facade for Java), log4j2
	private static final Logger LOGGER = LoggerFactory.getLogger(AdmissionService.class);

	
	@Autowired
	public AdmissionService(AdmissionDao dao
			, PatientDao patientDao
			, @Qualifier("patientIdGnrService") EgovIdGnrService patientIdGnrService
	        , @Qualifier("admissionIdGnrService") EgovIdGnrService admissionIdGnrService) {
		this.dao = dao;
		this.patientDao = patientDao;
		this.patientIdGnrService = patientIdGnrService;
		this.admissionIdGnrService = admissionIdGnrService;

		LOGGER.info("Dao {}", dao);
	}
	
	/**
	 * 입소내역리스트 조회
	 * @return List<AdmissionListVO>-입소내역리스트
	 */
	public List<AdmissionListVO> selectAdmissionList() {
		return dao.selectAdmissionList();
	}

	/**
	 * 입소내역 정보 조회
	 * @param admissionId-입소내역ID
	 * @return AdmissionInfoVO-입소내역 정보
	 */
	public AdmissionInfoVO selectAdmissionInfo(String admissionId) {
		return dao.selectAdmissionInfo(admissionId);
	}
	
	/**
	 * 입소내역 저장 
	 * @param patientVO PatientVO-환자VO
	 * @param admissionVO AdmissionVO-입소내역VO
	 * @return 입소내역ID
	 */
	@Transactional(rollbackFor = Exception.class)
	public String saveAdmission(PatientVO patientVO, AdmissionVO admissionVO) 
			throws FdlException {

		String patientId = "";	// 환자 ID
		String admissionId = "";	// 입소내역 ID
		
		// 01. 환자정보 저장
		// 신규 환자 정보 생성
		if (StringUtils.isEmpty(patientVO.getPatientId())) {
			// 환자 ID 채변
			patientId = patientIdGnrService.getNextStringId();
			patientVO.setPatientId(patientId);
	        
	        // 환자 생성
	        patientDao.insertPatient(patientVO);
			
		} else {
			patientId = patientVO.getPatientId(); 
			
			// 환자 정보 업데이트
			patientDao.updatePatient(patientVO);
		}
		
		// 02. 입소내역 저장
		if (StringUtils.isEmpty(admissionVO.getAdmissionId())) {
			// 입소내역 ID 채번
			admissionId = admissionIdGnrService.getNextStringId();
			admissionVO.setAdmissionId(admissionId);
			admissionVO.setPatientId(patientId);
			
			// 입소내역 생성
			dao.insertAdmission(admissionVO);
			
		} else {
			admissionId = admissionVO.getAdmissionId(); 
					
			// 입소내역 수정
			dao.updateAdmission(admissionVO);
		}
        
		
		return admissionId;
	}
	
	/**
	 * 퇴실처리
	 * @param vo AdmissionVO-입소내역VO
	 * @return affectedRow
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateAdmissionDischarge(AdmissionVO vo) {		
		
		return dao.updateAdmissionDischarge(vo);
	}
}
