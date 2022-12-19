package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * 환자 마지막 신체계측, 수면, 예측결과 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientDetailDashboardRecentResultVO implements Serializable {

    private static final long serialVersionUID = 6170803566328994389L;

    /**
     * 격리/입소내역ID
     */
    private String admissionId;
    /**
     * 혈압_SBP
     */
    private String sbpResult;
    /**
     * 혈압_DBP
     */
    private String dbpResult;
    /**
     * 혈압_SBP 이상 여부
     * H: 기준치 이상, L: 기준치 이하
     */
    private String sbpRiskGb;
    /**
     * 혈압_DBP 이상 여부
     * H: 기준치 이상, L: 기준치 이하
     */
    private String dbpRiskGb;
    /**
     * 혈압 측정일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp bpResultDt;
    /**
     * 혈압 단위
     */
    private String bpUnit;

    /**
     * 심박수
     */
    private String prResult;
    /**
     * 심박수 이상 여부
     * H: 기준치 이상, L: 기준치 이하
     */
    private String prRiskGb;
    /**
     * 심박수 측정일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp prResultDt;
    /**
     * 심박수 단위
     */
    private String prUnit;

    /**
     * 체온
     */
    private String btResult;
    /**
     * 체온 이상 여부
     * H: 기준치 이상, L: 기준치 이하
     */
    private String btRiskGb;
    /**
     * 체온 측정일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp btResultDt;
    /**
     * 체온 단위
     */
    private String btUnit;

    /**
     * 걸음수
     */
    private String st1Result;
    /**
     * 거리
     */
    private String st2Result;
    /**
     * 걸음수 이상 여부
     * H: 기준치 이상, L: 기준치 이하
     */
    private String st1RiskGb;
    /**
     * 거리 이상 여부
     * H: 기준치 이상, L: 기준치 이하
     */
    private String st2RiskGb;
    /**
     * 걸음수 측정일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp stResultDt;
    /**
     * 걸음수 단위
     */
    private String stUnit;

    /**
     * 호흡
     */
    private String rrResult;
    /**
     * 호흡 이상 여부
     * H: 기준치 이상, L: 기준치 이하
     */
    private String rrRiskGb;
    /**
     * 호흡 측정일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp rrResultDt;
    /**
     * 호흡 단위
     */
    private String rrUnit;

    /**
     * 산소포화도
     */
    private String spResult;
    /**
     * 산소포화도 이상 여부
     * H: 기준치 이상, L: 기준치 이하
     */
    private String spRiskGb;
    /**
     * 산소포화도 측정일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp spResultDt;
    /**
     * 산소포화도 단위
     */
    private String spUnit;

    /**
     * 총 수면시간
     */
    private String sleepResult;
    /**
     * 수면 측정일-Date
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate sleepDt;
    /**
     * 수면 단위
     */
    private String sleepUnit;
    /**
     * 호흡기계 위험도
     */
    private String respiratoryRiskResult;
    /**
     * 호흡기계 위험도 측정일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp respiratoryRiskResultDt;
    /**
     * 호흡기계 위험도 단위
     */
    private String respiratoryRiskUnit;


    /**
     * 호흡기계 스코어 이름
     */
    private String riskScoreNm;
    /**
     * 호흡기계 스코어 위험
     */
    private String riskScoreResult;

    /**
     * 호흡기계 스코어 위험도 측정일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp riskScoreDt;

    /**
     * 호흡기계 체온 이름
     */
    private String riskTemperNm;
    /**
     * 호흡기계 체온 위험
     */
    private String riskTemperResult;

    /**
     * 호흡기계 체온 위험도 측정일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp riskTemperDt;


    /**
     * 정신건강 우울 이름
     */
    private String mentalRiskNm;

    /**
     * 정신건강 우울 위험
     */
    private String mentalRiskResult;

    /**
     * 정신건강 우울 위험도 측정일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp mentalRiskResultDt;
}
