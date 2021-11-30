package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.domain.Result;
import kr.co.hconnect.domain.ResultDetail;
import kr.co.hconnect.domain.ResultSavedInformationData;
import kr.co.hconnect.repository.ResultDao;
import kr.co.hconnect.vo.AdmissionVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class ResultService extends EgovAbstractServiceImpl {

    /**
     * 측정결과 Dao
     */
    private final ResultDao resultDao;

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
     * 측정결과 내역 저장
     *
     * @param resultSavedInformationDataList 측정결과 저장정보 데이터 리스트
     * @return boolean 저장성공여부
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
}
