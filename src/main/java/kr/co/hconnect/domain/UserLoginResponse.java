package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 로그인 응답 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserLoginResponse extends BaseResponse {

    private static final long serialVersionUID = -2461380484928982120L;

    /**
     * AccessToken
     */
    private String token;
}
