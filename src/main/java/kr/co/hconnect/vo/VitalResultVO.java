package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Vital 측정결과
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class VitalResultVO implements Serializable {

    private static final long serialVersionUID = -387470501101880523L;

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
     * 심박수
     */
    private String prResult;
    /**
     * 심박수 이상 여부
     * H: 기준치 이상, L: 기준치 이하
     */
    private String prRiskGb;

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
     * 호흡
     */
    private String rrResult;
    /**
     * 호흡 이상 여부
     * H: 기준치 이상, L: 기준치 이하
     */
    private String rrRiskGb;

    /**
     * 산소포화도
     */
    private String spResult;
    /**
     * 산소포화도 이상 여부
     * H: 기준치 이상, L: 기준치 이하
     */
    private String spRiskGb;
}
