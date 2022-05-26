package kr.co.hconnect.common;

/**
 * 토큰 발급 타입
 */
public enum TokenType {

    /**
     * 보호관리 서비스 Web
     */
    WEB("WEB"),
    /**
     * 환자용 App
     */
    APP("APP");


    private final String tokenType;

    /**
     * 생성자
     * @param tokenType 토큰타입
     */
    TokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenType() {
        return tokenType;
    }

}
