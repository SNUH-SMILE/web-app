package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.exception.NotFoundAdmissionInfoException;
import kr.co.hconnect.repository.NoticeDao;
import kr.co.hconnect.repository.PatientDetailDashboardDao;
import kr.co.hconnect.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
	 * 알림 Dao
	 */
	private final NoticeDao noticeDao;	
	/**
	 * MessageSource
	 */
	private final MessageSource messageSource;
	
	/**
	 * 생성자
	 * @param patientDetailDashboardDao 환자 상세 대쉬보드 DAO
	 * @param noticeDao 알림 Dao
	 * @param messageSource MessageSource
	 */
	public PatientDetailDashboardService(PatientDetailDashboardDao patientDetailDashboardDao, NoticeDao noticeDao, MessageSource messageSource) {
		this.patientDetailDashboardDao = patientDetailDashboardDao;
		this.noticeDao = noticeDao;
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

		// 02. 마지막 신체계측, 수면, 예측 결과 조회
		// 최근 측정결과 조회
		PatientDetailDashboardRecentResultVO recentResultVO = patientDetailDashboardDao.selectPatientDetailDashboardRecentResult(admissionId);
		if (recentResultVO != null) {
			patientDetailDashboardHeaderVO.setRecentResultInfo(recentResultVO);
		}

		// 03. 환자 healthSignal 조회
		// TODO::AI 예측 결과 도입 후 작업필요
		PatientHealthSignalVO healthSignalVO = new PatientHealthSignalVO();
		patientDetailDashboardHeaderVO.setHealthSignalVO(healthSignalVO);

		// 04. 알림 내역 조회
		NoticeListSearchVO noticeListSearchVO = new NoticeListSearchVO();
		noticeListSearchVO.setAdmissionId(admissionId);
		List<NoticeVO> noticeVOList = noticeDao.selectNoticeListByAdmissionId(noticeListSearchVO);		
		
		PatientDetailDashboardVO vo = new PatientDetailDashboardVO();
		vo.setAdmissionId(admissionId);
		vo.setHeaderVO(patientDetailDashboardHeaderVO);		// 상단 헤더 정보 바인딩
		vo.setNoticeVOList(noticeVOList);					// 알림내역

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

	/**
	 * 환자 Vital 측정결과 차트 표현 데이터 VO
	 * @param vo 환자 차트용 Vital 조회조건 VO
	 * @return PatientResultChartDataVO 환자 Vital 측정결과 차트 표현 데이터
	 * @throws NotFoundAdmissionInfoException 격리/입소된 내역이 없을 경우 발생
	 */
	public PatientResultChartDataVO selectPatientResultChartData(PatientVitalChartDataSearchVO vo)
			throws NotFoundAdmissionInfoException {
		// 01. 상단 헤더 정보 조회
		PatientDetailDashboardHeaderVO patientDetailDashboardHeaderVO = patientDetailDashboardDao.selectPatientDetailDashboardHeader(vo.getAdmissionId());
		if (patientDetailDashboardHeaderVO == null) {
			// 내원정보 존재여부 확인
			throw new NotFoundAdmissionInfoException(ApiResponseCode.NOT_FOUND_ADMISSION_INFO.getCode()
					, messageSource.getMessage("message.notfound.admissionInfo"
					, null, Locale.getDefault()));
		}

		// 02. 환자 상태 알림 조회
		// TODO::AI 예측 결과 도입 후 작업필요
		PatientHealthSignalVO healthSignalVO = new PatientHealthSignalVO();
		healthSignalVO.setSignal1Yn("N");
		healthSignalVO.setSignal2Yn("N");

		// 03. 측정 결과 조회 (선택한 일자 기준, 당일일 경우 현재시간까지 데이터 조회)
		List<PatientVitalChartDataVO> tempList = patientDetailDashboardDao.selectPatientVitalChartData(vo);
//		측정결과가 없는 내역 제외
//		List<PatientVitalChartDataVO> tempList = list.stream()
//				.filter(x ->
//						!(StringUtils.isEmpty(x.getBt()) &&
//								StringUtils.isEmpty(x.getPr()) &&
//								StringUtils.isEmpty(x.getSpo2()) &&
//								StringUtils.isEmpty(x.getSbp()) &&
//								StringUtils.isEmpty(x.getDbp()))
//				)
//				.collect(Collectors.toList());

		List<PatientVitalChartVO> btList = new ArrayList<>();
		List<PatientVitalChartVO> prList = new ArrayList<>();
		List<PatientVitalChartVO> rrList = new ArrayList<>();
		List<PatientVitalChartVO> spo2List = new ArrayList<>();
		List<PatientVitalChartVO> sbpList = new ArrayList<>();
		List<PatientVitalChartVO> dbpList = new ArrayList<>();

		// 쿼리 상에서 조회일기준 00:00:00 부터 15분 단위 데이터 조회
		// 누락된 데이터 가공처리
		//  1.데이터 시점 기준 이후 데이터 바인딩
		//  2.이후 데이터가 없을 경우 이전 데이터 바인딩
		for (PatientVitalChartDataVO chartDataVO : tempList) {
			if (StringUtils.isEmpty(chartDataVO.getBt())) {
				// 측정 결과가 없는 경우 측정일시 기준 이후 데이터 조히
				Optional<String> bt = tempList.stream()
						.filter(x -> x.getResultDt().after(chartDataVO.getResultDt()) && StringUtils.isNotEmpty(x.getBt()))
						.findFirst()
						.map(PatientVitalChartDataVO::getBt);

				bt.ifPresent(chartDataVO::setBt);

				// 이후 데이터가 업는 경우 이전 데이터 바인딩 처리
				if (StringUtils.isEmpty(chartDataVO.getBt())) {
					bt = tempList.stream()
							.filter(x -> x.getResultDt().before(chartDataVO.getResultDt()) && StringUtils.isNotEmpty(x.getBt()))
							.max(Comparator.comparing(PatientVitalChartDataVO::getResultDt))
							.map(PatientVitalChartDataVO::getBt);

					bt.ifPresent(chartDataVO::setBt);
				}
			}

			if (StringUtils.isEmpty(chartDataVO.getPr())) {
				Optional<String> pr = tempList.stream()
						.filter(x -> x.getResultDt().after(chartDataVO.getResultDt()) && StringUtils.isNotEmpty(x.getPr()))
						.findFirst()
						.map(PatientVitalChartDataVO::getPr);

				pr.ifPresent(chartDataVO::setPr);

				if (StringUtils.isEmpty(chartDataVO.getPr())) {
					pr = tempList.stream()
							.filter(x -> x.getResultDt().before(chartDataVO.getResultDt()) && StringUtils.isNotEmpty(x.getPr()))
							.max(Comparator.comparing(PatientVitalChartDataVO::getResultDt))
							.map(PatientVitalChartDataVO::getPr);

					pr.ifPresent(chartDataVO::setPr);
				}
			}

			if (StringUtils.isEmpty(chartDataVO.getSpo2())) {
				Optional<String> spo2 = tempList.stream()
						.filter(x -> x.getResultDt().after(chartDataVO.getResultDt()) && StringUtils.isNotEmpty(x.getSpo2()))
						.findFirst()
						.map(PatientVitalChartDataVO::getSpo2);

				spo2.ifPresent(chartDataVO::setSpo2);

				if (StringUtils.isEmpty(chartDataVO.getSpo2())) {
					spo2 = tempList.stream()
							.filter(x -> x.getResultDt().before(chartDataVO.getResultDt()) && StringUtils.isNotEmpty(x.getSpo2()))
							.max(Comparator.comparing(PatientVitalChartDataVO::getResultDt))
							.map(PatientVitalChartDataVO::getSpo2);

					spo2.ifPresent(chartDataVO::setSpo2);
				}
			}

			if (StringUtils.isEmpty(chartDataVO.getSbp())) {
				Optional<String> sbp = tempList.stream()
						.filter(x -> x.getResultDt().after(chartDataVO.getResultDt()) && StringUtils.isNotEmpty(x.getSbp()))
						.findFirst()
						.map(PatientVitalChartDataVO::getSbp);

				sbp.ifPresent(chartDataVO::setSbp);

				if (StringUtils.isEmpty(chartDataVO.getSbp())) {
					sbp = tempList.stream()
							.filter(x -> x.getResultDt().before(chartDataVO.getResultDt()) && StringUtils.isNotEmpty(x.getSbp()))
							.max(Comparator.comparing(PatientVitalChartDataVO::getResultDt))
							.map(PatientVitalChartDataVO::getSbp);

					sbp.ifPresent(chartDataVO::setSbp);
				}
			}

			if (StringUtils.isEmpty(chartDataVO.getDbp())) {
				Optional<String> dbp = tempList.stream()
						.filter(x -> x.getResultDt().after(chartDataVO.getResultDt()) && StringUtils.isNotEmpty(x.getDbp()))
						.findFirst()
						.map(PatientVitalChartDataVO::getDbp);

				dbp.ifPresent(chartDataVO::setDbp);

				if (StringUtils.isEmpty(chartDataVO.getDbp())) {
					dbp = tempList.stream()
							.filter(x -> x.getResultDt().before(chartDataVO.getResultDt()) && StringUtils.isNotEmpty(x.getDbp()))
							.max(Comparator.comparing(PatientVitalChartDataVO::getResultDt))
							.map(PatientVitalChartDataVO::getDbp);

					dbp.ifPresent(chartDataVO::setDbp);
				}
			}

			PatientVitalChartVO btVal = new PatientVitalChartVO();
			btVal.setX(chartDataVO.getResultDt());
			btVal.setY(chartDataVO.getBt()) ;
			btList.add(btVal);

			PatientVitalChartVO prVal = new PatientVitalChartVO();
			prVal.setX(chartDataVO.getResultDt());
			prVal.setY(chartDataVO.getPr());
			prList.add(prVal);

			PatientVitalChartVO spo2Val = new PatientVitalChartVO();
			spo2Val.setX(chartDataVO.getResultDt());
			spo2Val.setY(chartDataVO.getSpo2());
			spo2List.add(spo2Val);

			PatientVitalChartVO rrVal = new PatientVitalChartVO();
			rrVal.setX(chartDataVO.getResultDt());
			rrVal.setY(null);
			rrList.add(rrVal);

			PatientVitalChartVO sbpVal = new PatientVitalChartVO();
			sbpVal.setX(chartDataVO.getResultDt());
			sbpVal.setY(chartDataVO.getSbp());
			sbpList.add(sbpVal);

			PatientVitalChartVO dbpVal = new PatientVitalChartVO();
			dbpVal.setX(chartDataVO.getResultDt());
			dbpVal.setY(chartDataVO.getDbp());
			dbpList.add(dbpVal);
		}

		PatientResultChartDataVO patientResultChartDataVO = new PatientResultChartDataVO();
		patientResultChartDataVO.setHeaderVO(patientDetailDashboardHeaderVO);
		patientResultChartDataVO.setHealthSignalVO(healthSignalVO);
		patientResultChartDataVO.setBtResultList(btList);
		patientResultChartDataVO.setPrResultList(prList);
		patientResultChartDataVO.setSpo2ResultList(spo2List);
		patientResultChartDataVO.setRrResultList(rrList);
		patientResultChartDataVO.setSbpResultList(sbpList);
		patientResultChartDataVO.setDbpResultList(dbpList);

		return patientResultChartDataVO;
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
