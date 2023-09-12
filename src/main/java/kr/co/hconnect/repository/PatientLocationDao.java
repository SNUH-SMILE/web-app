package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.domain.PatientLocation;
import org.springframework.stereotype.Repository;

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
}
