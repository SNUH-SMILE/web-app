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
public class UserVO extends BaseDefaultVO {
	
	private static final long serialVersionUID = -2967100267485390392L;
	
	/**
	 * 사용자ID
	 */
	private String userId;
	/**
	 * 비밀번호
	 */
	private String password;
	/**
	 * 사용자명
	 */
	private String userNm;
	/**
	 * 센터ID
	 */
	private String centerId;
	/**
	 * 리마크
	 */
	private String remark;
	/**
	 * 센터명
	 */
	private String centerNm;
	/**
	 * 로그인 유지 여부 
	 */
	private String rememberYn;
	
}
