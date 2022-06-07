package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 공통코드 조회조건 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ComCdSearchVO implements Serializable {

    private static final long serialVersionUID = 803454040887352971L;

    /**
     * 공통코드
     */
    private String comCd;
    /**
     * 공통코드명
     */
    private String comCdNm;
    /**
     * 사용여부
     */
    private String useYn;

}
