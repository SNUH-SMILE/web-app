package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 체온 구성정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BtResult extends BaseResult {

    /**
     * 체온 측정결과
     */
    private String result;

}
