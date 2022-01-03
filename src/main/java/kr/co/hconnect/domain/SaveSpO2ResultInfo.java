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
 * 산소포화도 측정결과 저장 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveSpO2ResultInfo implements Serializable, SaveResult {

    private static final long serialVersionUID = -7475744117489798364L;

    /**
     * 아이디
     */
    @NotNull(message = "{validation.null.loginId}")
    private String loginId;
    /**
     * 산소포화도 측정 결과
     */
    @JsonProperty("spO2List")
    @NotNull(message = "{validation.null.result}")
    @Valid
    private List<SpO2Result> results;

    /**
     * 산소포화도 측정 결과
     */
    public List<ResultValue> getResultList() {
        return new ArrayList<>(results);
    }
}
