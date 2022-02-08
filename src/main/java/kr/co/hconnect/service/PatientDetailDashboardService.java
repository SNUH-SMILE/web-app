package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.repository.PatientDetailDashboardDao;
import kr.co.hconnect.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
	 * 생성자
	 * @param patientDetailDashboardDao - 환자 상세 대쉬보드 DAO
	 */
	public PatientDetailDashboardService(PatientDetailDashboardDao patientDetailDashboardDao) {
		this.patientDetailDashboardDao = patientDetailDashboardDao;
	}
	
	/**
	 * 환자 상세 대쉬보드 입소내역 조회
	 * @param admissionId - 입소ID
	 * @return PatientDetailDashboardPatientInfoVO - 환자 상세 대쉬보드 환자정보 VO
	 */
	public PatientDetailDashboardPatientInfoVO selectPatientDetailDashboardPatientInfo(String admissionId) {
		return patientDetailDashboardDao.selectPatientDetailDashboardPatientInfo(admissionId);
	}

	/**
	 * 환자 상세 대쉬보드 측졍결과 리스트 조회
	 * @param patientDetailDashboardResultSearchVO - 측정결과 조회 조건
	 * @return List<PatientDetailDashboardResultVO> - 환자 상세 대쉬보드 측정결과 리스트
	 */
	public List<PatientDetailDashboardResultVO> selectPatientDetailDashboardResultList(
			PatientDetailDashboardResultSearchVO patientDetailDashboardResultSearchVO) {
		return patientDetailDashboardDao.selectPatientDetailDashboardResultList(patientDetailDashboardResultSearchVO);
	}


	/**
	 * 환자상세 대쉬보드 전체 데이터 조회 (초기 로딩시 사용)
	 * 환자상세 정보 + 최근 측정결과 V.I + 측정결과 리스트
	 * @param admissionId - 입소ID
	 * @return PatientDashboardInfoVO - 환자상세 대쉬보드 전체 데이터 VO
	 */
	public PatientDashboardInfoVO selectAllPatientDetailDashboardInfo(String admissionId){

		PatientDetailDashboardResultSearchVO patientDetailDashboardResultSearchVO
				= new PatientDetailDashboardResultSearchVO();
		patientDetailDashboardResultSearchVO.setAdmissionId(admissionId);

		PatientDashboardInfoVO patientDashboardInfoVO = new PatientDashboardInfoVO();

		patientDashboardInfoVO.setPatientDashboardDetails(this.selectPatientDetailDashboardPatientInfo(admissionId));

		//입소일자를 검색 FROM DATE 로 지정
		patientDetailDashboardResultSearchVO.setResultFromDt(
				patientDashboardInfoVO.getPatientDashboardDetails().getAdmitDate());

		//오늘 일자를 검색 TO DATE 로 지정
		patientDetailDashboardResultSearchVO.setResultToDt(new Date());

		patientDashboardInfoVO.setPatientDashboardResultVOs(
				this.selectPatientDetailDashboardResultList(patientDetailDashboardResultSearchVO));

		return patientDashboardInfoVO;
	}

	/**
	 * 센터 측정 ITEM항목 LIST select
	 * @return List<ItemVO> - 측정 ITEM항목 VO LIST
	 */
	public List<ItemVO> selectCenterItemListVO(){

		/*
		  TODO 측정 ITEM 항목 변경하기 (임시 로직)
		 */
		String[] items = {"I0001", "I0002", "I0003", "I0004", "I0005"};
		List<ItemVO> itemVOList = new ArrayList<>();

		Arrays.asList(items).forEach((item)->{
			ItemVO itemVO = patientDetailDashboardDao.selectCenterItemList(item);
			itemVOList.add(itemVO);
		});

		return itemVOList;
	}

}
