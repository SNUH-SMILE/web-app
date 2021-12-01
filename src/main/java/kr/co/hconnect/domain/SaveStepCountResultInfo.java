package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 걸음수 측정결과 저장 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveStepCountResultInfo implements Serializable {

    private static final long serialVersionUID = -3386649844875205459L;

    /**
     * 아이디
     */
    private String loginId;
    /**
     * 걸음수 측정 결과
     */
    @JsonProperty("stepCountList")
    @NotNull(message = "측정결과 누락")
    private List<StepCountResult> results;
}
