package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 수면시간 측정결과 상세
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SleepTimeResultDetail extends BaseResponse {

    /**
     * 총수면시간
     */
    private int totalSleepTime;
    /**
     * 측정시작일시
     */
    private LocalDateTime resultStartDateTime;
    /**
     * 측정종료일시
     */
    private LocalDateTime resultEndDateTime;
    /**
     * 수면시간 측정결과 목록
     */
    private List<SleepTimeResult> sleepTimeList;
}
