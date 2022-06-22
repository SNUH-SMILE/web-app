package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.PatientDetailDashboardHeaderVO;
import org.springframework.stereotype.Repository;

/**
 * 환자 상세 대쉬보드 Dao
 */
@Repository
public class PatientDetailDashboardDao extends EgovAbstractMapper {

    /**
     * 환자 상세 대시보드 상단 정보 조회
     *
     * @param admissionId 격리/입소내역ID
     * @return PatientDetailDashboardHeaderVO 환자 상세 대시보드 상단 정보
     */
    public PatientDetailDashboardHeaderVO selectPatientDetailDashboardHeader(String admissionId) {
        return selectOne("kr.co.hconnect.sqlmapper.selectPatientDetailDashboardHeader", admissionId);
    }



//	/**
//	 * 환자 상세 대쉬보드 입소내역 조회
//	 * @param admissionId - 입소ID
//	 * @return PatientDetailDashboardPatientInfoVO - 환자상세대시보드의 환자정보 (최신 측정데이터 + 환자정보)
//	 */
//	public PatientDetailDashboardPatientInfoVO selectPatientDetailDashboardPatientInfo(String admissionId) {
//		return selectOne("kr.co.hconnect.sqlmapper.selectPatientDetailDashboardPatientInfo", admissionId);
//	}
//
//	/**
//	 * 환자 상세 대쉬보드 입소내역 조회
//	 * @param admissionId - 입소ID
//	 * @return PatientRecentVitalVO - V/S 정보 VO
//	 */
//	public PatientRecentVitalVO selectRecentVitalResults(String admissionId){
//		return selectOne("kr.co.hconnect.sqlmapper.selectRecentVitalResults", admissionId);
//	}
//
//	/**
//	 * 환자 상세 대쉬보드 측졍결과 리스트 조회
//	 * @param patientDetailDashboardResultSearchVO - 측정결과 조회 조건
//	 * @return List<PatientDetailDashboardResultVO> - 환자 상세 대쉬보드 측정결과 리스트
//	 */
//	public List<PatientDetailDashboardResultVO> selectPatientDetailDashboardResultList(
//			PatientDetailDashboardResultSearchVO patientDetailDashboardResultSearchVO) {
//		return selectList("kr.co.hconnect.sqlmapper.selectPatientDetailDashboardResultList", patientDetailDashboardResultSearchVO);
//	}
//
//	/**
//	 * 센터 측정 ITEM 항목 VO 불러오기
//	 * @param itemId - 측정 ITEM ID
//	 * @return  List<ItemVO> - ITEM VO LIST
//	 */
//	public ItemVO selectCenterItemList(String itemId){
//		return selectOne("kr.co.hconnect.sqlmapper.selectItemByItemId", itemId);
//	}
}
