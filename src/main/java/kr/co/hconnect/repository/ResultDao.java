package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.domain.Result;
import kr.co.hconnect.domain.ResultDetail;
import kr.co.hconnect.domain.SaveSleepTimeResult;
import kr.co.hconnect.vo.VitalResultVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 측정결과 Dao
 */
@Repository
public class ResultDao extends EgovAbstractMapper {

    /**
     * 최근 Vital 측정결과 조회
     * @param admissionId 격리/입소내역 ID
     * @return 최근 Vital 측정결과 정보
     */
    public VitalResultVO selectLastVitalResult(String admissionId) {
        return selectOne("kr.co.hconnect.sqlmapper.selectLastVitalResult", admissionId);
    }

    /**
     * 최근 Vital 측정결과 조회
     * @param list 격리/입소내역 ID List
     * @return List&lt;VitalResultVO&gt; 최근 Vital 측정결과 정보 리스트
     */
    public List<VitalResultVO> selectLastVitalResultByAdmissionIdList(List<String> list) {
        return selectList("kr.co.hconnect.sqlmapper.selectLastVitalResultByAdmissionIdList", list);
    }
    
    /**
     * 측정결과 저장
     *
     * @param result 측정결과
     * @return affectedRow
     */
    public int insertResult(Result result) {
        return insert("kr.co.hconnect.sqlmapper.insertResult", result);
    }

    /**
     * 측정결과 상세 저장
     *
     * @param resultDetail 측정결과 상세
     * @return affectedRow
     */
    public int insertResultDetail(ResultDetail resultDetail) {
        return insert("kr.co.hconnect.sqlmapper.insertResultDetail", resultDetail);
    }

    /**
     * 수면 측정결과 저장
     *
     * @param resultInfo 수면 측정결과 저장 정보
     * @return affectedRow
     */
    public int insertResultSleepTime(SaveSleepTimeResult resultInfo) {
        return insert("kr.co.hconnect.sqlmapper.insertResultSleepTime", resultInfo);
    }

}
