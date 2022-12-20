package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.QantnDiv;
import kr.co.hconnect.domain.*;
import kr.co.hconnect.exception.*;
import kr.co.hconnect.repository.AdmissionDao;
import kr.co.hconnect.repository.InterviewDao;
import kr.co.hconnect.repository.PatientDao;
import kr.co.hconnect.repository.ResultDao;
import kr.co.hconnect.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
     * 인터뷰 dao
     */
    private final InterviewDao interviewDao;
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
     *
     * @param admissionDao          격리/입소내역 Dao
     * @param interviewDao
     * @param resultDao             측정결과 Dao
     * @param patientDao            환자정보 관리 Dao
     * @param patientIdGnrService   환자 ID 채번 서비스
     * @param admissionIdGnrService 격리/입소 ID 채번 서비스
     * @param messageSource         MessageSource
     */
	@Autowired
	public AdmissionService(AdmissionDao admissionDao, InterviewDao interviewDao, ResultDao resultDao, PatientDao patientDao
			, @Qualifier("patientIdGnrService") EgovIdGnrService patientIdGnrService
			, @Qualifier("admissionIdGnrService") EgovIdGnrService admissionIdGnrService
			, MessageSource messageSource) {
		this.admissionDao = admissionDao;
        this.interviewDao = interviewDao;
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
     * 로그인ID 기준 종료된 것 포함 격리/입소내역(내원중) 리스트 조회
     *
     * @param loginId 로그인ID
     * @return AdmissionVO 격리/입소내역 정보
     * @throws NotFoundAdmissionInfoException 현재일 기준 내원중인 격리/입소내역이 존재하지 않거나, 한건 이상일 경우 발생
     */
    public AdmissionVO selectAdmissionListByLoginId(String loginId) throws NotFoundAdmissionInfoException {
        List<AdmissionVO> admissionVOS = admissionDao.selectAdmissionListByLoginId(loginId);

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
	 * 입소내역 정보 조회
	 * @param admissionId 입소내역ID
	 * @return AdmissionInfoVO 입소내역 정보
	 */
	public AdmissionInfoVO selectAdmissionInfo(String admissionId) throws NotFoundAdmissionInfoException {
		AdmissionInfoVO admissionInfoVO = admissionDao.selectAdmissionInfo(admissionId);

		if (admissionInfoVO == null) {
			throw new NotFoundAdmissionInfoException(ApiResponseCode.NOT_FOUND_ADMISSION_INFO.getCode()
					, messageSource.getMessage("message.notfound.admissionInfo"
					, null, Locale.getDefault()));
		}

		return admissionInfoVO;
	}

	/**
	 * 생활치료센터 입소자 리스트 조회
	 *
	 * @param vo 입소자 리스트 조회조건
	 * @return AdmissionListResponseByCenterVO 입소자 리스트
	 */
	public AdmissionListResponseByCenterVO selectAdmissionListByCenter(AdmissionListSearchByCenterVO vo) {
		AdmissionListResponseByCenterVO admissionListResponseByCenterVO = new AdmissionListResponseByCenterVO();

		// 생활치료센터 입소자 리스트
		admissionListResponseByCenterVO.setAdmissionByCenterVOList(admissionDao.selectAdmissionListByCenter(vo));

		// 페이징 정보 바인딩
		vo.setTotalRecordCount(admissionDao.selectFoundRowsByAdmission());
		admissionListResponseByCenterVO.setPaginationInfoVO(vo);

		// 최근 측정결과 조회
		for (AdmissionByCenterVO admissionByCenterVO : admissionListResponseByCenterVO.getAdmissionByCenterVOList()) {
			VitalResultVO vitalResultVO = resultDao.selectLastVitalResult(admissionByCenterVO.getAdmissionId());

			if (vitalResultVO != null) {
				BeanUtils.copyProperties(vitalResultVO, admissionByCenterVO);
			}
		}

		return admissionListResponseByCenterVO;
	}

	/**
	 * 생활치료센터 입소자 등록/수정
	 *
	 * @param admissionVO 격리/입소자 저장 정보
	 * @param patientIdentityVO 환자 정보
	 * @param isNew true: 신규, false: 수정
	 * @return 저장된 입소내역ID
	 *
	 * @throws FdlException 환자ID, 격리/입소내역ID 채번 오류 시 발생
	 * @throws DuplicateActiveAdmissionException 신규 입소자 등록 시 이미 존재하는 격리/입소 정보가 있을 경우 발생
	 * @throws DuplicatePatientInfoException 환자 정보 변경 시 중복된 환자로 변경 시 발생
	 * @throws NotFoundAdmissionInfoException 입소 정보 수정 시 기존 입소정보가 존재하지 않을 경우 발생
	 * @throws InvalidAdmissionInfoException 기존 입소정보에 문제가 있을 경우 발생(종료예정일이 시작일 이후가 아닌 경우, 삭제된 입소자, 퇴소된 입소자, 격리/입소구분이 입소가 아닌경우)
	 */
	@Transactional(rollbackFor = Exception.class)
	public String saveAdmission(AdmissionVO admissionVO, PatientIdentityVO patientIdentityVO, Boolean isNew)
			throws FdlException, DuplicateActiveAdmissionException, DuplicatePatientInfoException
			,NotFoundPatientInfoException, NotFoundAdmissionInfoException, InvalidAdmissionInfoException {
		// 시작일, 종료예정일 확인
		if (admissionVO.getAdmissionDate().compareTo(admissionVO.getDschgeSchdldDate()) >= 0) {
			throw new InvalidAdmissionInfoException(messageSource.getMessage("message.admission.past.dschgeSchdldDate"
					, null, Locale.getDefault()));
		}

		// 격리/입소내역ID
		String admissionId;
		// 격리/입소구분
		String qantnDiv = admissionVO.getQantnDiv();

		if (isNew) {
			String patientId;

			// 기존 환자 존재여부 확인
			// TODO:: 2세부 API 변경 작업과 함께 수정 작업진행 필요
			IdentityInfo identityInfo = new IdentityInfo();
			identityInfo.setPatientNm(patientIdentityVO.getPatientNm());
			identityInfo.setBirthDate(patientIdentityVO.getBirthDate());
			identityInfo.setSex(patientIdentityVO.getSex());
			identityInfo.setCellPhone(patientIdentityVO.getCellPhone());
            identityInfo.setSearsAccount(patientIdentityVO.getSearsAccount());

			Patient patientByIdentityInfo = patientDao.selectPatientByIdentityInfo(identityInfo);

			// 퇴소처리 안된 격리/입소내역 존재여부 확인
			if (patientByIdentityInfo != null) {
				List<AdmissionVO> admissionVOList = admissionDao.selectActiveAdmissionListByPatientId(patientByIdentityInfo.getPatientId());

				if (admissionVOList.size() > 0) {
					AdmissionVO activeAdmissionVO = admissionVOList.get(0);
					String activeInfo = activeAdmissionVO.getQantnDiv().equals(QantnDiv.CENTER.getDbValue())
							? activeAdmissionVO.getCenterNm() + " 입소"
							: " 격리";

					// {0}중인 내역이 존재합니다
					throw new DuplicateActiveAdmissionException(messageSource.getMessage("message.admission.duplicate.active"
							, new Object[] { activeInfo }, Locale.getDefault()));
				}

				patientId = patientByIdentityInfo.getPatientId();

			} else {
				// 신규 환자정보 구성
				patientId = patientIdGnrService.getNextStringId();

				PatientVO patientVO = new PatientVO();
				patientVO.setPatientId(patientId);
				patientVO.setPatientNm(patientIdentityVO.getPatientNm());
				patientVO.setBirthDate(patientIdentityVO.getBirthDate());
				patientVO.setCellPhone(patientIdentityVO.getCellPhone());
				patientVO.setSex(patientIdentityVO.getSex());
				patientVO.setRegId(admissionVO.getRegId());
				patientVO.setSearsAccount(patientIdentityVO.getSearsAccount());


				patientDao.insertPatient(patientVO);
			}

			// 입소내역 생성
			admissionId = admissionIdGnrService.getNextStringId();
			admissionVO.setAdmissionId(admissionId);
			admissionVO.setPatientId(patientId);

			admissionDao.insertAdmission(admissionVO);

		} else {
			// 기존 환자 정보 확인
			PatientVO dbPatientVO = patientDao.selectPatientByPatientId(admissionVO.getPatientId());
			if (dbPatientVO == null) {
				throw new NotFoundPatientInfoException(messageSource.getMessage("message.notfound.patientInfo", null, Locale.getDefault()));
			}

			// 환자정보 수정
			PatientVO patientVO = new PatientVO();
			patientVO.setPatientId(admissionVO.getPatientId());
			patientVO.setPatientNm(patientIdentityVO.getPatientNm());
			patientVO.setBirthDate(patientIdentityVO.getBirthDate());
			patientVO.setCellPhone(patientIdentityVO.getCellPhone());
			patientVO.setSex(patientIdentityVO.getSex());
			patientVO.setUpdId(admissionVO.getUpdId());
			patientVO.setSearsAccount(patientIdentityVO.getSearsAccount());



			// 변경 데이터 확인
			if (!patientVO.isIdentityEquals(dbPatientVO)) {
				// 변경 정보 환자 중복여부 확인
				// TODO:: 2세부 API 변경 작업과 함께 수정 작업진행 필요
				IdentityInfo identityInfo = new IdentityInfo();
				identityInfo.setPatientNm(patientIdentityVO.getPatientNm());
				identityInfo.setBirthDate(patientIdentityVO.getBirthDate());
				identityInfo.setSex(patientIdentityVO.getSex());
				identityInfo.setCellPhone(patientIdentityVO.getCellPhone());
				identityInfo.setSearsAccount(patientIdentityVO.getSearsAccount());

				Patient patientByIdentityInfo = patientDao.selectPatientByIdentityInfo(identityInfo);

				if (patientByIdentityInfo != null) {
					throw new DuplicatePatientInfoException(messageSource.getMessage("message.duplicate.patientInfo", null, Locale.getDefault()));
				}

				patientDao.updatePatient(patientVO);
			}

			// 입소내역 수정
			admissionId = admissionVO.getAdmissionId();

			// 기존 입소내역 확인
			AdmissionInfoVO dbAdmissionVO = admissionDao.selectAdmissionInfo(admissionId);
			if (dbAdmissionVO == null) {
				throw new NotFoundAdmissionInfoException(ApiResponseCode.NOT_FOUND_ADMISSION_INFO.getCode()
						, messageSource.getMessage("message.notfound.admissionInfo"
						, null, Locale.getDefault()));
			} else if (!dbAdmissionVO.getQantnDiv().equals(qantnDiv)) {
				throw new InvalidAdmissionInfoException(messageSource.getMessage("message.admission.mismatch.qantnDiv"
						, null, Locale.getDefault()));
			} else if (dbAdmissionVO.getDelYn().equals("Y")) {
				throw new InvalidAdmissionInfoException(messageSource.getMessage("message.admission.delete"
						, null, Locale.getDefault()));
			} else if (dbAdmissionVO.getDschgeDate() != null) {
				throw new InvalidAdmissionInfoException(messageSource.getMessage("message.admission.discharge"
						, null, Locale.getDefault()));
			}

			admissionDao.updateAdmission(admissionVO);
		}

		return admissionId;
	}

	/**
	 * 퇴소 처리
	 *
	 * @param vo AdmissionDischargeVO 퇴소 처리 정보 VO
	 * @throws NotFoundAdmissionInfoException 입소 정보 수정 시 기존 입소정보가 존재하지 않을 경우 발생
	 * @throws InvalidAdmissionInfoException 기존 입소정보에 문제가 있을 경우 발생(삭제, 퇴소, 격리/입소구분이 다른 경우)
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateAdmissionDischarge(AdmissionVO vo)
			throws NotFoundAdmissionInfoException, InvalidAdmissionInfoException {
		// 기존 입소내역 확인
		AdmissionInfoVO dbAdmissionVO = admissionDao.selectAdmissionInfo(vo.getAdmissionId());

		if (dbAdmissionVO == null) {
			// 격리/입소내역 확인
			throw new NotFoundAdmissionInfoException(ApiResponseCode.NOT_FOUND_ADMISSION_INFO.getCode()
					, messageSource.getMessage("message.notfound.admissionInfo"
					, null, Locale.getDefault()));
		} else if (dbAdmissionVO.getAdmissionDate().compareTo(vo.getDschgeDate()) >= 0) {
			// 종료일이 시작일 이후 여부 확인
			throw new InvalidAdmissionInfoException(messageSource.getMessage("message.admission.past.dschgeDate"
					, null, Locale.getDefault()));
		} else if (!dbAdmissionVO.getQantnDiv().equals(vo.getQantnDiv())) {
			// 지정한 격리/입소 내역과 동일한지 확인
			throw new InvalidAdmissionInfoException(messageSource.getMessage("message.admission.mismatch.qantnDiv"
					, null, Locale.getDefault()));
		} else if (dbAdmissionVO.getDelYn().equals("Y")) {
			// 삭제 여부 확인
			throw new InvalidAdmissionInfoException(messageSource.getMessage("message.admission.delete"
					, null, Locale.getDefault()));
		} else if (dbAdmissionVO.getDschgeDate() != null) {
			// 퇴소 여부 확인
			throw new InvalidAdmissionInfoException(messageSource.getMessage("message.admission.discharge"
					, null, Locale.getDefault()));
		}

		// 퇴소 처리
		admissionDao.updateAdmissionDischarge(vo);
	}


	/**
	 * 자가격리지 리스트 조회
	 *
	 * @param vo 자가격리지 리스트 조회조건
	 * @return AdmissionListResponseByQuarantineVO 자가격리자 리스트
	 */
	public AdmissionListResponseByQuarantineVO selectAdmissionListByQuarantine(AdmissionListSearchByQuarantineVO vo) {
		AdmissionListResponseByQuarantineVO admissionListResponseByQuarantineVO = new AdmissionListResponseByQuarantineVO();

		// 자가격리자 리스트
		admissionListResponseByQuarantineVO.setAdmissionByQuarantineVOList(admissionDao.selectAdmissionListByQuarantine(vo));

		// 페이징 정보 바인딩
		vo.setTotalRecordCount(admissionDao.selectFoundRowsByAdmission());
		admissionListResponseByQuarantineVO.setPaginationInfoVO(vo);

		// 최근 측정결과 조회
		for (AdmissionByQuarantineVO admissionByQuarantineVO : admissionListResponseByQuarantineVO.getAdmissionByQuarantineVOList()) {
			VitalResultVO vitalResultVO = resultDao.selectLastVitalResult(admissionByQuarantineVO.getAdmissionId());

			if (vitalResultVO != null) {
				BeanUtils.copyProperties(vitalResultVO, admissionByQuarantineVO);
			}
		}

		return admissionListResponseByQuarantineVO;
	}
    /**
     * 문진리스트 조회
     *
     * @param interview
     * @return AdmissionListResponseByQuarantineVO 자가격리자 리스트
     */
    public List<InterviewList> selectInterviewList(Interview interview){

        // 사용자가 입력한 문진 조회
        List<InterviewList> interviewLists = new ArrayList<>();
        interviewLists = interviewDao.selectInterviewList(interview);

        //검색조건 담기위한
        InterviewSearchVO serachVO = new InterviewSearchVO();

        for(InterviewList interviewList : interviewLists){
            //설문지 내용 조회
            List<InterviewContentVO> contentVOS = new ArrayList<>();
            serachVO.setInterviewType(interviewList.getInterviewType());
            serachVO.setInterviewSeq(interviewList.getInterviewSeq());
            contentVOS.addAll(interviewDao.selectInterviewContentList(serachVO));

            //설문지에 대한 답변 조회
            InterviewDetailVO detailVO = new InterviewDetailVO();
            for(InterviewContentVO vo: contentVOS){
                serachVO.setAnswerSeq(vo.getInterNo());
                detailVO=interviewDao.selectInterviewDetailList(serachVO);
                if (detailVO != null) {
                    vo.setAnswerValue(detailVO.getAnswerValue());
                    vo.setInterviewDetailSeq(detailVO.getInterviewDetailSeq());
                }
            }

            interviewList.setInterviewContents(contentVOS);
        }


        return interviewLists;

    }
}
