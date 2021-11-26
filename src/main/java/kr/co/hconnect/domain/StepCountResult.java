package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 걸음수 구성정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StepCountResult extends BaseResult {

    /**
     * 걸음수 측정결과
     */
    private String resultStepCount;
    /**
     * 거리 측정결과
     */
    private String resultDistance;
}
