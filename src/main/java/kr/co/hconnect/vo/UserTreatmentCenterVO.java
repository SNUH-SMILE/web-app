package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import kr.co.hconnect.common.VoValidationGroups;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 사용자별 센터
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserTreatmentCenterVO extends BaseDefaultVO {
    
    private static final long serialVersionUID = -687280396523410428L;

    /**
     * 사용자ID
     */
    private String userId;
    /**
     * 센터ID
     */
    @NotBlank(message = "{validation.null.centerId}", groups = VoValidationGroups.add.class)
    private String centerId;
    /**
     * 센터명
     */
    private String centerNm;
    /**
     * 메인여부
     */
    @Pattern(regexp = "^[YN]$", message = "{validation.patternMismatch.mainYn}", groups = VoValidationGroups.add.class)
    private String mainYn;    
}
