package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 환자 Vital 측정결과 차트 표현 데이터 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientResultChartDataVO implements Serializable {

    private static final long serialVersionUID = -5893365910578493728L;

    /**
     * 환자 상태 알림
     */
    private PatientHealthSignalVO healthSignalVO;
    /**
     * 환자 상세 대시보드 상단 헤더 정보
     */
    private PatientDetailDashboardHeaderVO headerVO;

    /**
     * 체온 리스트
     */
    private List<PatientVitalChartVO> btResultList;
    /**
     * 심박수 리스트
     */
    private List<PatientVitalChartVO> PrResultList;
    /**
     * 산소포화도 리스트
     */
    private List<PatientVitalChartVO> spo2ResultList;
    /**
     * 호흡수 리스트
     */
    private List<PatientVitalChartVO> rrResultList;
    /**
     * 협압 리스트-SBP
     */
    private List<PatientVitalChartVO> sbpResultList;
    /**
     * 협압 리스트-DBP
     */
    private List<PatientVitalChartVO> dbpResultList;
}
