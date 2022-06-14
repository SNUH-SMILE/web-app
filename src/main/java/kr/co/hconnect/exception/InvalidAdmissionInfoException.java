package kr.co.hconnect.exception;

/**
 * 격리/입소 저장 정보에 문제가 있을 경우 발생
 */
public class InvalidAdmissionInfoException extends RuntimeException {

    private static final long serialVersionUID = -425772226014169337L;

    /**
     * 에러 내역
     */
    private final String message;

    public InvalidAdmissionInfoException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
