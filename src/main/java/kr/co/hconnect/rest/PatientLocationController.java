package kr.co.hconnect.rest;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.domain.BaseResponse;
import kr.co.hconnect.domain.PatientLocation;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.service.PatientLocationService;
import kr.co.hconnect.vo.PatientLocationInfoRequestVO;
import kr.co.hconnect.vo.PatientLocationInfoResponseVO;
import kr.co.hconnect.vo.PatientLocationResponseVO;
import kr.co.hconnect.vo.PatientLocationVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestController
public class PatientLocationController {

    private final PatientLocationService service;
    private final MessageSource messageSource;

    @Autowired
    public PatientLocationController(PatientLocationService service, MessageSource messageSource) {
        this.service = service;
        this.messageSource = messageSource;
    }

    /**
     * 사용자 위치정보 추가
     *
     * @param patientLocationVO {@link PatientLocationVO}
     * @param result            {@link BindingResult}
     * @return {@link BaseResponse}
     */
    @PostMapping("/api/location/setLocationList")
    public PatientLocationResponseVO addPatientLocation(@RequestBody @Valid PatientLocationVO patientLocationVO
        , BindingResult result) {
        // 유효성 검사
        if (result.hasErrors()) {
            throw new InvalidRequestArgumentException(result);
        }

        // 위치정보 저장
        PatientLocation location = new PatientLocation();
        location.setResultDt(LocalDateTime.parse(
            patientLocationVO.getResultDate() + patientLocationVO.getResultTime(),
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        location.setLatitude(patientLocationVO.getLatitude());
        location.setLongitude(patientLocationVO.getLongitude());
        location.setLoginId(patientLocationVO.getLoginId());
        service.addPatientLocation(location);

        // 응답
        PatientLocationResponseVO responseVO = new PatientLocationResponseVO();
        responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
        responseVO.setMessage(messageSource.getMessage("message.success.addPatientLocation", null, Locale.getDefault()));
        return responseVO;
    }

    /**
     * 사용자 위치정보 조회
     *
     * @param requestVO {@link PatientLocationInfoRequestVO}
     * @param result    {@link BindingResult}
     * @return {@link PatientLocationInfoResponseVO}
     */
    @GetMapping("/api/patient-locations")
    public PatientLocationInfoResponseVO getPatientLocations(@Valid @ModelAttribute PatientLocationInfoRequestVO requestVO
        , BindingResult result
    ) {
        // 유효성 검사
        if (result.hasErrors()) {
            throw new InvalidRequestArgumentException(result);
        }

        // 위치정보 조회
        PatientLocationInfoResponseVO responseVO = service.getPatientLocationInfo(requestVO);
        responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
        responseVO.setMessage(messageSource.getMessage("message.success.searchResultList", null, Locale.getDefault()));
        return responseVO;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        binder.registerCustomEditor(LocalDate.class, new CustomLocalDateEditor(formatter, true));
    }

    /**
     * String to LocalDate PropertyEditor
     */
    static class CustomLocalDateEditor extends PropertyEditorSupport {
        private final DateTimeFormatter formatter;
        private final boolean allowEmpty;

        CustomLocalDateEditor(DateTimeFormatter formatter, boolean allowEmpty) {
            this.formatter = formatter;
            this.allowEmpty = allowEmpty;
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (allowEmpty && StringUtils.isEmpty(text)) {
                setValue(null);
            } else {
                setValue(LocalDate.parse(text, formatter));
            }
        }

        @Override
        public String getAsText() {
            LocalDate value = (LocalDate) getValue();
            return value != null ? formatter.format(value) : "";
        }
    }

}
