package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 공통코드상세 조회조건 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ComCdDetailSearchVO implements Serializable {

    private static final long serialVersionUID = -4311384103878410635L;

    /**
     * 공통코드
     */
    @NotNull(message = "{validation.null.comCd}")
    private String comCd;
    /**
     * 세부코드
     */
    private String detailCd;
    /**
     * 사용여부
     */
    private String useYn;
    /**
     * 속성1
     */
    private String property1;
    /**
     * 속성2
     */
    private String property2;
    /**
     * 속성3
     */
    private String property3;
    /**
     * 속성4
     */
    private String property4;
    /**
     * 속성5
     */
    private String property5;
}
