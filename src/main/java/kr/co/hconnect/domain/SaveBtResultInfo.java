package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 체온 측정 결과 저장 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveBtResultInfo {

    /**
     * 아이디
     */
    private String loginId;
    /**
     * 체온 측정 결과
     */
    private List<BtResult> results;

}
