package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.domain.PatientLocation;
import kr.co.hconnect.vo.PatientLocationInfoRequestVO;
import kr.co.hconnect.vo.PatientLocationInfoResponseVO;
import kr.co.hconnect.vo.PatientLocationInfoVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatientLocationDao extends EgovAbstractMapper {

    /**
     * 위치정보 추가
     *
     * @param location {@link PatientLocation}
     */
    public void addPatientLocation(PatientLocation location) {
        insert("kr.co.hconnect.sqlmapper.addPatientLocation", location);
    }

    /**
     * 격리/입소내역ID로 격리/입소정보 조회
     *
     * @param admissionId 격리/입소내역ID
     * @return {@link PatientLocationInfoResponseVO}
     */
    public PatientLocationInfoResponseVO getAdmissionInfoById(String admissionId) {
        return selectOne("kr.co.hconnect.sqlmapper.getAdmissionInfoById", admissionId);
    }

    /**
     * 위치정보 목록 조회
     *
     * @param requestVO {@link PatientLocationInfoRequestVO}
     * @return {@link PatientLocationInfoVO}
     */
    public List<PatientLocationInfoVO> getPatientLocations(PatientLocationInfoRequestVO requestVO) {
        return selectList("kr.co.hconnect.sqlmapper.getPatientLocations", requestVO);
    }
}
