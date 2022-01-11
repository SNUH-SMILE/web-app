package kr.co.hconnect.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.domain.BaseResponse;
import kr.co.hconnect.domain.LoginSuccessInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * REST API 접근 유효성 확인
 */
public class JwtFilter implements Filter {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

    public final String AUTHORIZATION_HEADER = "Authorization";

    private TokenProvider tokenProvider;

    private ObjectMapper mapper;

    private List<String> passUrl = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        tokenProvider = new TokenProvider();
        mapper = new ObjectMapper();

        // 웹 페이지 contextPath
        String contextPath = filterConfig.getServletContext().getContextPath();
        passUrl = new ArrayList<>();

        // web.xml 설정했던 init-param 설정-토큰정보가 필요 없는 REST API URL
        String initParameter = filterConfig.getInitParameter("passUrl");
        for (String ignoredPath : initParameter.split(",")) {
            passUrl.add(contextPath + ignoredPath);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String requestURI = httpServletRequest.getRequestURI();

        // passUrl 확인
        if (!passUrl.contains(requestURI)) {
            String jwt = resolveToken(httpServletRequest);

            BaseResponse baseResponse = null;   // TODO::토큰 상태 메시지 프로퍼티 적용
            if (StringUtils.hasText(jwt)) {

                TokenProvider.TOKEN_STATUS tokenStatus = tokenProvider.validateToken(jwt);

                if (tokenStatus == TokenProvider.TOKEN_STATUS.EXPIRED) {
                    baseResponse = getBaseResponseByTokenInfo(ApiResponseCode.EXPIRED_TOKEN
                        , "토큰이 만료되었습니다"
                        , true);
                } else if (tokenStatus == TokenProvider.TOKEN_STATUS.ILLEGAL) {
                    baseResponse = getBaseResponseByTokenInfo(ApiResponseCode.ILLEGAL_TOKEN
                        , "토큰정보가 유효하지 않습니다"
                        , false);
                }

            } else {
                LOGGER.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);

                baseResponse = getBaseResponseByTokenInfo(ApiResponseCode.INVALID_TOKEN
                    , "토큰정보가 존재하지 않습니다"
                    , false);
            }

            if (baseResponse != null) {
                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

                mapper.writeValue(httpServletResponse.getWriter(), baseResponse);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {

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
        , boolean isNewToken) {
        LoginSuccessInfo token = new LoginSuccessInfo();
        token.setCode(apiResponseCode.getCode());
        token.setMessage(message);

        if (isNewToken) {
            token.setToken(tokenProvider.createToken());
        }

        return token;
    }

    /**
     * 토큰정보 추출
     * 
     * @param request HttpServletRequest
     * @return 토큰정보
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
