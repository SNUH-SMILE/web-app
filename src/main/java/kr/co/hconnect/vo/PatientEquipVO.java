package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 환자별 사용장비
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientEquipVO extends BaseDefaultVO {
	
	private static final long serialVersionUID = -8066691331949367304L;
	
	/**
	 * 입소내역ID
	 */
	private String admissionId;
	/**
	 * 환자ID
	 */
	private String patientId;
	/**
	 * 이름
	 */
	private String name;
	/**
	 * 장비ID
	 */
	private String equipId;
	/**
	 * 장비명
	 */
    private String equipNm;
}
