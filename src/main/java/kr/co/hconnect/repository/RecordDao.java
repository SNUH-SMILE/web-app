package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.NoticeListSearchVO;
import kr.co.hconnect.vo.NoticeVO;
import kr.co.hconnect.vo.PatientVO;
import kr.co.hconnect.vo.RecordVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 진료기록 Dao
 */
@Repository
public class RecordDao extends EgovAbstractMapper {



    /**
     * 신규 진료기록 내역 생성
     *
     * @param vo 신규 진료기록 저장 정보
     * @return affectedRow
     */
    public int insertRecord(RecordVO vo) {
        return insert("kr.co.hconnect.sqlmapper.insertRecord", vo);
    }

    /**
     * 알림 리스트 조회 - 격리/입소내역ID 기준
     *
     * @param  알림 리스트 조회 조건
     * @return List&lt;NoticeVO&gt; 알림 리스트
     */
    public List<RecordVO> selectRecordListByAdmissionId(String id) {
        return selectList("kr.co.hconnect.sqlmapper.selectRecordListByAdmissionId", id);
    }
    public int updatePatient(RecordVO vo) {
        return update("kr.co.hconnect.sqlmapper.updateRecord", vo);
    }

}
