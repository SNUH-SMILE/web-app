package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 걸음수 구성정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StepCountResult extends BaseResult {

    private static final long serialVersionUID = -7647935258239993387L;

    /**
     * 걸음수 측정결과
     */
    @NotNull
    @JsonProperty("stepCount")
    private String resultStepCount;
    /**
     * 거리 측정결과
     */
    @NotNull
    @JsonProperty("distance")
    private String resultDistance;
}
