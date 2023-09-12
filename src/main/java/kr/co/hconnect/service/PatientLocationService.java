package kr.co.hconnect.service;

import kr.co.hconnect.domain.PatientLocation;
import kr.co.hconnect.repository.PatientLocationDao;
import kr.co.hconnect.vo.AdmissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PatientLocationService {

    private final AdmissionService admissionService;
    private final PatientLocationDao patientLocationDao;

    @Autowired
    public PatientLocationService(AdmissionService admissionService, PatientLocationDao patientLocationDao) {
        this.admissionService = admissionService;
        this.patientLocationDao = patientLocationDao;
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
}
