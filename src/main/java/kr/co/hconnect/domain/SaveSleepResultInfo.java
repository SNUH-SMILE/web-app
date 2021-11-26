package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 수면 측정결과 저장 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveSleepResultInfo {

    /**
     * 아이디
     */
    private String loginId;
    /**
     * 수면 측정 결과
     */
    private List<SaveSleepTimeResult> results;
}
