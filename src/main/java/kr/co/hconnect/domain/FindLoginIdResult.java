package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 아이디 검색 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class FindLoginIdResult extends BaseResponse {

    private static final long serialVersionUID = 5817875168013867531L;

    /**
     * 아이디
     */
    private String loginId;
}
