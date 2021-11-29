package kr.co.hconnect.exception;

import org.springframework.util.StringUtils;

/**
 * 환자정보 미존재 Exception
 */
public class NotFoundPatientInfoException extends RuntimeException {

    /**
     * 환자정보 오류 내역
     */
    private final String errorMessage;

    public NotFoundPatientInfoException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        if (StringUtils.isEmpty(errorMessage)) {
            return "환자정보가 존재하지 않습니다.";
        } else {
            return errorMessage;
        }
    }

}
