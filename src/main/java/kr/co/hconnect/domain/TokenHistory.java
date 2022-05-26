package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 토큰 발급 내역
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TokenHistory implements Serializable {

    private static final long serialVersionUID = -3854619170571454512L;

    /**
     * 토큰정보
     */
    private String token;
    /**
     * 토큰 발급 유형
     */
    private String tokenType;
    /**
     * 사용자ID or 환자 로그인ID
     */
    private String id;
    /**
     * 사용자 or 환자 이름
     */
    private String name;
    /**
     * 발급일시
     */
    private Timestamp issueDt;
    /**
     * 만료일시
     */
    private Timestamp expiryDt;
}
