package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.PatientStatusDashboardDetailSearchVO;
import kr.co.hconnect.vo.PatientStatusDashboardDetailVO;
import kr.co.hconnect.vo.PatientStatusDashboardHeaderVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 환자 대쉬보드 Dao
 */
@Repository
public class PatientDashboardDao extends EgovAbstractMapper {

    /**
     * 환자 현황 대시보드 환자정보 리스트 조회
     *
     * @param vo 환자 현황 대시보드 환자정보 조회조건
     * @return List&lt;PatientStatusDashboardDetailVO&gt; 환자 현황 대시보드 환자정보 리스트
     */
    public List<PatientStatusDashboardDetailVO> selectPatientStatusDashboardDetailList(PatientStatusDashboardDetailSearchVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.selectPatientStatusDashboardDetail", vo);
    }

    /**
     * 환자 현황 대시보드 상단 정보 조회
     *
     * @param vo 환자 현황 대시보드 환자정보 조회조건
     * @return PatientStatusDashboardHeaderVO 환자 현황 대시보드 상단 정보
     */
    public PatientStatusDashboardHeaderVO selectPatientStatusDashboardHeader(PatientStatusDashboardDetailSearchVO vo) {
        return selectOne("kr.co.hconnect.sqlmapper.selectPatientStatusDashboardHeader", vo);
    }
	
}
