package kr.co.hconnect.exception;

import org.springframework.util.StringUtils;

/**
 * 환자 로그인ID 중복 Exception
 */
public class DuplicatePatientLoginIdException extends RuntimeException {

    private static final long serialVersionUID = -1208615054275765674L;

    /**
     * 로그인ID
     */
    private final String loginId;

    public DuplicatePatientLoginIdException(String loginId) {
        this.loginId = loginId;
    }

    @Override
    public String getMessage() {
        if (StringUtils.isEmpty(loginId)) {
            return "해당 아이디는 사용중입니다.";
        } else {
            return String.format("[%s] 해당 아이디는 사용중입니다.", loginId);
        }
    }
}
