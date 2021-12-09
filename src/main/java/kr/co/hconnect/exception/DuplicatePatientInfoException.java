package kr.co.hconnect.exception;

/**
 * 동일 환자정보 존재 Exception
 */
public class DuplicatePatientInfoException extends RuntimeException {

    private static final long serialVersionUID = -2583386757017787896L;

    /**
     * 에러 내역
     */
    private final String message;

    public DuplicatePatientInfoException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
