package kr.co.hconnect.common;

import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 요청,응답 로깅 필터
 */
public class CommonRequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final String NEW_LINE = System.getProperty("line.separator");

    private static final int DEFAULT_MAX_PAYLOAD_LENGTH = 50;

    private boolean includeQueryString = false;

    private boolean includeClientInfo = false;

    private boolean includeHeaders = false;

    private boolean includePayload = false;

    private int maxPayloadLength = DEFAULT_MAX_PAYLOAD_LENGTH;

    /**
     * Set whether the query string should be included in the log message.
     * <p>Should be configured using an {@code <init-param>} for parameter name
     * "includeQueryString" in the filter definition in {@code web.xml}.
     */
    public void setIncludeQueryString(boolean includeQueryString) {
        this.includeQueryString = includeQueryString;
    }

    /**
     * Return whether the query string should be included in the log message.
     */
    public boolean isIncludeQueryString() {
        return this.includeQueryString;
    }

    /**
     * Set whether the client address and session id should be included in the
     * log message.
     * <p>Should be configured using an {@code <init-param>} for parameter name
     * "includeClientInfo" in the filter definition in {@code web.xml}.
     */
    public void setIncludeClientInfo(boolean includeClientInfo) {
        this.includeClientInfo = includeClientInfo;
    }

    /**
     * Return whether the client address and session id should be included in the
     * log message.
     */
    public boolean isIncludeClientInfo() {
        return this.includeClientInfo;
    }

    /**
     * Set whether the request headers should be included in the log message.
     * <p>Should be configured using an {@code <init-param>} for parameter name
     * "includeHeaders" in the filter definition in {@code web.xml}.
     * @since 4.3
     */
    public void setIncludeHeaders(boolean includeHeaders) {
        this.includeHeaders = includeHeaders;
    }

    /**
     * Return whether the request headers should be included in the log message.
     * @since 4.3
     */
    public boolean isIncludeHeaders() {
        return this.includeHeaders;
    }

    /**
     * Set whether the request payload (body) should be included in the log message.
     * <p>Should be configured using an {@code <init-param>} for parameter name
     * "includePayload" in the filter definition in {@code web.xml}.
     * @since 3.0
     */
    public void setIncludePayload(boolean includePayload) {
        this.includePayload = includePayload;
    }

    /**
     * Return whether the request payload (body) should be included in the log message.
     * @since 3.0
     */
    public boolean isIncludePayload() {
        return this.includePayload;
    }

    /**
     * Set the maximum length of the payload body to be included in the log message.
     * Default is 50 characters.
     * @since 3.0
     */
    public void setMaxPayloadLength(int maxPayloadLength) {
        Assert.isTrue(maxPayloadLength >= 0, "'maxPayloadLength' should be larger than or equal to 0");
        this.maxPayloadLength = maxPayloadLength;
    }

    /**
     * Return the maximum length of the payload body to be included in the log message.
     * @since 3.0
     */
    public int getMaxPayloadLength() {
        return this.maxPayloadLength;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        boolean isFirstRequest = !isAsyncDispatch(request);
        HttpServletRequest requestToUse = request;
        HttpServletResponse responseToUse = response;

        if (isFirstRequest) {
            if (!(request instanceof ContentCachingRequestWrapper)) {
                requestToUse = new ContentCachingRequestWrapper(request, getMaxPayloadLength());
            }
            if (!(response instanceof ContentCachingResponseWrapper)) {
                responseToUse = new ContentCachingResponseWrapper(response);
            }
        }

        try {
            filterChain.doFilter(requestToUse, responseToUse);
        }
        finally {
            if (!isAsyncStarted(requestToUse)) {
                createLog(requestToUse, responseToUse);
            }
        }
    }

    /**
     * 요청, 응답 정보 로깅
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    private void createLog(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder msg = new StringBuilder();

        // Request 정보
        msg.append(NEW_LINE)
            .append("Request [")
            .append(NEW_LINE)
            .append("  uri=").append(request.getRequestURI());

        if (isIncludeQueryString()) {
            String queryString = request.getQueryString();
            if (queryString != null) {
                msg.append(NEW_LINE)
                    .append("  ?").append(queryString);
            }
        }

        if (isIncludeClientInfo()) {
            String client = request.getRemoteAddr();
            if (StringUtils.hasLength(client)) {
                msg.append(NEW_LINE)
                    .append("  client=").append(client);
            }
            HttpSession session = request.getSession(false);
            if (session != null) {
                msg.append(NEW_LINE)
                    .append("  session=").append(session.getId());
            }
            String user = request.getRemoteUser();
            if (user != null) {
                msg.append(NEW_LINE)
                    .append("  user=").append(user);
            }
        }

        if (isIncludeHeaders()) {
            msg.append(NEW_LINE)
                .append("  headers=").append(new ServletServerHttpRequest(request).getHeaders());
        }

        if (isIncludePayload()) {
            ContentCachingRequestWrapper wrapper =
                WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
            if (wrapper != null) {
                byte[] buf = wrapper.getContentAsByteArray();
                if (buf.length > 0) {
                    int length = Math.min(buf.length, getMaxPayloadLength());
                    String payload;
                    try {
                        payload = new String(buf, 0, length, wrapper.getCharacterEncoding());
                    }
                    catch (UnsupportedEncodingException ex) {
                        payload = "[unknown]";
                    }
                    msg.append(NEW_LINE)
                        .append("  payload=").append(payload);
                }
            }
        }

        msg.append(NEW_LINE)
            .append("]");


        // Response 정보
        ContentCachingResponseWrapper responseWrapper =
            WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (responseWrapper != null) {

            byte[] buf = responseWrapper.getContentAsByteArray();
            if (buf.length > 0) {
                msg.append(NEW_LINE)
                    .append("Response [ ");

                int length = Math.min(buf.length, getMaxPayloadLength());
                String payload;

                try {
                    payload = new String(buf, 0, length, responseWrapper.getCharacterEncoding());
                }
                catch (UnsupportedEncodingException ex) {
                    payload = "[unknown]";
                }
                msg.append(NEW_LINE)
                    .append("  payload=").append(payload);

                msg.append(NEW_LINE);
                msg.append("]");
            }

            responseWrapper.copyBodyToResponse();
        }

        logger.debug(msg.toString());
    }

}
