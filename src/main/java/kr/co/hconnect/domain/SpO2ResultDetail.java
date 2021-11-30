package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 산소포화도 측정 결과
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SpO2ResultDetail extends BaseResponse {

    private static final long serialVersionUID = 882837456396619808L;

    /**
     * 산소포화도 측정결과 목록
     */
    private List<SpO2Result> spO2List;
}
