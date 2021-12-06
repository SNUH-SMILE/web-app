package kr.co.hconnect.rest;

import kr.co.hconnect.domain.*;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.exception.NotFoundAdmissionInfoException;
import kr.co.hconnect.exception.NotFoundPatientInfoException;
import kr.co.hconnect.service.PatientService;
import kr.co.hconnect.service.QantnStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
     * 격리상태 관리 Service
     */
    private final QantnStatusService qantnStatusService;

    /**
     * 생성자
     * @param patientService 환자관리 Service
     * @param qantnStatusService 격리상태 관리 Service
     */
    @Autowired
    public PatientRestController(PatientService patientService, QantnStatusService qantnStatusService) {
        this.patientService = patientService;
        this.qantnStatusService = qantnStatusService;
    }

    /**
     * 회원정보 조회-로그인ID 기준
     *
     * @param loginId 로그인ID VO
     * @return Patient 회원정보
     */
    @RequestMapping(value = "/patient", method = RequestMethod.GET)
    public Patient selectPatient(@Valid @RequestBody LoginId loginId, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidRequestArgumentException(result);
        }

        // 회원정보 조회
        Patient patient = patientService.selectPatientByLoginId(loginId.getLoginId());

        if (patient != null) {
            patient.setCode("00");
            patient.setMessage("회원정보 조회 완료.");
        } else {
            patient = new Patient();
            patient.setCode("99");
            patient.setMessage("회원정보가 존재하지 않습니다.");
        }

        return patient;
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
            throw new InvalidRequestArgumentException(result);
        }

        // 신규 가입일 경우 데이터 확인
        if (patient.getFlag().equals("A")) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setCode("99");

            if (!patient.getSsn().matches("^[0-9]{13}")) {
                // 주민번호 입력형태 확인
                baseResponse.setMessage("주민번호를 확인하세요.");
                return baseResponse;
            } else if (patientService.checkDuplicateLoginId(patient.getLoginId())) {
                // 동일 로그인ID 중복여부 확인
                baseResponse.setMessage("사용중인 로그인ID 입니다.");
                return baseResponse;
            }
        }

        BaseResponse baseResponse = new BaseResponse();

        try {
            // 환자정보 저장
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
     * 로그인ID 중복체크
     *
     * @param loginId 로그인ID
     * @return LoginDuplicateResult 로그인ID 중복 확인 정보
     */
    @RequestMapping(value = "/patient/duplicate", method = RequestMethod.GET)
    public LoginDuplicateResult checkDuplicateLoginId(@Valid @RequestBody LoginId loginId, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidRequestArgumentException(result);
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
     * 환자 비밀번호 수정
     *
     * @param loginInfo 로그인 구성정보
     * @return BaseResponse
     */
    @RequestMapping(value = "/patient/password", method = RequestMethod.PUT)
    public BaseResponse changePassword(@Valid @RequestBody LoginInfo loginInfo
            , BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidRequestArgumentException(result);
        }

        BaseResponse baseResponse = new BaseResponse();
        try {
            patientService.updatePatientPasswordByLoginId(loginInfo);

            baseResponse.setCode("00");
            baseResponse.setMessage("비밀번호가 변경되었습니다.");
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
            throw new InvalidRequestArgumentException(result);
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

    /**
     * 개인정보 존재여부 확인
     *
     * @param searchExistLoginInfo 개인정보 확인 검색 조건
     * @return ExistResult
     */
    @RequestMapping(value = "/patients/find", method = RequestMethod.GET)
    public ExistResult checkExistLoginInfo(@Valid @RequestBody SearchExistLoginInfo searchExistLoginInfo
            , BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidRequestArgumentException(result);
        }

        // 환자정보 조회
        List<Patient> patientList = patientService.selectPatientBySearchExistLoginInfo(searchExistLoginInfo);

        ExistResult existResult = new ExistResult();

        if (patientList.size() == 0) {
            existResult.setCode("00");
            existResult.setMessage("동일 환자정보가 존재하지 않습니다.");
            existResult.setExistYn("N");
        } else {
            existResult.setCode("00");
            existResult.setMessage("동일 환자정보가 존재합니다.");
            existResult.setExistYn("Y");
        }

        return existResult;
    }

    /**
     * 격리 상태 조회
     *
     * @param loginId 로그인ID VO
     * @return SaveQuarantineStatusInfo
     */
    @RequestMapping(value = "/quarantine", method = RequestMethod.GET)
    public SaveQuarantineStatusInfo selectQuarantine(@Valid @RequestBody LoginId loginId, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidRequestArgumentException(result);
        }

        // 격리상태 조회
        QantnStatus qantnStatus = qantnStatusService.selectQantnStatus(loginId.getLoginId());

        SaveQuarantineStatusInfo saveQuarantineStatusInfo = new SaveQuarantineStatusInfo();

        if (qantnStatus != null) {
            saveQuarantineStatusInfo.setCode("00");
            saveQuarantineStatusInfo.setMessage("격리상태 조회 완료.");
            saveQuarantineStatusInfo.setQuarantineStatusDiv(qantnStatus.getQantnStatusDiv());
        } else {
            saveQuarantineStatusInfo.setCode("99");
            saveQuarantineStatusInfo.setMessage("격리상태 내역이 존재하지 않습니다.");
        }

        return saveQuarantineStatusInfo;
    }

    /**
     * 격리 상태 저장
     *
     * @param saveQuarantineStatusInfo 격리상태 저장 정보
     * @return BaseResponse
     */
    @RequestMapping(value = "/quarantine", method = RequestMethod.POST)
    public BaseResponse saveQuarantine(@Valid @RequestBody SaveQuarantineStatusInfo saveQuarantineStatusInfo
        , BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidRequestArgumentException(result);
        }

        BaseResponse baseResponse = new BaseResponse();

        try {
            qantnStatusService.insertQantnStatus(saveQuarantineStatusInfo);

            baseResponse.setCode("00");
            baseResponse.setMessage("격리 상태 저장 완료");
        } catch (NotFoundAdmissionInfoException e) {
            baseResponse.setCode("99");
            baseResponse.setMessage(e.getMessage());
        }

        return baseResponse;
    }

}
