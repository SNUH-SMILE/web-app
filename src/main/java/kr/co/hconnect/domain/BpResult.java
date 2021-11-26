package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 혈압 구성정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BpResult extends BaseResult {

    /**
     * 최저혈압 측정결과
     */
    private String resultDbp;
    /**
     * 최고혈압 측정결과
     */
    private String resultSbp;
    
}
