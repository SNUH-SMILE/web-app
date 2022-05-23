package kr.co.hconnect.exception;

/**
 * 사용자 정보 미존재 Exception
 */
public class NotFoundUserInfoException extends RuntimeException {

    private static final long serialVersionUID = 2009383189724700722L;

    /**
     * 오류 내역
     */
    private final String errorMessage;

    public NotFoundUserInfoException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
