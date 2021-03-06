package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 걸음수 측정결과 저장 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveStepCountResultInfo implements Serializable, SaveResult {

    private static final long serialVersionUID = -3386649844875205459L;

    /**
     * 아이디
     */
    @NotNull(message = "{validation.null.loginId}")
    private String loginId;
    /**
     * 걸음수 측정 결과
     */
    @JsonProperty("stepCountList")
    @NotNull(message = "{validation.null.result}")
    @Valid
    private List<StepCountResult> results;

    /**
     * 걸은수 측정 결과
     */
    public List<ResultValue> getResultList() {
        return new ArrayList<>(results);
    }
}
