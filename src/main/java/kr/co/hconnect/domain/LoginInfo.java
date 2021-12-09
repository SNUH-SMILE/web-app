package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 로그인 구성정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginInfo implements Serializable {

    private static final long serialVersionUID = 6132809804573792605L;

    /**
     * 아이디
     */
    @NotNull(message = "{validation.null.loginId}")
    private String loginId;
    /**
     * 비밀번호
     */
    @NotNull(message = "{validation.null.password}")
    private String password;

}
