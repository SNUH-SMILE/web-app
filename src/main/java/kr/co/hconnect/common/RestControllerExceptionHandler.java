package kr.co.hconnect.common;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import kr.co.hconnect.domain.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * REST Controller에서 발생하는 예외 처리
 */
@RestControllerAdvice
public class RestControllerExceptionHandler {

    /**
     * 유효하지 않은 요청 파라메터 코드
     */
    public static final String COD_INVALID_REQUEST_PARAMETER = "99";

    /**
     * {@link InvalidFormatException} 예외 처리
     * @param e {@link InvalidFormatException} 객체
     * @return {@link BaseResponse} 객체
     */
    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse handleInvalidFormatException(InvalidFormatException e) {
        BaseResponse resp = new BaseResponse();
        resp.setCode(COD_INVALID_REQUEST_PARAMETER);
        resp.setMessage(e.getMessage());
        return resp;
    }

}
