package kr.co.hconnect.exception;

import org.springframework.util.StringUtils;

public class NotFoundAdmissionInfoException extends RuntimeException {

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
