package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;

/**
 * 본인인증 완료 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class IdentityResult extends BaseResponse {

    private static final long serialVersionUID = -923728304689769850L;

    /**
     * 격리/입소 구분 - CD004
     * (0:대상자 아님, 1:자가격리 대상자, 2:생활치료센터 입소 대상자)
     */
    @Pattern(regexp = "^[012]$", message = "{validation.patternMismatch.sleepType}")
    private String quarantineDiv;
    /**
     * 회원가업여부
     */
    @Pattern(regexp = "^[YN]$", message = "{validation.patternMismatch.existYn}")
    private String registerYn;

}
