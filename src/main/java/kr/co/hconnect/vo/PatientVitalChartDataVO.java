package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 환자 Vital 조회결과 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientVitalChartDataVO implements Serializable {

    private static final long serialVersionUID = 7275263792144194648L;

    /**
     * 측정일시
     */
    private Timestamp resultDt;
    /**
     * 체온 결과
     */
    private String bt;
    /**
     * 심박수 결과
     */
    private String pr;
    /**
     * 산소포화도 결과
     */
    private String spo2;
    /**
     * 호흡수 결과
     */
    private String rr;
    /**
     * 혈압 결과-SBP
     */
    private String sbp;
    /**
     * 혈압 결과-DBP
     */
    private String dbp;
}
