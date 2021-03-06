package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import kr.co.hconnect.common.VoValidationGroups;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ItemSaveVO extends BaseDefaultVO {

    private static final long serialVersionUID = 8324681977906421699L;

    /**
     * 측정항목 검색 정보
     */
    private ItemVO searchInfo;

    /**
     * 측정항목 ID
     */
    @NotBlank(message = "{validation.null.itemId}", groups = { VoValidationGroups.modify.class, VoValidationGroups.delete.class })
    private String itemId;

    /**
     * 측정항목명
     */
    @NotBlank(message = "{validation.null.itemNm}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    @Size(max = 50, message = "{validation.size.itemNm}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    private String itemNm;

    /**
     * 측정항목단위
     */
    @NotBlank(message = "{validation.null.itemUnit}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    @Size(max = 20, message = "{validation.size.itemUnit}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    private String unit;

    /**
     * 참고치 From
     */
    @NotNull(message = "{validation.null.itemRefValue}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    @Max(value = 32767, message = "{validation.size.itemRefMaxValue}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    @Min(value = -32767, message = "{validation.size.itemRefMinValue}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    private int refFrom;

    /**
     * 참고치 To
     */
    @NotNull(message = "{validation.null.itemRefValue}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    @Max(value = 32767, message = "{validation.size.itemRefMaxValue}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    @Min(value = -32767, message = "{validation.size.itemRefMinValue}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    private int refTo;

    /**
     * 삭제 여부
     */
    private String delYn;

}
