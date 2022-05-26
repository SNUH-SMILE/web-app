package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 토큰 상태 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TokenStatusInfo implements Serializable {

    private static final long serialVersionUID = 6533754731403947648L;

    /**
     * Token
     */
    @NotNull(message = "{validation.null.token}")
    private String token;
    /**
     * 토큰상태
     * 00:정상
     * 80:만료
     * 81:폐기
     * 82:부적합
     */
    private String tokenStatus;
    /**
     * 로그인 유지 여부
     */
    private String rememberYn;
}
