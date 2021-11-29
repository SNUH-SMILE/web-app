package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 로그인 구성정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginInfo {

    /**
     * 아이디
     */
    @NotNull(message = "로그인 아이디가 누락되었습니다.")
    private String loginId;
    /**
     * 비밀번호
     */
    @NotNull(message = "패스워드가 누락되었습니다.")
    private String password;

}
