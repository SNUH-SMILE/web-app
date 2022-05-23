package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 사용자 로그인 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserLoginInfo implements Serializable {

    private static final long serialVersionUID = -1773620410222057699L;

    /**
     * 아이디
     */
    @NotNull(message = "{validation.null.loginId}")
    private String loginId;

    /**
     * 비밀번호
     */
    @NotNull(message = "{validation.null.password}")
    @Size(max = 20, message = "{validation.size.password}")
    private String password;

    /**
     * 로그인 유지 여부
     */
    @Pattern(regexp = "^[YN]$")
    private String rememberYn;
}
