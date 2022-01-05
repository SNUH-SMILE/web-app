package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 산소포화도 구성정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SpO2Result extends BaseResult implements ResultValue {

    private static final long serialVersionUID = 704469605148079253L;

    /**
     * 산소포화도 측정결과
     */
    @NotNull(message = "{validation.null.result}")
    @Size(max = 10, message = "{validation.size.spO2Result}")
    @JsonProperty("spO2")
    private String result;
    
}
