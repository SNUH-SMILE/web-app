package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 환자 차트용 Vital 조회조건 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientVitalChartDataSearchVO implements Serializable {

    private static final long serialVersionUID = -3058944571562362583L;

    /**
     * 격리/입소내역ID
     */
    @NotBlank(message = "{validation.null.admissionId}")
    private String admissionId;
    /**
     * 조회일자
     */
    @NotNull(message = "{validation.null.searchDt}")
    private Date searchDt;
    /**
     * 조회시간
     */
    private String time;
}
