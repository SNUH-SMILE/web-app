package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.exception.NotFoundAdmissionInfoException;
import kr.co.hconnect.repository.PatientDetailDashboardDao;
import kr.co.hconnect.vo.PatientDetailDashboardHeaderVO;
import kr.co.hconnect.vo.PatientDetailDashboardVO;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

/**
 * 환자 상세 대쉬보드 서비스
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class PatientDetailDashboardService extends EgovAbstractServiceImpl {

	/**
	 * 환자 상세 대쉬보드 Dao
	 */
	private final PatientDetailDashboardDao patientDetailDashboardDao;
	/**
	 * MessageSource
	 */
	private final MessageSource messageSource;
	
	/**
	 * 생성자
	 * @param patientDetailDashboardDao 환자 상세 대쉬보드 DAO
	 * @param messageSource MessageSource
	 */
	public PatientDetailDashboardService(PatientDetailDashboardDao patientDetailDashboardDao, MessageSource messageSource) {
		this.patientDetailDashboardDao = patientDetailDashboardDao;
		this.messageSource = messageSource;
	}

	/**
	 * 환자 상세 대시보드 정보 조회
	 * @param admissionId 격리/입소내역ID
	 * @return PatientDetailDashboardVO 환자 상세 대시보드 정보
	 */
	public PatientDetailDashboardVO selectPatientDetailDashboard(String admissionId)
			throws NotFoundAdmissionInfoException {
		// 01. 상단 헤더 정보 조회
		PatientDetailDashboardHeaderVO patientDetailDashboardHeaderVO = patientDetailDashboardDao.selectPatientDetailDashboardHeader(admissionId);
		if (patientDetailDashboardHeaderVO == null) {
			// 내원정보 존재여부 확인
			throw new NotFoundAdmissionInfoException(ApiResponseCode.NOT_FOUND_ADMISSION_INFO.getCode()
					, messageSource.getMessage("message.notfound.admissionInfo"
					, null, Locale.getDefault()));
		}

		// 02. 상단 최근 측정결과 조회


		PatientDetailDashboardVO vo = new PatientDetailDashboardVO();
		vo.setAdmissionId(admissionId);
		vo.setHeaderVO(patientDetailDashboardHeaderVO);		// 상단 헤더 정보 바인딩

		return vo;
	}

	/**
	 * 환자 상세 대시보드 상단 정보 조회
	 *
	 * @param admissionId 격리/입소내역ID
	 * @return PatientDetailDashboardHeaderVO 환자 상세 대시보드 상단 정보
	 * @throws NotFoundAdmissionInfoException 내원정보가 없는 경우 발생
	 */
	public PatientDetailDashboardHeaderVO selectPatientDetailDashboardHeader(String admissionId)
			throws NotFoundAdmissionInfoException {
		PatientDetailDashboardHeaderVO vo = patientDetailDashboardDao.selectPatientDetailDashboardHeader(admissionId);

		if (vo == null) {
			// 내원정보 존재여부 확인
			throw new NotFoundAdmissionInfoException(ApiResponseCode.NOT_FOUND_ADMISSION_INFO.getCode()
					, messageSource.getMessage("message.notfound.admissionInfo"
					, null, Locale.getDefault()));
		}

		return vo;
	}
	
//	/**
//	 * 환자 상세 대쉬보드 입소내역 조회
//	 * @param admissionId - 입소ID
//	 * @return PatientDetailDashboardPatientInfoVO - 환자 상세 대쉬보드 환자정보 VO
//	 */
//	public PatientDetailDashboardPatientInfoVO selectPatientDetailDashboardPatientInfo(String admissionId) {
//		return patientDetailDashboardDao.selectPatientDetailDashboardPatientInfo(admissionId);
//	}
//
//	/**
//	 * 환자 상세 대쉬보드 측졍결과 리스트 조회
//	 * @param patientDetailDashboardResultSearchVO - 측정결과 조회 조건
//	 * @return List<PatientDetailDashboardResultVO> - 환자 상세 대쉬보드 측정결과 리스트
//	 */
//	public List<PatientDetailDashboardResultVO> selectPatientDetailDashboardResultList(
//			PatientDetailDashboardResultSearchVO patientDetailDashboardResultSearchVO) {
//		return patientDetailDashboardDao.selectPatientDetailDashboardResultList(patientDetailDashboardResultSearchVO);
//	}
//
//
//	/**
//	 * 환자상세 대쉬보드 전체 데이터 조회 (초기 로딩시 사용)
//	 * 환자상세 정보 + 최근 측정결과 V.I + 측정결과 리스트
//	 * @param admissionId - 입소ID
//	 * @return PatientDashboardInfoVO - 환자상세 대쉬보드 전체 데이터 VO
//	 */
//	public PatientDashboardInfoVO selectAllPatientDetailDashboardInfo(String admissionId){
//
//		PatientDetailDashboardResultSearchVO patientDetailDashboardResultSearchVO
//				= new PatientDetailDashboardResultSearchVO();
//		patientDetailDashboardResultSearchVO.setAdmissionId(admissionId);
//
//		PatientDashboardInfoVO patientDashboardInfoVO = new PatientDashboardInfoVO();
//
//		patientDashboardInfoVO.setPatientDashboardDetails(this.selectPatientDetailDashboardPatientInfo(admissionId));
//
//		//입소일자를 검색 FROM DATE 로 지정
//		patientDetailDashboardResultSearchVO.setResultFromDt(
//				patientDashboardInfoVO.getPatientDashboardDetails().getAdmitDate());
//
//		//오늘 일자를 검색 TO DATE 로 지정
//		patientDetailDashboardResultSearchVO.setResultToDt(new Date());
//
//		patientDashboardInfoVO.setPatientDashboardResultVOs(
//				this.selectPatientDetailDashboardResultList(patientDetailDashboardResultSearchVO));
//
//		return patientDashboardInfoVO;
//	}
//
//	/**
//	 * 센터 측정 ITEM항목 LIST select
//	 * @return List<ItemVO> - 측정 ITEM항목 VO LIST
//	 */
//	public List<ItemVO> selectCenterItemListVO(){
//
//		/*
//		  TODO 측정 ITEM 항목 변경하기 (임시 로직)
//		 */
//		String[] items = {"I0005", "I0002", "I0001", "I0004", "I0003"};
//		List<ItemVO> itemVOList = new ArrayList<>();
//
//		Arrays.asList(items).forEach((item)->{
//			ItemVO itemVO = patientDetailDashboardDao.selectCenterItemList(item);
//			itemVOList.add(itemVO);
//		});
//
//		return itemVOList;
//	}

}
