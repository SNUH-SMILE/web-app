package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 혈압 측정결과
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BpResultDetail extends BaseResponse {

    private static final long serialVersionUID = 8570616428809449743L;

    /**
     * 혈압 측정결과 목록
     */
    private List<BpResult> bpList;
    
}
