package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
	 * 공통코드 
	 */
	private String comCd;
	/**
	 * 공통코드명
	 */
	private String comCdNm;
	/**
	 * 사용여부 
	 */
	private String useYn;
	/**
	 * 공통코드구분
	 */
	private String comCdDiv;
	/**
	 * 리마크
	 */
	private String remark;
	
}
