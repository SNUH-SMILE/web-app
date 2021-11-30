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

    private static final long serialVersionUID = -5344118631588677759L;

    /**
     * 체온 측정결과
     */
    private String result;

}
