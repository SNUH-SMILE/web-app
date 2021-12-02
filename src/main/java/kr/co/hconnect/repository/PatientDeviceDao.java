package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.domain.PatientDevice;
import org.springframework.stereotype.Repository;

/**
 * 환자별 장비 Dao
 */
@Repository
public class PatientDeviceDao extends EgovAbstractMapper {

    /**
     * 환자별 장비 사용여부 수정-격리/입소내역ID 기준
     *
     * @param patientDevice 환자별 장비
     * @return affectedRow
     */
    public int updatePatientDeviceUseYnByAdmissionId(PatientDevice patientDevice) {
        return update("kr.co.hconnect.sqlmapper.updatePatientDeviceUseYnByAdmissionId", patientDevice);
    }

    /**
     * 환자별 장비 입력
     *
     * @param patientDevice 환자별 장비
     * @return affectedRow
     */
    public int insertPatientDevice(PatientDevice patientDevice) {
        return insert("kr.co.hconnect.sqlmapper.insertPatientDevice", patientDevice);
    }

}
