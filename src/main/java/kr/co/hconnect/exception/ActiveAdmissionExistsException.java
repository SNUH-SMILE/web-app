package kr.co.hconnect.exception;

/**
 * 격리/입소내역이 존재하는 경우 발생
 */
public class ActiveAdmissionExistsException extends RuntimeException {

    private static final long serialVersionUID = 7324812097264422017L;

    /**
     * 에러 내역
     */
    private final String message;

    public ActiveAdmissionExistsException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
