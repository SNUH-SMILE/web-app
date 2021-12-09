package kr.co.hconnect.exception;

/**
 * 현재일 기준 내원중인 격리/입소내역이 존재하지 않거나, 한건 이상일 경우 발생
 */
public class NotFoundAdmissionInfoException extends RuntimeException {

    private static final long serialVersionUID = 7203908342314774373L;

    /**
     * 에러코드
     */
    private final String errorCode;

    /**
     * 격리/입소내역 오류정보
     */
    private final String errorMessage;
    
    public NotFoundAdmissionInfoException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * 에러코드
     */
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
