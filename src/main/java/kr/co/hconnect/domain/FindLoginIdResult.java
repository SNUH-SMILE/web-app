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

    /**
     * 아이디
     */
    private String loginId;
}
