package kr.co.hconnect.common;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 기본 WebBindingInitializer 구현
 */
public class DefaultBindingInitializer implements WebBindingInitializer {

    private Validator validator;

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    /**
     * Initialize the given DataBinder for the given request.
     * @param binder the DataBinder to initialize
     * @param request the web request that the data binding happens within
     */
    @Override
    public void initBinder(WebDataBinder binder, WebRequest request) {
        // Empty String -> null
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

        // yyyy-MM-dd 포맷 문자열 -> Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

        // Validator 설정
        if (validator != null) {
            binder.setValidator(validator);
        }
    }

}
