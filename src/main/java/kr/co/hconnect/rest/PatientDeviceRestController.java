package kr.co.hconnect.rest;

import kr.co.hconnect.domain.BaseResponse;
import kr.co.hconnect.domain.PatientDevice;
import kr.co.hconnect.domain.SavePatientDeviceInfo;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.service.AdmissionService;
import kr.co.hconnect.service.PatientDeviceService;
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
     */
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
    @RequestMapping(value = "/device", method = RequestMethod.POST)
    public BaseResponse insertPatientDevice(@Valid @RequestBody SavePatientDeviceInfo savePatientDeviceInfo
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
        patientDeviceService.insertPatientDevice(savePatientDeviceInfo.getPatientDeviceList());

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode("00");
        // baseResponse.setMessage("환자별 장비 저장 완료.");
        baseResponse.setMessage(messageSource.getMessage("message.success.savePatientDevice", null, Locale.getDefault()));

        return baseResponse;
    }
}
