package kr.co.hconnect.common;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import kr.co.hconnect.domain.BaseResponse;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.exception.NotFoundAdmissionInfoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
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
    public static final String CODE_INVALID_REQUEST_PARAMETER = "99";

    /**
     * {@link InvalidFormatException} 예외 처리
     * @param e {@link InvalidFormatException} 객체
     * @return {@link BaseResponse} 객체
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    public BaseResponse handleInvalidFormatException(InvalidFormatException e) {
        BaseResponse resp = new BaseResponse();
        resp.setCode(CODE_INVALID_REQUEST_PARAMETER);
        resp.setMessage(e.getMessage());
        return resp;
    }

    /**
     * {@link InvalidRequestArgumentException} 예외 처리
     * @param e {@link InvalidRequestArgumentException} 객체
     * @return {@link BaseResponse} 객체
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestArgumentException.class)
    public BaseResponse handleInvalidRequestArgumentException(InvalidRequestArgumentException e) {
        StringBuilder sbError = new StringBuilder();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            sbError.append(error.getDefaultMessage());
        }

        BaseResponse resp = new BaseResponse();
        resp.setCode(CODE_INVALID_REQUEST_PARAMETER);
        resp.setMessage(sbError.toString());
        return resp;
    }

    /**
     * {@link NotFoundAdmissionInfoException} 예외 처리
     * @param e {@link NotFoundAdmissionInfoException} 객체
     * @return {@link BaseResponse} 객체
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundAdmissionInfoException.class)
    public BaseResponse handleNotFoundAdmissionInfoException(NotFoundAdmissionInfoException e) {
        BaseResponse resp = new BaseResponse();
        resp.setCode(e.getErrorCode());
        resp.setMessage(e.getMessage());
        return resp;
    }


}
