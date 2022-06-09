package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

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
	@NotBlank(message = "{validation.null.password}")
	@Size(max = 20, message = "{validation.size.password}")
	private String password;
	/**
	 * 사용자명
	 */
	@NotBlank(message = "{validation.null.userNm}")
	@Size(max = 50, message = "{validation.size.userNm}")
	private String userNm;
	/**
	 * 메인 센터ID
	 */
	private String mainCenterId;
	/**
	 * 메인 센터명
	 */
	private String mainCenterNm;
	/**
	 * 리마크
	 */
	@Size(max = 100, message = "{validation.size.userRemark}")
	private String remark;
	/**
	 * 로그인 유지 여부 
	 */
	private String rememberYn;
	/**
	 * 사용자별 센터 정보
	 */
	private List<UserTreatmentCenterVO> userTreatmentCenterVOList;
	
}
