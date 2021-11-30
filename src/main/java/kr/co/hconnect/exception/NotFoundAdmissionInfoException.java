package kr.co.hconnect.exception;

import org.springframework.util.StringUtils;

/**
 * 현재일 기준 내원중인 격리/입소내역이 존재하지 않거나, 한건 이상일 경우 발생
 */
public class NotFoundAdmissionInfoException extends RuntimeException {

    private static final long serialVersionUID = 7203908342314774373L;

    /**
     * 격리/입소내역 오류정보
     */
    private final String errorMessage;
    
    public NotFoundAdmissionInfoException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        if (StringUtils.isEmpty(errorMessage)) {
            return "격리/입소내역이 존재하지 않습니다.";
        } else {
            return errorMessage;
        }
    }
}
