package kr.co.hconnect.jwt;

import io.jsonwebtoken.*;
import kr.co.hconnect.common.TokenStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * jwt Token 관리
 */
public class TokenProvider {

    private final Logger LOGGER = LoggerFactory.getLogger(TokenProvider.class);

    private final String secret = "Smart-Monitoring-solution-for-Infectious-disease-management-through-Lifestyle-Evaluation";
    private final SignatureAlgorithm keyAlg = SignatureAlgorithm.HS256;

    private final byte[] keyBytes = DatatypeConverter.parseBase64Binary(secret);
    private final Key key = new SecretKeySpec(keyBytes, keyAlg.getJcaName());

    /**
     * 토큰 만료시간 - milliseconds
     */
    private Integer validity;

    /**
     * 토큰 만료시간 Setter
     * @param validity 토큰 만료시간(초단위)
     */
    public void setValidity(Integer validity) {
        this.validity = validity * 1000;
    }

    /**
     * 토큰 생성
     *
     * @return 토큰 정보
     */
    public String createToken() {
        Date now = new Date();
        // 토큰 만료시간
        Date validityInterval = new Date(now.getTime() + this.validity);

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)   // 헤더 타입 지정
            .setIssuer("HealthConnect")                     // 발급자 정보
            .setIssuedAt(now)                               // 발급시간
            .setExpiration(validityInterval)                // 만료시간
            .signWith(key, keyAlg)                          // 키정보 및 해싱 알고리즘 정보
            .compact();
    }

    /**
     * 토큰 유효성 확인
     *
     * @param token 토큰 문자열
     * @return TOKEN_STATUS - 토큰상태
     */
    public TokenStatus validateToken(String token) {
        TokenStatus tokenStatus = TokenStatus.OK;

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            LOGGER.info("만료된 JWT 토큰입니다.");
            tokenStatus = TokenStatus.EXPIRED;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            LOGGER.info("잘못된 JWT 서명입니다.");
            tokenStatus = TokenStatus.ILLEGAL;
        } catch (UnsupportedJwtException e) {
            LOGGER.info("지원되지 않는 JWT 토큰입니다.");
            tokenStatus = TokenStatus.ILLEGAL;
        } catch (IllegalArgumentException e) {
            LOGGER.info("JWT 토큰이 잘못되었습니다.");
            tokenStatus = TokenStatus.ILLEGAL;
        }

        return tokenStatus;
    }

}


