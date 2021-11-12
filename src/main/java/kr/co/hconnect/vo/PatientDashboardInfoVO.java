package kr.co.hconnect.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 환자상세 대쉬보드 전체 페이지 데이터
 * 환자상세 대쉬보드 초기 로딩 시 담을 전체 페이지 정보 VO
 */
@Data
@NoArgsConstructor
public class PatientDashboardInfoVO implements Serializable {

    private static final long serialVersionUID = 3346801837051902516L;

    /**
     * 환자상세VO 
     */
    private PatientDetailDashboardPatientInfoVO patientDashboardDetails;
    /**
     * 환자상세 대시보드 측정결고 목록VO 리스트
     */
    private List<PatientDetailDashboardResultVO> patientDashboardResultVOs;
}
