package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 전체 측정결과 저장 정보 데이터
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResultTotalSavedInformationData implements Serializable {

    private static final long serialVersionUID = -5176242558756726665L;

    /**
     * VitalSign 측정결과 전체 저장 정보
     */
    private List<ResultSavedInformationData> resultSavedInformationDataList;
    /**
     * 수면 정보 측정결과 저장 정보
     */
    private SaveSleepResultInfo saveSleepResultInfo;

    /**
     * 저장정보 컨텐츠 요소 확인
     */
    public Boolean isEmpty() {
        return (resultSavedInformationDataList == null || resultSavedInformationDataList.isEmpty())
            && (saveSleepResultInfo == null || saveSleepResultInfo.getResults().isEmpty());
    }
}
