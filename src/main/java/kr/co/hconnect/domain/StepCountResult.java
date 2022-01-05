package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 걸음수 구성정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StepCountResult extends BaseResult implements ResultValue {

    private static final long serialVersionUID = -7647935258239993387L;

    /**
     * 걸음수 측정결과
     */
    @NotNull(message = "{validation.null.result}")
    @Size(max = 10, message = "{validation.size.stepCountResult}")
    @JsonProperty("stepCount")
    private String result;
    /**
     * 거리 측정결과
     */
    @NotNull(message = "{validation.null.result}")
    @Size(max = 10, message = "{validation.size.distanceResult}")
    @JsonProperty("distance")
    private String result2;
}
