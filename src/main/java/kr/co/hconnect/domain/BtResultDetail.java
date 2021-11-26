package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 체온 측정결과
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BtResultDetail extends BaseResponse {

    /**
     * 체온 측정결과 목록
     */
    private List<BtResult> btList;
}
