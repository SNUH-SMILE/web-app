package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 산소포화도 측정결과 저장 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveSpO2ResultInfo implements Serializable {

    private static final long serialVersionUID = -7475744117489798364L;

    /**
     * 아이디
     */
    private String loginId;
    /**
     * 산소포화도 측정 결과
     */
    private List<SpO2Result> results;
}
