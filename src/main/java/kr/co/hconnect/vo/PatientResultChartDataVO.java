package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
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
     * 격리/입소내역ID
     */
    private String admissionId;
    /**
     * 측정결과 조회일
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date searchDt;
    /**
     * 측정결과 min Data - Chart 속성으로 사용
     */
    private Date min;
    /**
     * 측정결과 max Data - Chart 속성으로 사용
     */
    private Date max;
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
