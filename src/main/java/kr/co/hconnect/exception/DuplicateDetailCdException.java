package kr.co.hconnect.exception;

/**
 * 공통코드상세 세부코드 중복 시 발생
 */
public class DuplicateDetailCdException extends RuntimeException {

    private static final long serialVersionUID = 2111477326290551044L;

    /**
     * 에러 내역
     */
    private final String message;

    public DuplicateDetailCdException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
