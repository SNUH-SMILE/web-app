package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 개인정보 존재여부 결과
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExistResult extends BaseResponse {

    /**
     * 존재여부 (Y : 존재, N : 존재하지 않음)
     */
    private String existYn;
}
