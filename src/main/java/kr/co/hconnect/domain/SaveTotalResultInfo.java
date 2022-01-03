package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 전체 측정결과 저장 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveTotalResultInfo implements Serializable {

    private static final long serialVersionUID = 7934662929235375786L;

    /**
     * 아이디
     */
    @NotNull(message = "{validation.null.loginId}")
    private String loginId;

    /**
     * 체온 측정 결과
     */
    @JsonProperty("btList")
    @Valid
    private List<BtResult> btResults;

    /**
     * 혈압 측졍 결과
     */
    @JsonProperty("bpList")
    @Valid
    private List<BpResult> bpResults;

    /**
     * 심박수 측정결과
     */
    @JsonProperty("hrList")
    @Valid
    private List<HrResult> hrResults;

    /**
     * 산소포화도 측정 결과
     */
    @JsonProperty("spO2List")
    @Valid
    private List<SpO2Result> spO2Results;

    /**
     * 걸음수 측정 결과
     */
    @JsonProperty("stepCountList")
    @Valid
    private List<StepCountResult> stepCountResults;

    /**
     * 수면 측정 결과
     */
    @JsonProperty("sleepTimeList")
    @Valid
    private List<SaveSleepTimeResult> sleepTimeResults;
}
