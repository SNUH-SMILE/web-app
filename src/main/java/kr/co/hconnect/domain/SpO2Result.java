package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 산소포화도 구성정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SpO2Result extends BaseResult {

    /**
     * 산소포화도 측정결과
     */
    private String result;
    
}
