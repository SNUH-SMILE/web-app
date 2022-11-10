package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 호흡 구성정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RrResult extends BaseResult implements ResultValue {

    private static final long serialVersionUID = 597967509958591636L;

    /**
     * 호흡 측정결과
     */
    @NotNull(message = "{validation.null.result}")
    @Size(max = 10, message = "{validation.size.rrResult}")
    @JsonProperty("rr")
    private String result;
}
