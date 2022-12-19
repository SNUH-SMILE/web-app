package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MeasurementResultDao extends EgovAbstractMapper {

    /**
     * 측정일자별 상세체온 목록 조회
     *
     * @param searchResultInfo 검색조건
     * @return 측정일자별 상세체온 목록 조회
     */
    public List<BtResult> selectBtList(SearchResultInfo searchResultInfo) {
        return selectList("kr.co.hconnect.sqlmapper.selectBtList", searchResultInfo);
    }

    /**
     * 측정일자별 상세심박수 목록 조회
     *
     * @param searchResultInfo 측정결과 검색 조건
     * @return 측정일자별 상세심박수 목록
     */
    public List<HrResult> selectHrList(SearchResultInfo searchResultInfo) {
        return selectList("kr.co.hconnect.sqlmapper.selectHrList", searchResultInfo);
    }

    /**
     * 측정일자별 상세산소포화도 목록 조회
     *
     * @param searchResultInfo 측정결과 검색 조건
     * @return 측정일자별 상세산소포화도 목록
     */
    public List<SpO2Result> selectSpO2List(SearchResultInfo searchResultInfo) {
        return selectList("kr.co.hconnect.sqlmapper.selectSpO2List", searchResultInfo);
    }

    /**
     * 측정일자별 상세혈압 목록 조회
     *
     * @param searchResultInfos 측정결과 검색 조건
     * @return 측정일자별 상세혈압 목록
     */
    public List<BpResult> selectBpList(SearchResultInfos searchResultInfos) {
        return selectList("kr.co.hconnect.sqlmapper.selectBpList", searchResultInfos);
    }

    /**
     * 측정일자별 상세호흡 목록 조회
     * @param searchResultInfo 측정결과 검색 조건
     * @return 측정일자별 상세호흡 목록
     */
    public List<RrResult> selectRrList(SearchResultInfo searchResultInfo) {
        return selectList("kr.co.hconnect.sqlmapper.selectRrList", searchResultInfo);
    }

    /**
     * 측정일자별 상세걸음수 목록 조회
     *
     * @param searchResultInfos 측정결과 검색 조건
     * @return 측정일자별 상세걸음수 목록
     */
    public List<StepCountResult> selectStepList(SearchResultInfos searchResultInfos) {
        return selectList("kr.co.hconnect.sqlmapper.selectStepList", searchResultInfos);
    }

    /**
     * 측정일자별 상세수면 목록 조회
     *
     * @param searchSleepResultInfo 측정결과 검색 조건
     * @return 측정일자별 상세수면 목록
     */
    public List<SleepTimeResult> selectSleepTimeList(SearchSleepResultInfo searchSleepResultInfo) {
        return selectList("kr.co.hconnect.sqlmapper.selectSleepTimeList", searchSleepResultInfo);
    }

    /**
     * 일자별 문진 목록 조회
     *
     * @param searchResultInfo 문진검색 조건
     * @return 일자별 문진 목록
     */
    public List<InterviewResult> selectInterviewList(SearchResultInfo searchResultInfo) {
        return selectList("kr.co.hconnect.sqlmapper.selectInviewList", searchResultInfo);
    }

    /**
     * 일자별 문진 목록 조회
     *
     * @param searchResultInfo 문진검색 조건
     * @return 일자별 문진 목록
     */
    public List<DrugResult> selectDrugList(SearchResultInfo searchResultInfo) {
        return selectList("kr.co.hconnect.sqlmapper.selectDrugList", searchResultInfo);
    }

}
