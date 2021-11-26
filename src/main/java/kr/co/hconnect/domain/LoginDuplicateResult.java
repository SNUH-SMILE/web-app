package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 아이디 중복 확인 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginDuplicateResult extends BaseResponse {

    /**
     * 중복여부 (Y : 중복, N : 중복아님)
     */
    private String dupYn;
}
