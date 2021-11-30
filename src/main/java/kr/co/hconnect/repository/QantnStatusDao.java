package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.domain.QantnStatus;
import org.springframework.stereotype.Repository;

/**
 * 격리상태 관리 Dao
 */
@Repository
public class QantnStatusDao extends EgovAbstractMapper {

    /**
     * 현재 격리상태 조회
     *
     * @param admissionId 격리/입소내역ID
     * @return QantnStatus
     */
    public QantnStatus selectActiveQantnStatus(String admissionId) {
        return selectOne("kr.co.hconnect.sqlmapper.selectActiveQantnStatus", admissionId);
    }

    /**
     * 격리상태 저장
     *
     * @param qantnStatus 격리상태
     * @return affectedRow
     */
    public int insertQantnStatus(QantnStatus qantnStatus) {
        return insert("kr.co.hconnect.sqlmapper.insertQantnStatus", qantnStatus);
    }

}
