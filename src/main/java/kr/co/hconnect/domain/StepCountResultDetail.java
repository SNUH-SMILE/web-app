package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 걸음수 측정결과
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StepCountResultDetail extends BaseResponse {

    private static final long serialVersionUID = 4495282342621565117L;

    /**
     * 걸음수 측정결과 목록
     */
    private List<StepCountResult> stepCountList;
}
