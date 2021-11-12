package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 환자별 사용장비 리스트
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientEquipListVO extends BaseDefaultVO {
	
	private static final long serialVersionUID = -5052634052198033868L;

	/**
	 * 입소내역ID
	 */
	private String admissionId;
	/**
	 * 환자ID
	 */
	private String patientId;
	/**
	 * 센터ID
	 */
	private String centerId;
	/**
	 * 이름
	 */
	private String name;
	/**
	 * 장비내역
	 */
	private String equipListNm;
}
