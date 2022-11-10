package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * 메인 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MainContentDetail extends BaseResponse {

    private static final long serialVersionUID = -5442871090266382740L;

    /**
     * 성명
     */
    private String patientNm;
    /**
     * 격리시작일자
     */
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate admissionDate;
    /**
     * 격리종료예정일자
     */
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate dischargeScheduledDate;
    /**
     * 격리종료일자
     */
    @JsonFormat(pattern = "yyyyMMdd")
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
     * 당일 호흡 목록
     */
    private List<RrResult> todayRrList;
    /**
     * 당일 총수면시간
     */
    @JsonFormat(pattern = "HHmm")
    private LocalTime todayTotalSleepTime;
    /**
     * 당일 수면시간 목록
     */
    private List<SleepTimeResult> todaySleepTimeList;

}
