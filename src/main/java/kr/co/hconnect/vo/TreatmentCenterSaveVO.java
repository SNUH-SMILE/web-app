package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import kr.co.hconnect.common.VoValidationGroups;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 생활치료센터 저장 정보 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TreatmentCenterSaveVO extends BaseDefaultVO {

    private static final long serialVersionUID = 45946373362499273L;

    /**
     * 생활치료센터 검색 정보
     */
    private TreatmentCenterVO searchInfo;

    /**
     * 생활치료센터 ID
     */
    @NotNull(message = "{validation.null.centerId}", groups = { VoValidationGroups.modify.class, VoValidationGroups.delete.class })
    private String centerId;
    /**
     * 생활치료센터명
     */
    @NotNull(message = "{validation.null.centerNm}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    @Size(max = 20, message = "{validation.size.centerNm}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    private String centerNm;
    /**
     * 생활치료센터위치
     */
    @NotNull(message = "{validation.null.centerLocation}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    @Size(max = 500, message = "{validation.size.centerLocation}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    private String centerLocation;
    /**
     * 생활치료센터 병원코드
     */
    @NotNull(message = "{validation.null.hospitalCd}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    private String hospitalCd;
}
