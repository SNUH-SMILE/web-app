package kr.co.hconnect.exception;

import org.springframework.util.StringUtils;

/**
 * 환자 비밀번호 불일치 Exception
 */
public class NotMatchPatientPasswordException extends Throwable {

    /**
     * 환자 비밀번호 불일치 오류내역
     */
    private final String errorMessage;

    public NotMatchPatientPasswordException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        if (StringUtils.isEmpty(errorMessage)) {
            return "비밀번호가 일치하지 않습니다.";
        } else {
            return errorMessage;
        }
    }
}
