package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 체온 구성정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BtResult extends BaseResult implements ResultValue {

    private static final long serialVersionUID = -5344118631588677759L;

    /**
     * 체온 측정결과
     */
    @NotNull(message = "{validation.null.result}")
    @Size(max = 10, message = "{validation.size.btResult}")
    @JsonProperty("bt")
    private String result;

}
