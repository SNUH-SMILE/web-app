package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 심박수 측정 결과
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HrResultDetail extends BaseResponse {

    private static final long serialVersionUID = 6852351119555954343L;

    /**
     * 심막수 측정결과 목록
     */
    private List<HrResult> hrList;
}
