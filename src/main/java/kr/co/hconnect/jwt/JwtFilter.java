package kr.co.hconnect.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.TokenStatus;
import kr.co.hconnect.common.TokenType;
import kr.co.hconnect.domain.LoginSuccessInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class JwtFilter extends OncePerRequestFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_TYPE = "Bearer";

    private final TokenProvider tokenProvider;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<String, Set<String>> passUrls = new HashMap<>();

    private final MessageSource messageSource;

    /**
     * token 유효성 검사 제외 URL 목록 Setter
     * @param passUrls token 유효성 검사 제외 URL 목록
     */
    public void setPassUrls(Map<String, Set<String>> passUrls) {
        this.passUrls.clear();
        if (!ObjectUtils.isEmpty(passUrls)) {
            for (Map.Entry<String, Set<String>> passUrl : passUrls.entrySet()) {
                if (!StringUtils.isEmpty(passUrl.getKey()) && !this.passUrls.containsKey(passUrl.getKey())) {
                    this.passUrls.put(passUrl.getKey(), passUrl.getValue());
                }
            }
        }
    }

    /**
     * 생성자
     *
     * @param tokenProvider jwt Token 관리
     * @param messageSource MessageSource
     */
    public JwtFilter(TokenProvider tokenProvider, MessageSource messageSource) {
        this.tokenProvider = tokenProvider;
        this.messageSource = messageSource;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();

        String jwt = resolveToken(request);
        TokenDetailInfo tokenDetailInfo = null;
        TokenStatus tokenStatus;

        if (StringUtils.hasText(jwt)) {
            tokenDetailInfo = tokenProvider.validateToken(jwt);
            tokenStatus = tokenDetailInfo.getTokenStatus();
        } else {
            tokenStatus = TokenStatus.INVALID;
        }

        // passUrl 확인
        if (!(this.passUrls.containsKey(requestURI) && this.passUrls.get(requestURI).contains(requestMethod))
                && !requestMethod.equals("OPTIONS")) {
            if (tokenStatus != TokenStatus.OK) {
                response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

                LoginSuccessInfo loginSuccessInfo = null;

                switch (tokenStatus) {
                    case EXPIRED:
                        loginSuccessInfo = getBaseResponseByTokenInfo(ApiResponseCode.EXPIRED_TOKEN
                                , messageSource.getMessage("message.jwtToken.expired", null, Locale.getDefault())
                                , true, jwt);
                        break;
                    case ILLEGAL:
                        loginSuccessInfo = getBaseResponseByTokenInfo(ApiResponseCode.ILLEGAL_TOKEN
                                , messageSource.getMessage("message.jwtToken.illegal", null, Locale.getDefault())
                                , false, "");
                        break;
                    case INVALID:
                        LOGGER.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
                        loginSuccessInfo = getBaseResponseByTokenInfo(ApiResponseCode.INVALID_TOKEN
                                , messageSource.getMessage("message.jwtToken.invalid", null, Locale.getDefault())
                                , false, "");
                        break;
                }

                objectMapper.writeValue(response.getWriter(), loginSuccessInfo);
                return;
            }
        }

        request.setAttribute("tokenDetailInfo", tokenDetailInfo);

        filterChain.doFilter(request, response);
    }

    /**
     * Token 상태에 따른 반환정보 구성
     *
     * @param apiResponseCode API 결과코드 정보
     * @param message Message
     * @param isNewToken 신규 토큰 생성여부
     * @return Token
     */
    private LoginSuccessInfo getBaseResponseByTokenInfo(ApiResponseCode apiResponseCode, String message
            , boolean isNewToken, String expiredToken) {
        LoginSuccessInfo loginSuccessInfo = new LoginSuccessInfo();
        loginSuccessInfo.setCode(apiResponseCode.getCode());
        loginSuccessInfo.setMessage(message);

        if (isNewToken && !StringUtils.isEmpty(expiredToken)) {
            loginSuccessInfo.setToken(tokenProvider.createToken(expiredToken, TokenType.APP));
        }

        return loginSuccessInfo;
    }

    /**
     * 토큰정보 추출
     *
     * @param request HttpServletRequest
     * @return 토큰정보
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(7);
        }

        return null;
    }

}
