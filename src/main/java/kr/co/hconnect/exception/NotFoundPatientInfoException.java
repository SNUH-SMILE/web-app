package kr.co.hconnect.exception;

/**
 * 환자정보 미존재 Exception
 */
public class NotFoundPatientInfoException extends RuntimeException {

    private static final long serialVersionUID = -4652372284776165213L;

    /**
     * 환자정보 오류 내역
     */
    private final String errorMessage;

    public NotFoundPatientInfoException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }

}
