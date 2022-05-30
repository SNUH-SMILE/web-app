package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;

//생활치료센터
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TreatmentCenterVO extends BaseDefaultVO {

    private static final long serialVersionUID = -7673531673284572678L;
    /**
     * 생활치료센터 ID
     */
    private String centerId;
    /**
     * 생활치료센터명
     */
    private String centerNm;
    /**
     * 생활치료센터위치
     */
    private String centerLocation;
    /**
     * 생활치료센터 병원코드
     */
    private String hospitalCd;
    /**
     * 생활치료센터 병원이름
     */
    private String hospitalNm;
    /**
     * 사용여부
     */
    @Pattern(regexp = "^[YN]$")
    private String useYn;
}
