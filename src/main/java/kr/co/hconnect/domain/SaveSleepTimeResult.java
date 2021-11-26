package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 수면 구성정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveSleepTimeResult {

    /**
     * 측정시작일자
     */
    private LocalDate resultStartDate;
    /**
     * 측정시작시간
     */
    private LocalTime resultStartTime;
    /**
     * 측정종료일자
     */
    private LocalDate resultEndDate;
    /**
     * 측정종료시간
     */
    private LocalTime resultEndTime;
    /**
     * 수면유형
     */
    private String sleepType;
    /**
     * 디바이스ID
     */
    private String deviceId;
}
