package kr.co.hconnect.exception;

/**
 * 환자 비밀번호 불일치 Exception
 */
public class NotMatchPatientPasswordException extends RuntimeException {

    private static final long serialVersionUID = 8031810052520379149L;

    /**
     * 환자 비밀번호 불일치 오류내역
     */
    private final String errorMessage;

    public NotMatchPatientPasswordException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
