package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 혈압 구성정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BpResult extends BaseResult implements ResultValue {

    private static final long serialVersionUID = 3856975045053471529L;

    /**
     * 최고혈압 측정결과
     */
    @NotNull(message = "{validation.null.result}")
    @Size(max = 10, message = "{validation.size.sbpResult}")
    @JsonProperty("sbp")
    private String result;
    /**
     * 최저혈압 측정결과
     */
    @NotNull(message = "{validation.null.result}")
    @Size(max = 10, message = "{validation.size.dbpResult}")
    @JsonProperty("dbp")
    private String result2;

}
