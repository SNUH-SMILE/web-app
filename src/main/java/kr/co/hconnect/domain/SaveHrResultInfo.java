package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 심박수 측정결과 저장 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveHrResultInfo {

    /**
     * 아이디
     */
    private String loginId;
    /**
     * 심박수 측정결과
     */
    private List<HrResult> results;
}
