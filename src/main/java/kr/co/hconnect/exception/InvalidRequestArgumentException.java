package kr.co.hconnect.exception;

import org.springframework.validation.BindingResult;

/**
 * 전달된 인수정보가 올바르지 않은 경우 발생
 */
public class InvalidRequestArgumentException extends RuntimeException {

    private static final long serialVersionUID = 3933437498916311406L;

    /**
     * BindingResult
     */
    private final BindingResult bindingResult;

    /**
     * 생성자
     * @param bindingResult 오류내역
     */
    public InvalidRequestArgumentException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
