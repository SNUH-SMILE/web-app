package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 환자별 장비 저장 완료 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientDeviceSavedInfo extends BaseResponse {

    private static final long serialVersionUID = 6125666943597432709L;

    /**
     * 장비 사용 이력
     */
    private List<PatientDeviceUseHistory> deviceUseHistoryList;

}
