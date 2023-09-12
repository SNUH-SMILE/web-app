package kr.co.hconnect.rest;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.domain.BaseResponse;
import kr.co.hconnect.domain.PatientLocation;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.service.PatientLocationService;
import kr.co.hconnect.vo.PatientLocationResponseVO;
import kr.co.hconnect.vo.PatientLocationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

}
