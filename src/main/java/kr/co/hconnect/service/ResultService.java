package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.domain.*;
import kr.co.hconnect.repository.ResultDao;
import kr.co.hconnect.vo.AdmissionVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 측정결과 Service
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class ResultService extends EgovAbstractServiceImpl {

    /**
     * 측정결과 Dao
     */
    private final ResultDao resultDao;

    /**
     * 격리/입소내역 관리 Service
     */
    private final AdmissionService admissionService;

    /**
     * 생성자
     *
     * @param resultDao        측정결과 Dao
     * @param admissionService 격리/입소내역 관리 Service
     */
    @Autowired
    public ResultService(ResultDao resultDao, AdmissionService admissionService) {
        this.resultDao = resultDao;
        this.admissionService = admissionService;
    }

    /**
     * 전체 측정결과 저장
     * @param resultTotalSavedInformationData 전체 측정결과 저장 정보 데이터
     * @return boolean 저장성공여부
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveTotalResult(ResultTotalSavedInformationData resultTotalSavedInformationData) {
        int affectedRow = 0;

        // vitalSign 저장
        if (resultTotalSavedInformationData.getResultSavedInformationDataList() != null &&
            !resultTotalSavedInformationData.getResultSavedInformationDataList().isEmpty()) {
            saveResult(resultTotalSavedInformationData.getResultSavedInformationDataList());
        }
        // 수면 측정결과 저장
        if (resultTotalSavedInformationData.getSaveSleepResultInfo() != null &&
            !resultTotalSavedInformationData.getSaveSleepResultInfo().getResults().isEmpty()) {
            saveResultSleep(resultTotalSavedInformationData.getSaveSleepResultInfo());
        }

        return true;
    }

    /**
     * 측정결과 내역 저장
     *
     * @param resultSavedInformationDataList 측정결과 저장정보 데이터 리스트
     * @return affectedRow
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveResult(List<ResultSavedInformationData> resultSavedInformationDataList) {
        // 로그인 ID
        String loginId = null;
        // 격리/입소내역ID
        String admissionId = null;

        // 측정결과 저장
        for (ResultSavedInformationData data : resultSavedInformationDataList) {
            if (StringUtils.isEmpty(admissionId) || StringUtils.isEmpty(loginId) || !loginId.equals(data.getLoginId())) {
                loginId = data.getLoginId();
                // 격리/입소내역 내원정보 조회
                AdmissionVO admissionVO = admissionService.selectActiveAdmissionByLoginId(loginId);
                admissionId = admissionVO.getAdmissionId();
            }

            Result result = data.getResult();

            // 측정결과 저장
            result.setAdmissionId(admissionId);
            resultDao.insertResult(result);

            // 측정결과 상세 저장
            for (ResultDetail resultDetail : data.getResultDetails()) {
                resultDetail.setResultSeq(result.getResultSeq());
                resultDao.insertResultDetail(resultDetail);
            }
        }

        return true;
    }

    /**
     * 수면 측정결과 내역 저장
     *
     * @param resultInfo 수면 측정결과 저장 정보
     * @return boolean 저장성공여부
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveResultSleep(SaveSleepResultInfo resultInfo) {
        // 격리/입소내역ID
        String admissionId = admissionService.selectActiveAdmissionByLoginId(resultInfo.getLoginId()).getAdmissionId();
        String sleepkey = resultInfo.getSleepListKey();
        // 수면 측정결과 저장
        for (SaveSleepTimeResult data : resultInfo.getResults()) {
            data.setAdmissionId(admissionId);
            data.setSleepListKey(sleepkey);   //insert key value
            resultDao.insertResultSleepTime(data);
        }

        return true;
    }

    /**
     * 수면 측정결과 내역 학제
     *
     * @param resultInfo 수면 측정결과 저장 정보
     * @return boolean 저장성공여부
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteResultSleep(SaveSleepResultInfo resultInfo) {
        // 격리/입소내역ID
        String admissionId = admissionService.selectActiveAdmissionByLoginId(resultInfo.getLoginId()).getAdmissionId();
        String sleepkey = resultInfo.getDeleteSleepListKey();
        // 수면 측정결과 삭제
        for (SaveSleepTimeResult data : resultInfo.getResults()) {
            data.setAdmissionId(admissionId);
            data.setDeleteSleepListKey(sleepkey);  // delete key value
            resultDao.deleteResultSleepTime(data);
        }

        return true;
    }

}
