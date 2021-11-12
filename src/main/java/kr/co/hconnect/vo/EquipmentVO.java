package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EquipmentVO extends BaseDefaultVO {

	private static final long serialVersionUID = -172975321685033042L;
	//장비Id
	private String equipId;
	//장비명
	private String equipNm;
	//센터Id
	private String centerId;
	//센터명
	private String centerNm;
	//리마크
	private String remark;
}
