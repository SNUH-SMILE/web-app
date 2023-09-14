package kr.co.hconnect.service;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.domain.PatientLocation;
import kr.co.hconnect.exception.NotFoundAdmissionInfoException;
import kr.co.hconnect.repository.PatientLocationDao;
import kr.co.hconnect.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@Transactional(readOnly = true)
public class PatientLocationService {

    private final AdmissionService admissionService;
    private final PatientLocationDao patientLocationDao;
    private final MessageSource messageSource;

    @Autowired
    public PatientLocationService(AdmissionService admissionService, PatientLocationDao patientLocationDao, MessageSource messageSource) {
        this.admissionService = admissionService;
        this.patientLocationDao = patientLocationDao;
        this.messageSource = messageSource;
    }

    /**
     * 위치정보 추가
     *
     * @param location {@link PatientLocation}
     */
    @Transactional
    public void addPatientLocation(PatientLocation location) {
        // 격리/입소내역 확인
        AdmissionVO admissionVO = admissionService.selectActiveAdmissionByLoginId(location.getLoginId());
        location.setAdmissionId(admissionVO.getAdmissionId());

        // 위치정보 추가
        patientLocationDao.addPatientLocation(location);
    }

    /**
     * 위치정보 조회
     *
     * @param requestVO {@link PatientLocationInfoRequestVO}
     * @return {@link PatientLocationInfoResponseVO}
     */
    public PatientLocationInfoResponseVO getPatientLocationInfo(PatientLocationInfoRequestVO requestVO) {
        // 환자 및 격리/내원정보 조회
        PatientLocationInfoResponseVO responseVO = patientLocationDao.getAdmissionInfoById(requestVO.getAdmissionId());
        if (responseVO == null) {
            throw new NotFoundAdmissionInfoException(ApiResponseCode.NOT_FOUND_ADMISSION_INFO.getCode()
                , messageSource.getMessage("message.notfound.admissionInfo", null, Locale.getDefault()));
        }

        // 위치정보 목록 조회
        responseVO.setPatientLocations(patientLocationDao.getPatientLocations(requestVO));
        return responseVO;
    }
}
