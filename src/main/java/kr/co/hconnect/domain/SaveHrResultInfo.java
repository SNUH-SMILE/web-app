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
 * 심박수 측정결과 저장 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveHrResultInfo implements Serializable, SaveResult {

    private static final long serialVersionUID = 1263304633019181010L;

    /**
     * 아이디
     */
    @NotNull(message = "{validation.null.loginId}")
    private String loginId;
    /**
     * 심박수 측정결과
     */
    @JsonProperty("hrList")
    @NotNull(message = "{validation.null.result}")
    @Valid
    private List<HrResult> results;

    /**
     * 심박수 측정결과
     */
    public List<ResultValue> getResultList() {
        return new ArrayList<>(results);
    }
}
