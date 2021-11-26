package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

/**
 * 수면시간 측정결과
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SleepTimeResult {

    /**
     * 수면타입 (0 : 기상, 1 : 얕은잠, 2 : 깊은잠)
     */
    private String sleepType;
    /**
     * 수면 시작시각
     */
    private LocalTime sleepStartTime;
    /**
     * 수면 종료시각
     */
    private LocalTime sleepEndTime;
}
