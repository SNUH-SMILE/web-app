package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 심박수 구성정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HrResult extends BaseResult {

    private static final long serialVersionUID = 682236620960989258L;

    /**
     * 심박수 측정결과
     */
    private String result;
    
}
