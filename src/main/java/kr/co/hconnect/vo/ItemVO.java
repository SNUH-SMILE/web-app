package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 측정항목
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ItemVO extends BaseDefaultVO {


	private static final long serialVersionUID = -905259945895557269L;
    /**
     * 측정항목 ID
     */
	private String itemId;

    /**
     * 측정항목명
     */
	private String itemNm;

    /**
     * 측정항목단위
     */
	private String unit;

    /**
     * 참고치 From
     */
    private String refFrom;

    /**
     * 참고치 To
     */
    private String refTo;

    /**
     * 삭제 여부
     */
    private String delYn;

}
