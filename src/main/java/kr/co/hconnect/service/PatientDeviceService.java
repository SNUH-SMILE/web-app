package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.domain.PatientDevice;
import kr.co.hconnect.repository.PatientDeviceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 환자별 장비 Service
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class PatientDeviceService extends EgovAbstractServiceImpl {

    /**
     * 환자별 장비 Dao
     */
    private final PatientDeviceDao patientDeviceDao;

    /**
     * 생성자
     *
     * @param patientDeviceDao 환자별 장비 Dao
     */
    @Autowired
    public PatientDeviceService(PatientDeviceDao patientDeviceDao) {
        this.patientDeviceDao = patientDeviceDao;
    }

    /**
     * 환자별 장비 미사용 처리
     *
     * @param admissionId 격리/입소내역ID
     * @return affectedRow
     */
    public int updatePatientDeviceNotUseByAdmissionId(String admissionId) {
        PatientDevice patientDevice = new PatientDevice();
        patientDevice.setAdmissionId(admissionId);
        patientDevice.setUseYn("N");

        return patientDeviceDao.updatePatientDeviceUseYnByAdmissionId(patientDevice);
    }

    /**
     * 환자별 장비 리스트 추가
     *
     * @param patientDeviceList 환자별 장비 추가리스트
     * @return affectedRow
     */
    public int insertPatientDevice(List<PatientDevice> patientDeviceList) {
        int affectedRow = 0;
        // 미사용 사용장비 처리내역
        List<String> initDeviceAdmissionList = new ArrayList<>();

        for (PatientDevice patientDevice : patientDeviceList) {
            // 장비 미사용 처리
            if (!initDeviceAdmissionList.contains(patientDevice.getAdmissionId())) {
                initDeviceAdmissionList.add(patientDevice.getAdmissionId());

                updatePatientDeviceNotUseByAdmissionId(patientDevice.getAdmissionId());
            }

            // 신규 환자별 장비 추가
            affectedRow += patientDeviceDao.insertPatientDevice(patientDevice);
        }

        return affectedRow;
    }
}
