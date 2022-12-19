package kr.co.hconnect.rest;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.domain.*;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.service.AdmissionService;
import kr.co.hconnect.service.PatientDeviceService;
import kr.co.hconnect.vo.ResponseBaseVO;
import kr.co.hconnect.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api")
public class PatientDeviceRestController {

    /**
     * 환자별 장비 Service
     */
    private final PatientDeviceService patientDeviceService;

    /**
     * 격리/입소내역 관리 Service
     */
    private final AdmissionService admissionService;

    private final MessageSource messageSource;

    /**
     * 생성자
     *
     * @param patientDeviceService 환자별 장비 Service
     * @param admissionService 격리/입소내역 관리 Service
     * @param messageSource MessageSource
     */
    @Autowired
    public PatientDeviceRestController(PatientDeviceService patientDeviceService, AdmissionService admissionService
                                      ,MessageSource messageSource) {
        this.patientDeviceService = patientDeviceService;
        this.admissionService = admissionService;
        this.messageSource = messageSource;
    }

    /**
     * 환자별 장비 추가
     *
     * @param savePatientDeviceInfo 환자별 장비 저장 정보
     * @return BaseResponse
     */
    @RequestMapping(value = "/patient/device", method = RequestMethod.POST)
    public PatientDeviceSavedInfo insertPatientDevice(@Valid @RequestBody SavePatientDeviceInfo savePatientDeviceInfo
        , BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidRequestArgumentException(result);
        }

        // 격리/입소내역ID
        String admissionId = admissionService.selectActiveAdmissionByLoginId(savePatientDeviceInfo.getLoginId()).getAdmissionId();

        // 격리/입소내역ID 바인딩
        for (PatientDevice patientDevice : savePatientDeviceInfo.getPatientDeviceList()) {
            patientDevice.setAdmissionId(admissionId);
        }

        // 환자별 장비 등록
        List<PatientDeviceUseHistory> patientDeviceUseHistories =
            patientDeviceService.insertPatientDevice(admissionId, savePatientDeviceInfo.getPatientDeviceList());

        // 저장 결과정보 생성
        PatientDeviceSavedInfo patientDeviceSavedInfo = new PatientDeviceSavedInfo();
        patientDeviceSavedInfo.setCode(ApiResponseCode.SUCCESS.getCode());
        patientDeviceSavedInfo.setMessage(messageSource.getMessage("message.success.savePatientDevice"
            , null, Locale.getDefault()));
        patientDeviceSavedInfo.setDeviceUseHistoryList(patientDeviceUseHistories);

        return patientDeviceSavedInfo;
    }

    @RequestMapping(value = "/patient/getDevice", method = RequestMethod.POST)
    public ResponseBaseVO<List<PatientDeviceList>> selectPatientDevice(@Valid @RequestBody LoginId loginId
        , BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidRequestArgumentException(result);
        }
        // 격리/입소내역ID
        String admissionId = admissionService.selectActiveAdmissionByLoginId(loginId.getLoginId()).getAdmissionId();

        PatientDevice vo = new PatientDevice();
        vo.setAdmissionId(admissionId);

        ResponseBaseVO<List<PatientDeviceList>> responseVO = new ResponseBaseVO<>();

        responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
        responseVO.setMessage("조회성공");
        responseVO.setResult(patientDeviceService.selectPatientDevice(vo));

        return responseVO;

    }

}
