package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 혈압 측정결과 저장 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveBpResultInfo implements Serializable {

    private static final long serialVersionUID = -1415709398233866668L;

    /**
     * 아이디
     */
    private String loginId;
    /**
     * 혈압 측졍 결과
     */
    private List<BpResult> results;
}
