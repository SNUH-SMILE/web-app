package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.domain.*;
import kr.co.hconnect.repository.MeasurementResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementResultService extends EgovAbstractServiceImpl {
    /**
     * 메인 Dao
     */
    private final MeasurementResultDao dao;

    /**
     * 생성자
     * @param dao MeasurementResultDao
     */
    @Autowired
    public MeasurementResultService(MeasurementResultDao dao) {
        this.dao = dao;
    }

    /**
     * 신규 알림 여부조회
     * @param admissionId 입소자 Id
     * @return 읽지않은 공지사항 갯수
     */
    public int unReadNotice(String admissionId) {
        return dao.selectUnreadNotice(admissionId);
    }

    /**
     * 측정일자별 상세체온 목록 조회
     *
     * @param searchResultInfo 측정결과 검색 조건
     * @return 측정일자별 상세체온목록
     */
    public List<BtResult> selectBpLIst(SearchResultInfo searchResultInfo) {
        return dao.selectBtList(searchResultInfo);
    }

    /**
     * 측정일자별 상세심박수 목록 조회
     *
     * @param searchResultInfo 측정결과 검색 조건
     * @return 측정일자별 상세심박수 목록
     */
    public List<HrResult> selectHrList(SearchResultInfo searchResultInfo) {
        return dao.selectHrList(searchResultInfo);
    }

    /**
     * 측정일자별 상세산소포화도 목록 조회
     *
     * @param searchResultInfo 측정결과 검색 조건
     * @return 측정일자별 상세산소포화도 목록
     */
    public List<SpO2Result> selectSpO2List(SearchResultInfo searchResultInfo) {
        return dao.selectSpO2List(searchResultInfo);
    }


    /**
     * 측정일자별 상세혈압 목록 조회
     *
     * @param searchResultInfos 측정결과 검색 조건
     * @return 측정일자별 상세혈압 목록
     */
    public List<BpResult> selectBpList(SearchResultInfos searchResultInfos) {
        return dao.selectBpList(searchResultInfos);
    }

    /**
     * 측정일자별 상세걸음수 목록 조회
     *
     * @param searchResultInfos 측정결과 검색 조건
     * @return 측정일자별 상세걸음수 목록
     */
    public List<StepCountResult> selectStepList(SearchResultInfos searchResultInfos) {
        return dao.selectStepList(searchResultInfos);
    }

    /**
     * 측정일자별 상세수면 목록 조회
     *
     * @param searchSleepResultInfo 측정결과 검색 조건
     * @return 측정일자별 상세수면 목록
     */
    public List<SleepTimeResult> selectSleepTimeList(SearchSleepResultInfo searchSleepResultInfo) {
        return dao.selectSleepTimeList(searchSleepResultInfo);
    }
}
