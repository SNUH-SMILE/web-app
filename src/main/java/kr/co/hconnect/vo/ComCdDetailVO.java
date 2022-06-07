package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 공통코드상세
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ComCdDetailVO extends BaseDefaultVO {

	private static final long serialVersionUID = 8684333858331402389L;

	/**
	 * cudFlag
	 */
	@Pattern(regexp = "^[CUD]$", message = "{validation.patternMismatch.cudFlag}")
	private String cudFlag;
	/**
	 * 공통코드
	 */
	@NotBlank(message = "{validation.null.comCd}")
	private String comCd;
	/**
	 * 세부코드
	 */
	@NotBlank(message = "{validation.null.detailCd}")
	@Size(max = 20, message = "{validation.size.detailCd}")
	private String detailCd;
	/**
	 * 세부코드명
	 */
	@NotBlank(message = "{validation.null.detailCdNm}")
	@Size(max = 50, message = "{validation.size.detailCdNm}")
	private String detailCdNm;
	/**
	 * 정렬순서
	 */
	private int sortSeq;
	/**
	 * 사용여부
	 */
	@Pattern(regexp = "^[YN]$", message = "{validation.patternMismatch.useYn}")
	private String useYn;
	/**
	 * 속성1
	 */
	@Size(max = 50, message = "{validation.size.property}")
	private String property1;
	/**
	 * 속성2
	 */
	@Size(max = 50, message = "{validation.size.property}")
	private String property2;
	/**
	 * 속성3
	 */
	@Size(max = 50, message = "{validation.size.property}")
	private String property3;
	/**
	 * 속성4
	 */
	@Size(max = 50, message = "{validation.size.property}")
	private String property4;
	/**
	 * 속성5
	 */
	@Size(max = 50, message = "{validation.size.property}")
	private String property5;
	/**
	 * 리마크
	 */
	@Size(max = 100, message = "{validation.size.comCdDetailRemark}")
	private String remark;
}
