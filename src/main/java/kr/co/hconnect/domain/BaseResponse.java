package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 요청결과 반환 기본 구성 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 6592236361981006525L;

    /**
     * 결과 코드
     */
    private String code;
    /**
     * 결과 메시지
     */
    private String message;
}
