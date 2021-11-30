package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MeasurementResultDao extends EgovAbstractMapper {

    /**
     * @param admissionId 입소자 Id
     * @return 읽지않은 공지사항 갯수
     */
    public int selectUnreadNotice(String admissionId) {
        return selectList("kr.co.hconnect.sqlmapper.selectNotice", admissionId).size();
    }

    public List<BtResult> selectBtList(SearchResultInfo searchResultInfo) {
        return selectList("kr.co.hconnect.sqlmapper.selectBtList", searchResultInfo);
    }

    public List<HrResult> selectHrList(SearchResultInfo searchResultInfo) {
        return selectList("kr.co.hconnect.sqlmapper.selectHrList", searchResultInfo);
    }

    public List<SpO2Result> selectSpO2List(SearchResultInfo searchResultInfo) {
        return selectList("kr.co.hconnect.sqlmapper.selectSpO2List", searchResultInfo);
    }

    public List<BpResult> selectBpList(SearchResultInfo searchResultInfo) {
        return selectList("kr.co.hconnect.sqlmapper.selectBpList", searchResultInfo);
    }

    public List<StepCountResult> selectStepList(SearchResultInfo searchResultInfo) {
        return selectList("kr.co.hconnect.sqlmapper.selectStepList", searchResultInfo);
    }
}
