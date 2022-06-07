package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 공통코드상세 순서 변경 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ComCdDetailSortChangeVO extends BaseDefaultVO {

    private static final long serialVersionUID = -5683282231302157950L;

    /**
     * 공통코드
     */
    @NotBlank(message = "{validation.null.comCd}")
    private String comCd;
    /**
     * 세부코드
     */
    @NotBlank(message = "{validation.null.detailCd}")
    private String detailCd;
    /**
     * 정렬순서
     */
    @NotNull(message = "{validation.null.sortSeq}")
    @Max(value = 32767, message = "{validation.size.sortSeq.max}")
    @Min(value = 1, message = "{validation.size.sortSeq.min}")
    private int sortSeq;
}
