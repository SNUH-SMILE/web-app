package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 로그인 성공 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginSuccessInfo extends BaseResponse {

    private static final long serialVersionUID = 698063311057457836L;

    /**
     * 토큰
     */
    private String token;

}
