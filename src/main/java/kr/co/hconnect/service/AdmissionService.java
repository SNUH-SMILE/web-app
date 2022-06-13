package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.exception.NotFoundAdmissionInfoException;
import kr.co.hconnect.repository.AdmissionDao;
import kr.co.hconnect.repository.PatientDao;
import kr.co.hconnect.repository.ResultDao;
import kr.co.hconnect.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

/**
 * 격리/입소내역 관리 Service
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class AdmissionService extends EgovAbstractServiceImpl {

	/**
	 * 입소내역 Dao
	 */
	private final AdmissionDao admissionDao;

	/**
	 * 측정결과 Dao
	 */
	private final ResultDao resultDao;
	
	/**
	 * 환자 Dao
	 */
	private final PatientDao patientDao;
	
	/**
	 * 환자 ID 채번 서비스
	 */
	private final EgovIdGnrService patientIdGnrService;         
	
	/**
	 * 격리/입소 ID 채번 서비스
	 */
    private final EgovIdGnrService admissionIdGnrService;

    private final MessageSource messageSource;

	// SLF4J (Simple Logging Facade for Java), log4j2
	private static final Logger LOGGER = LoggerFactory.getLogger(AdmissionService.class);

	/**
	 * 생성자
	 * @param admissionDao 격리/입소내역 Dao
	 * @param resultDao 측정결과 Dao
	 * @param patientDao 환자정보 관리 Dao
	 * @param patientIdGnrService 환자 ID 채번 서비스
	 * @param admissionIdGnrService 격리/입소 ID 채번 서비스
	 * @param messageSource MessageSource
	 */
	@Autowired
	public AdmissionService(AdmissionDao admissionDao, ResultDao resultDao, PatientDao patientDao
			, @Qualifier("patientIdGnrService") EgovIdGnrService patientIdGnrService
			, @Qualifier("admissionIdGnrService") EgovIdGnrService admissionIdGnrService
			, MessageSource messageSource) {
		this.admissionDao = admissionDao;
		this.resultDao = resultDao;
		this.patientDao = patientDao;
		this.patientIdGnrService = patientIdGnrService;
		this.admissionIdGnrService = admissionIdGnrService;
        this.messageSource = messageSource;

        LOGGER.info("admissionDao {}", admissionDao);
	}

	/**
	 * 로그인ID 기준 격리/입소내역(내원중) 리스트 조회
	 *
	 * @param loginId 로그인ID
	 * @return AdmissionVO 격리/입소내역 정보
	 * @throws NotFoundAdmissionInfoException 현재일 기준 내원중인 격리/입소내역이 존재하지 않거나, 한건 이상일 경우 발생
	 */
	public AdmissionVO selectActiveAdmissionByLoginId(String loginId) throws NotFoundAdmissionInfoException {
		List<AdmissionVO> admissionVOS = admissionDao.selectActiveAdmissionListByLoginId(loginId);

		if (admissionVOS == null || admissionVOS.size() == 0) {
			throw new NotFoundAdmissionInfoException(ApiResponseCode.NOT_FOUND_ADMISSION_INFO.getCode()
                , messageSource.getMessage("message.notfound.admissionInfo"
                , null, Locale.getDefault()));
		} else if (admissionVOS.size() > 1) {
            throw new NotFoundAdmissionInfoException(ApiResponseCode.DUPLICATE_ACTIVE_ADMISSION_INFO.getCode()
                , messageSource.getMessage("message.duplicate.admissionInfo"
                , null, Locale.getDefault()));
		}

		return admissionVOS.get(0);
	}

	/**
	 * 생활치료센터 입소자 리스트 조회
	 *
	 * @param vo 입소자 리스트 조회조건
	 * @return List<AdmissionListVO> 입소자 리스트
	 */
	public AdmissionListResponseByCenterVO selectAdmissionListByCenter(AdmissionListSearchByCenterVO vo) {
		AdmissionListResponseByCenterVO admissionListResponseByCenterVO = new AdmissionListResponseByCenterVO();

		// 생활치료센터 입소자 리스트
		admissionListResponseByCenterVO.setAdmissionByCenterVOList(admissionDao.selectAdmissionListByCenter(vo));

		// 최근 측정결과 조회
		for (AdmissionByCenterVO admissionByCenterVO : admissionListResponseByCenterVO.getAdmissionByCenterVOList()) {
			VitalResultVO vitalResultVO = resultDao.selectLastVitalResult(admissionByCenterVO.getAdmissionId());

			if (vitalResultVO != null) {
				BeanUtils.copyProperties(vitalResultVO, admissionByCenterVO);
			}
		}

		// 페이징 정보 바인딩
		vo.setTotalRecordCount(admissionDao.selectFoundRowsByAdmission());
		admissionListResponseByCenterVO.setPaginationInfoVO(vo);


		return admissionListResponseByCenterVO;
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 변경 작업 대상
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 입소내역 정보 조회
	 * @param admissionId-입소내역ID
	 * @return AdmissionInfoVO-입소내역 정보
	 */
	public AdmissionInfoVO selectAdmissionInfo(String admissionId) {
		return admissionDao.selectAdmissionInfo(admissionId);
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
			admissionDao.insertAdmission(admissionVO);
			
		} else {
			admissionId = admissionVO.getAdmissionId(); 
					
			// 입소내역 수정
			admissionDao.updateAdmission(admissionVO);
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
		
		return admissionDao.updateAdmissionDischarge(vo);
	}
}
