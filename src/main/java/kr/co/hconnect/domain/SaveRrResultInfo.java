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
 * 호흡 측정결과 저장 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveRrResultInfo implements Serializable, SaveResult {

    private static final long serialVersionUID = -5586904948263983412L;

    /**
     * 아이디
     */
    @NotNull(message = "{validation.null.loginId}")
    public String loginId;
    /**
     * 호흡 측정 결과
     */
    @JsonProperty("rrList")
    @NotNull(message = "{validation.null.result}")
    @Valid
    public List<RrResult> results;

    /**
     * 호흡 측정 결과
     */
    public List<ResultValue> getResultList() {
        return new ArrayList<>(results);
    }

}
