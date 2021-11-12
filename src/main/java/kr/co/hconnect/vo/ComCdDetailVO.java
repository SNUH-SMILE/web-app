package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
	 * 공통코드
	 */
	private String comCd;
	/**
	 * 세부코드
	 */
	private String detailCd;
	/**
	 * 세부코드명
	 */
	private String detailCdNm;
	/**
	 * 정렬순서
	 */
	private int sortSeq;
	/**
	 * 속성1
	 */
	private String property1;
	/**
	 * 속성2
	 */
	private String property2;
	/**
	 * 속성3
	 */
	private String property3;
	/**
	 * 사용여부
	 */
	private String useYn;
	/**
	 * 리마크
	 */
	private String remark;
}
