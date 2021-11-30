package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 측정결과 저장 정보 데이터
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResultSavedInformationData implements Serializable {

    private static final long serialVersionUID = 4384984791871742016L;

    /**
     * 로그인ID
     */
    private String loginId;
    /**
     * 측정결과
     */
    private Result result;
    /**
     * 측정결과 세부
     */
    private List<ResultDetail> resultDetails;

}
