package kr.co.hconnect.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * 인증체크 인터셉터
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    /**
     * 인증체크 제외 URL 목록
     */
    private final Set<String> permitUrls = new HashSet<>();

    /**
     * 인증체크 제외 URL 목록 Getter
     * @return 인증체크 제외 URL 목록
     */
    public Set<String> getPermitUrls() {
        return permitUrls;
    }

    /**
     * 인증체크 제외 URL 목록 Setter
     * @param permitUrls 인증체크 제외 URL 목록
     */
    public void setPermitUrls(String... permitUrls) {
        this.permitUrls.clear();
        if (!ObjectUtils.isEmpty(permitUrls)) {
            for (String permitUrl : permitUrls) {
                if (StringUtils.isEmpty(permitUrl)) continue;
                this.permitUrls.add(permitUrl);
            }
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 인증체크 제외 URL인 경우
        String uri = request.getRequestURI();
        LOGGER.info("Request URI: {}", uri);
        if (this.permitUrls.contains(uri))
            return true;

        // REST API 대응 URL은 인증체크에서 제외
        if (uri.startsWith("/api/"))
            return true;

        // 로그인여부 확인. 로그인 사용자가 아닌 경우 로그인 페이지 표시
        if (RequestContextHolder.getRequestAttributes().getAttribute("sessionVO", RequestAttributes.SCOPE_SESSION) == null) {
            response.sendRedirect("/login/login.do");
            return false;
        }
        return true;
    }

}
