package kr.co.hconnect.exception;

/**
 * 환자 로그인ID 중복 Exception
 */
public class DuplicatePatientLoginIdException extends RuntimeException {

    private static final long serialVersionUID = -1208615054275765674L;

    /**
     * 에러 내역
     */
    private final String message;

    public DuplicatePatientLoginIdException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
