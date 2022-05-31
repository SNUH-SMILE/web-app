package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 공통코드 상세 내역 리스트
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Deprecated
public class ComCdDetailListVO implements Serializable {

	private static final long serialVersionUID = -2392991796721941806L;

	/**
	 * 공통코드
	 */
	private String comCd;
	/**
	 * 공통코드명
	 */
	private String comCdNm;
	/**
	 * 공통코드구분
	 */
	private String comCdDiv;
	/**
	 * 공통코드 리마크
	 */
	private String comCdRemark;
	/**
	 * 세부코드
	 */
	private String detailCd;
	/**
	 * 세부코드명
	 */
	private String detailCdNm;
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
