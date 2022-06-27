package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 환자 Vital 차트 표현용 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientVitalChartVO implements Serializable {

    private static final long serialVersionUID = 1569116946674603486L;

    /**
     * 차트 X축 데이터-측정일시
     */
    private Timestamp x;
    /**
     * 차트 Y축 데이터-Vital 측정 결과
     */
    private String y;
}
