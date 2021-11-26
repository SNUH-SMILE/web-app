package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 요청결과 반환 기본 구성 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BaseResponse {

    /**
     * 결과 코드
     */
    private String code;
    /**
     * 결과 메시지
     */
    private String message;
}
