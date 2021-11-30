package kr.co.hconnect.domain;

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
public class SaveQuarantineStatusInfo {

    /**
     * 로그인ID
     */
    @NotNull
    private String loginId;
    /**
     * 격리 상태 구분 (0 : 정상, 1 : 이탈)
     */
    @NotNull
    @Pattern(regexp = "^[01]$")
    private String quarantineStatusDiv;
}
