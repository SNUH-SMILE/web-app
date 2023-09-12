package kr.co.hconnect.vo;

import kr.co.hconnect.domain.BaseResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PatientLocationResponseVO extends BaseResponse {

    private static final long serialVersionUID = -1389180997638576995L;
    /**
     * 위치정보 레포트 주기 (기본값 10분)
     */
    private static final long REPORT_LOCATION_INTERVAL = 1000 * 60 * 10;

    private String interval = String.valueOf(REPORT_LOCATION_INTERVAL);
}
