package kr.co.hconnect.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 로그인 정보 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginVO implements Serializable {

	private static final long serialVersionUID = 7340698795654276903L;

	/**
	 * 사용자VO
	 */
	private UserVO userVO;
	/**
	 * 로그인 실패 사유
	 */
	private String failMessage;

}
