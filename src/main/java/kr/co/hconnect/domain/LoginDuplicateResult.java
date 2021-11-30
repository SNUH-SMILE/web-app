package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 로그인ID 중복 확인 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginDuplicateResult extends BaseResponse {

    private static final long serialVersionUID = -1549074312490147220L;

    /**
     * 중복여부 (Y : 중복, N : 중복아님)
     */
    private String dupYn;
}
