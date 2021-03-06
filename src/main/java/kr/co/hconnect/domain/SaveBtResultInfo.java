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
 * 체온 측정 결과 저장 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveBtResultInfo implements Serializable, SaveResult {

    private static final long serialVersionUID = -4753288242715285488L;

    /**
     * 아이디
     */
    @NotNull(message = "{validation.null.loginId}")
    private String loginId;
    /**
     * 체온 측정 결과
     */
    @JsonProperty("btList")
    @NotNull(message = "{validation.null.result}")
    @Valid
    private List<BtResult> results;

    /**
     * 체온 측정 결과
     */
    public List<ResultValue> getResultList() {
        return new ArrayList<>(results);
    }
}
