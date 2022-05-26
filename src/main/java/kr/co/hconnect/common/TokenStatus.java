package kr.co.hconnect.common;

/**
 * 토큰 상태 정보
 */
public enum TokenStatus {
    /**
     * 정상
     */
    OK,
    /**
     * 만료
     */
    EXPIRED,
    /**
     * 부적합
     */
    ILLEGAL,
    /**
     * 유효하지 않은 토큰
     */
    INVALID
}
