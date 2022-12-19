package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.domain.PatientDevice;
import kr.co.hconnect.domain.PatientDeviceList;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 환자별 장비 Dao
 */
@Repository
public class PatientDeviceDao extends EgovAbstractMapper {

    /**
     * 환자 장비 사용이력 조회
     *
     * @param patientDevice - 환자별 장비
     * @return int - 다른내원 사용이력 카운트
     */
    public int selectPatientDeviceUseHistory(PatientDevice patientDevice) {
        return selectOne("kr.co.hconnect.sqlmapper.selectPatientDeviceUseHistory", patientDevice);
    }

    /**
     * 환자별 장비 사용여부 수정-격리/입소내역ID 기준
     *
     * @param admissionId 격리/입소내역ID
     * @return affectedRow
     */
    public int updatePatientDeviceNotUseByAdmissionId(String admissionId) {
        return update("kr.co.hconnect.sqlmapper.updatePatientDeviceNotUseByAdmissionId", admissionId);
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

    /**
     * 환자별 장비 리스트
     *
     * @param patientDevice 환자별 장비
     * @return affectedRow
     */
    public List<PatientDeviceList> selectPatientDeviceList(PatientDevice vo) {
        return selectList("kr.co.hconnect.sqlmapper.selectPatientDeviceList", vo);
    }




}
