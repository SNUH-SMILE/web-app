package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * 메인 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MainContentDetail extends BaseResponse {

    /**
     * 성명
     */
    private String patientNm;
    /**
     * 격리시작일자
     */
    private LocalDate admissionDate;
    /**
     * 격리종료예정일자
     */
    private LocalDate dischargeScheduledDate;
    /**
     * 격리종료일자
     */
    private LocalDate dischargeDate;
    /**
     * 담당자
     */
    private String personCharge;
    /**
     * 당일 체온 목록
     */
    private List<BtResult> todayBtList;
    /**
     * 당일 혈압 목록
     */
    private List<BpResult> todayBpList;
    /**
     * 당일 심박수 목록
     */
    private List<HrResult> todayHrList;
    /**
     * 당일 산소포화도 목록
     */
    private List<SpO2Result> todaySpO2List;
    /**
     * 당일 걸음수 목록
     */
    private List<StepCountResult> todayStepCountList;
    /**
     * 당일 총수면시간
     */
    private LocalDate todayTotalSleepTime;
    /**
     * 당일 수면시간 목록
     */
    private List<SleepTimeResult> todaySleepTimeList;

}
