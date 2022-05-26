package kr.co.hconnect.jwt;

import io.jsonwebtoken.*;
import kr.co.hconnect.common.TokenStatus;
import kr.co.hconnect.common.TokenType;
import kr.co.hconnect.domain.TokenHistory;
import kr.co.hconnect.service.TokenHistoryService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.sql.Timestamp;
import java.util.Date;

/**
 * jwt Token 관리
 */
public class TokenProvider {

    private final Logger LOGGER = LoggerFactory.getLogger(TokenProvider.class);

    /**
     * 서명 알고리즘
     */
    private final SignatureAlgorithm keyAlg = SignatureAlgorithm.HS256;

    /**
     * 인증키
     */
    private final Key key;

    /**
     * 토큰 만료시간 - milliseconds
     */
    private final Integer validity;

    /**
     * 토큰 폐기기간 - 일단위
     */
    private final Integer expiryDay;

    /**
     * 토큰 발급 이력 Service
     */
    @Autowired
    private TokenHistoryService tokenHistoryService;

    /**
     * 생성자
     *  @param secretKey 암호키
     * @param validity 토큰 만료시간(초단위)
     */
    public TokenProvider(String secretKey, Integer validity, Integer expiryDay) {
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(secretKey);
        this.key = new SecretKeySpec(keyBytes, keyAlg.getJcaName());
        this.validity = validity * 1000;
        this.expiryDay = expiryDay;
    }

    /**
     * 토큰 생성
     *
     * @param tokenType 토큰 발급 타입
     * @param id 사용자 or 로그인 ID
     * @param name 사용자 or 환자 이름
     * @return JWT Token
     */
    public String createToken(TokenType tokenType, String id, String name) {
        Date now = new Date();
        // 토큰 만료시간
        Date validityInterval = new Date(now.getTime() + this.validity);

        String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)   // 헤더 타입 지정
                .setIssuer("HealthConnect")                     // 발급자 정보
                .setIssuedAt(now)                               // 발급시간
                .setExpiration(validityInterval)                // 만료시간
                .signWith(key, keyAlg)                          // 키정보 및 해싱 알고리즘 정보
                .claim("tokenType", tokenType.getTokenType())// 토큰 발급 타입
                .claim("id", id)                             // 아이디
                .claim("name", name)                         // 이름
                .compact();

        // 토큰 이력 생성
        Date expiryDate = DateUtils.addDays(now, expiryDay);
        TokenHistory tokenHistory = new TokenHistory();
        tokenHistory.setToken(token);
        tokenHistory.setTokenType(tokenType.getTokenType());
        tokenHistory.setId(id);
        tokenHistory.setExpiryDt(new Timestamp(expiryDate.getTime()));

        tokenHistoryService.insertTokenHistory(tokenHistory);

        return token;
    }

    /**
     * 토큰 생성
     *
     * @param expiredToken 기존 토큰 정보
     * @param tokenType 토큰 발급 타입
     * @return JWT Token
     */
    public String createToken(String expiredToken, TokenType tokenType) {
        String token = "";

        TokenHistory tokenHistory = tokenHistoryService.selectTokenHistory(expiredToken);

        if (tokenHistory != null && tokenHistory.getTokenType().equals(tokenType.getTokenType())) {
            // 기존 토큰 삭제
            tokenHistoryService.deleteTokenHistory(expiredToken);
            // 신규 토큰 발급
            token = createToken(tokenType, tokenHistory.getId(), tokenHistory.getName());
        }

        return token;
    }

    /**
     * 토큰 유효성 확인
     *
     * @param token 토큰 문자열
     * @return TOKEN_STATUS - 토큰상태
     */
    public TokenDetailInfo validateToken(String token) {
        TokenDetailInfo tokenDetailInfo = new TokenDetailInfo();
        TokenStatus tokenStatus = TokenStatus.OK;

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            try {
                tokenDetailInfo.setTokenType(TokenType.valueOf(claimsJws.getBody().get("tokenType").toString()));
                tokenDetailInfo.setId(claimsJws.getBody().get("id").toString());
                tokenDetailInfo.setName(claimsJws.getBody().get("name").toString());
            } catch (Exception e) {
                LOGGER.info("토큰 정보 누락");
                tokenStatus = TokenStatus.INVALID;
            }
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

        tokenDetailInfo.setTokenStatus(tokenStatus);

        return tokenDetailInfo;
    }

}


