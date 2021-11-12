package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MeasurementCategoryVO extends BaseDefaultVO {

    /**
     * 항목ID (형식: I+일련번호(4))
     */
    private String itemId;

    /**
     * 항목명
     */
    private String itemNm;

    /**
     * 단위
     */
    private String unit;

}
