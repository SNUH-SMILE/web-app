package kr.co.hconnect.jwt;

import kr.co.hconnect.common.TokenStatus;
import kr.co.hconnect.common.TokenType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 토큰 상세 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TokenDetailInfo implements Serializable {

    private static final long serialVersionUID = 2730027757782228759L;

    /**
     * 토큰 상태 정보
     */
    private TokenStatus tokenStatus;
    /**
     * 토큰 발급 정보
     */
    private TokenType tokenType;
    /**
     * tokenType
     *  WEB-userId
     *  APP-loginId
     */
    private String id;
    /**
     * tokenType
     *  WEB-userNm
     *  APP-patientNm
     */
    private String name;
}
