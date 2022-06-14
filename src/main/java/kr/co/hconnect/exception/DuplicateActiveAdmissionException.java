package kr.co.hconnect.exception;

/**
 * 재원중인 격리/입소 내역이 존재 할 경우 발생
 */
public class DuplicateActiveAdmissionException extends RuntimeException {

    private static final long serialVersionUID = 941980752367871220L;

    /**
     * 에러 내역
     */
    private final String message;

    public DuplicateActiveAdmissionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
