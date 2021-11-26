package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    private String loginId;
    /**
     * 비밀번호
     */
    private String password;

}
