package kr.co.hconnect.rest;

import kr.co.hconnect.domain.*;
import kr.co.hconnect.exception.NotFoundPatientInfoException;
import kr.co.hconnect.exception.NotMatchPatientPasswordException;
import kr.co.hconnect.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class LoginRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRestController.class);

    /**
     * 환자 관리 Service
     */
    private final PatientService patientService;

    /**
     * 생서자
     * @param patientService 환자관리 Service
     */
    @Autowired
    public LoginRestController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * 로그인
     * @param loginInfo 로그인 정보
     * @return BaseResponse
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public BaseResponse login(@Valid @RequestBody LoginInfo loginInfo, BindingResult result) {
        if (result.hasErrors()) {
            // TODO::Valid 체크 처리
            StringBuilder sbError = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                LOGGER.info(error.toString());
                sbError.append(error.getDefaultMessage());
            }

            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setCode("99");
            baseResponse.setMessage(sbError.toString());

            return baseResponse;
        }

        BaseResponse baseResponse = new BaseResponse();

        try {
            Patient patient = patientService.selectPatientByLoginInfo(loginInfo);
            baseResponse.setCode("00");
            baseResponse.setMessage(String.format("%s 님 로그인 성공", patient.getPatientNm()));
        } catch (NotFoundPatientInfoException e) {
            baseResponse.setCode("99");
            baseResponse.setMessage(e.getMessage());
        } catch (NotMatchPatientPasswordException e) {
            baseResponse.setCode("99");
            baseResponse.setMessage(e.getMessage());
        }

        return baseResponse;
    }
}
