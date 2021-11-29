package kr.co.hconnect.rest;

import kr.co.hconnect.domain.*;
import kr.co.hconnect.exception.NotFoundPatientInfoException;
import kr.co.hconnect.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/api")
public class PatientRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientRestController.class);

    /**
     * 환자 관리 Service
     */
    private final PatientService patientService;

    /**
     * 생성자
     * @param patientService 환자관리 Service
     */
    @Autowired
    public PatientRestController (PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * 로그인ID 중복체크
     *
     * @param loginId 로그인ID
     * @return LoginDuplicateResult 로그인ID 중복 확인 정보
     */
    @RequestMapping(value = "/patient/duplicate", method = RequestMethod.GET)
    public LoginDuplicateResult checkDuplicateLoginId(@Valid @RequestBody LoginId loginId, BindingResult result) {
        if (result.hasErrors()) {
            // TODO::Valid 체크 처리
            StringBuilder sbError = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                LOGGER.info(error.toString());
                sbError.append(error.getDefaultMessage());
            }

            LoginDuplicateResult loginDuplicateResult = new LoginDuplicateResult();
            loginDuplicateResult.setCode("99");
            loginDuplicateResult.setMessage(sbError.toString());

            return loginDuplicateResult;
        }

        boolean isDuplicateLoginId = patientService.checkDuplicateLoginId(loginId.getLoginId());

        LoginDuplicateResult loginDuplicateResult = new LoginDuplicateResult();
        loginDuplicateResult.setCode("00");
        loginDuplicateResult.setMessage(String.format("ID : [%s] %s"
                , loginId.getLoginId()
                , isDuplicateLoginId ? "사용중입니다." : "사용중이지 않습니다."));
        loginDuplicateResult.setDupYn(isDuplicateLoginId ? "Y" : "N");

        return loginDuplicateResult;
    }

    /**
     * 환자정보 저장
     *
     * @param patient 환자 저장 정보
     * @return BaseResponse
     */
    @RequestMapping(value = "/patient", method = RequestMethod.POST)
    public BaseResponse savePatient(@Valid @RequestBody Patient patient, BindingResult result) {
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
            Patient savePatientInfo = patientService.savePatientInfo(patient);

            baseResponse.setCode("00");
            baseResponse.setMessage("환자정보를 저장하였습니다.");
        } catch (NotFoundPatientInfoException e) {
            baseResponse.setCode("99");
            baseResponse.setMessage(e.getMessage());
        }

        return baseResponse;
    }

    /**
     * 로그인ID 찾기
     *
     * @param searchLoginIdInfo 아이디 검색 조건 정보
     * @return FindLoginIdResult
     */
    @RequestMapping(value = "/patients/findById", method = RequestMethod.GET)
    public FindLoginIdResult selectPatientLoginId(@Valid @RequestBody SearchLoginIdInfo searchLoginIdInfo
            , BindingResult result) {
        if (result.hasErrors()) {
            // TODO::Valid 체크 처리
            StringBuilder sbError = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                LOGGER.info(error.toString());
                sbError.append(error.getDefaultMessage());
            }

            FindLoginIdResult findLoginIdResult = new FindLoginIdResult();
            findLoginIdResult.setCode("99");
            findLoginIdResult.setMessage(sbError.toString());

            return findLoginIdResult;
        }

        // 환자정보 조회
        List<Patient> patientList = patientService.selectPatientBySearchLoginIdInfo(searchLoginIdInfo);

        FindLoginIdResult findLoginIdResult = new FindLoginIdResult();

        if (patientList.size() == 1) {
            findLoginIdResult.setCode("00");
            findLoginIdResult.setLoginId(patientList.get(0).getLoginId());
        } else {
            findLoginIdResult.setCode("99");

            if (patientList.size() == 0) {
                findLoginIdResult.setMessage("환자정보가 존재하지 않습니다.");
            } else {
                findLoginIdResult.setMessage("동일한 환자정보가 존재합니다.");
            }
        }

        return findLoginIdResult;
    }

    @RequestMapping(value = "/patients/find", method = RequestMethod.GET)
    public ExistResult checkExistLoginInfo(@Valid @RequestBody SearchExistLoginInfo searchExistLoginInfo
            , BindingResult result) {
        if (result.hasErrors()) {
            // TODO::Valid 체크 처리
            StringBuilder sbError = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                LOGGER.info(error.toString());
                sbError.append(error.getDefaultMessage());
            }

            ExistResult existResult = new ExistResult();
            existResult.setCode("99");
            existResult.setMessage(sbError.toString());

            return existResult;
        }

        // 환자정보 조회
        List<Patient> patientList = patientService.selectPatientBySearchExistLoginInfo(searchExistLoginInfo);

        ExistResult existResult = new ExistResult();

        if (patientList.size() == 0) {
            existResult.setCode("00");
            existResult.setMessage("동일 환자정보가 존재하지 않습니다.");
            existResult.setExistYn("N");
        } else {
            existResult.setCode("99");
            existResult.setMessage("동일 환자정보가 존재합니다.");
            existResult.setExistYn("Y");
        }

        return existResult;
    }
}
