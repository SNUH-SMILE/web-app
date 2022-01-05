package kr.co.hconnect.rest;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.domain.*;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.exception.NotFoundPatientInfoException;
import kr.co.hconnect.exception.NotMatchPatientPasswordException;
import kr.co.hconnect.service.PatientService;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping("/api")
public class LoginRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRestController.class);

    /**
     * 환자 관리 Service
     */
    private final PatientService patientService;

    private final MessageSource messageSource;

    /**
     * 생성자
     *
     * @param patientService 환자관리 Service
     * @param messageSource MessageSource
     */
    @Autowired
    public LoginRestController(PatientService patientService, MessageSource messageSource) {
        this.patientService = patientService;
        this.messageSource = messageSource;
    }

    /**
     * 본인인증
     *
     * @param identityInfo 본인인증 확인 정보
     * @return IdentityResult
     */
    @RequestMapping(value = "/identity", method = RequestMethod.GET)
    public IdentityResult checkIdentity(@Valid @RequestBody IdentityInfo identityInfo, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidRequestArgumentException(result);
        }

        IdentityResult identityResult = null;

        try {
            // 본인인증 내역 확인
            identityResult = patientService.selectIdentityInfo(identityInfo.getSsn());

            if (identityResult != null) {
                // 환자정보 존재
                identityResult.setCode(ApiResponseCode.SUCCESS.getCode());
                identityResult.setMessage(messageSource.getMessage("message.success.Identity", null, Locale.getDefault()));
            } else {
                // 환자정보 존재하지 않음
                identityResult = setFailIdentityResult(ApiResponseCode.SUCCESS.getCode()
                    , messageSource.getMessage("message.success.Identity", null, Locale.getDefault()));
            }
        } catch (MyBatisSystemException e) {
            // 다중 입소내역으로 인한 오류
            if (e.getCause() instanceof TooManyResultsException) {
                identityResult = setFailIdentityResult(ApiResponseCode.DUPLICATE_ACTIVE_ADMISSION_INFO.getCode()
                    , messageSource.getMessage("message.duplicate.admissionInfo", null, Locale.getDefault()));
            }
        }

        return identityResult;
    }

    /**
     * 본인인증 실패 결과 Response 생성
     *
     * @param code    결과코드
     * @param message 결과메시지
     * @return IdentityResult
     */
    private IdentityResult setFailIdentityResult(String code, String message) {
        IdentityResult identityResult = new IdentityResult();
        identityResult.setCode(code);
        identityResult.setMessage(message);
        identityResult.setQuarantineDiv("0");
        identityResult.setRegisterYn("N");

        return identityResult;
    }

    /**
     * 로그인
     *
     * @param loginInfo 로그인 정보
     * @return BaseResponse
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public BaseResponse login(@Valid @RequestBody LoginInfo loginInfo, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidRequestArgumentException(result);
        }

        BaseResponse baseResponse = new BaseResponse();

        try {
            Patient patient = patientService.selectPatientByLoginInfo(loginInfo);
            baseResponse.setCode(ApiResponseCode.SUCCESS.getCode());
            baseResponse.setMessage(messageSource.getMessage("message.success.login", null, Locale.getDefault()));
        } catch (NotFoundPatientInfoException e) {
            baseResponse.setCode(ApiResponseCode.NOT_FOUND_PATIENT_INFO.getCode());
            baseResponse.setMessage(e.getMessage());
        } catch (NotMatchPatientPasswordException e) {
            baseResponse.setCode(ApiResponseCode.NOT_MATCH_PATIENT_PASSWORD.getCode());
            baseResponse.setMessage(e.getMessage());
        }

        return baseResponse;
    }
}
