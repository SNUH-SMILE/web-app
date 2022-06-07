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
 * 공통코드
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ComCdVO extends BaseDefaultVO {

	private static final long serialVersionUID = -1700964762530457863L;

	/**
	 * cudFlag
	 */
	@Pattern(regexp = "^[CUD]$", message = "{validation.patternMismatch.cudFlag}")
	private String cudFlag;
	/**
	 * 공통코드 
	 */
	private String comCd;
	/**
	 * 공통코드명
	 */
	@NotBlank(message = "{validation.null.comCdNm}")
	@Size(max = 50, message = "{validation.size.comCdNm}")
	private String comCdNm;
	/**
	 * 공통코드구분
	 */
	@NotBlank(message = "{validation.null.comCdDiv}")
	private String comCdDiv;
	/**
	 * 사용여부 
	 */
	@Pattern(regexp = "^[YN]$", message = "{validation.patternMismatch.useYn}")
	private String useYn;
	/**
	 * 리마크
	 */
	@Size(max = 100, message = "{validation.size.comCdRemark}")
	private String remark;
	
}
