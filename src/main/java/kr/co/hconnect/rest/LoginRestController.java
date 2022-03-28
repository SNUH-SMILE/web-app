package kr.co.hconnect.rest;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.domain.*;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.exception.NotFoundPatientInfoException;
import kr.co.hconnect.exception.NotMatchPatientPasswordException;
import kr.co.hconnect.jwt.TokenProvider;
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

    private final TokenProvider tokenProvider;

    private final MessageSource messageSource;

    /**
     * 생성자
     *  @param patientService 환자관리 Service
     * @param tokenProvider jwt Token 관리
     * @param messageSource MessageSource
     */
    @Autowired
    public LoginRestController(PatientService patientService, TokenProvider tokenProvider, MessageSource messageSource) {
        this.patientService = patientService;
        this.tokenProvider = tokenProvider;
        this.messageSource = messageSource;
    }

    /**
     * 본인인증
     *
     * @param identityInfo 본인인증 확인 정보
     * @return IdentityResult
     */
    @RequestMapping(value = "/identity", method = RequestMethod.POST)
    public IdentityResult checkIdentity(@Valid @RequestBody IdentityInfo identityInfo, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidRequestArgumentException(result);
        }

        IdentityResult identityResult = null;

        try {
            // 본인인증 내역 확인
            identityResult = patientService.selectIdentityInfo(identityInfo);

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
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginSuccessInfo login(@Valid @RequestBody LoginInfo loginInfo, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidRequestArgumentException(result);
        }

        LoginSuccessInfo loginSuccessInfo = new LoginSuccessInfo();

        try {
            // 로그인 정보 확인
            Patient patient = patientService.selectPatientByLoginInfo(loginInfo);

            // Token 발행
            String jwtToken = tokenProvider.createToken();

            // 응답데이터 구성
            loginSuccessInfo.setCode(ApiResponseCode.SUCCESS.getCode());
            loginSuccessInfo.setMessage(messageSource.getMessage("message.success.login", null, Locale.getDefault()));
            loginSuccessInfo.setToken(jwtToken);
        } catch (NotFoundPatientInfoException e) {
            loginSuccessInfo.setCode(ApiResponseCode.NOT_FOUND_PATIENT_INFO.getCode());
            loginSuccessInfo.setMessage(e.getMessage());
        } catch (NotMatchPatientPasswordException e) {
            loginSuccessInfo.setCode(ApiResponseCode.NOT_MATCH_PATIENT_PASSWORD.getCode());
            loginSuccessInfo.setMessage(e.getMessage());
        }

        return loginSuccessInfo;
    }
}
