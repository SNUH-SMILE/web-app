package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 격리상태 저장 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveQuarantineStatusInfo extends BaseResponse {

    private static final long serialVersionUID = -5426661290209696802L;

    /**
     * 로그인ID
     */
    @NotNull(message = "{validation.loginId.null}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String loginId;
    /**
     * 격리 상태 구분 (0 : 정상, 1 : 이탈)
     */
    @NotNull
    @Pattern(regexp = "^[01]$")
    private String quarantineStatusDiv;
}
