package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 산소포화도 구성정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SpO2Result extends BaseResult {

    private static final long serialVersionUID = 704469605148079253L;

    /**
     * 산소포화도 측정결과
     */
    @NotNull
    @JsonProperty("spO2")
    private String result;
    
}
