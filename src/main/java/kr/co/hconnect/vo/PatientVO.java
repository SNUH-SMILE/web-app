package kr.co.hconnect.vo;

import java.util.Date;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 환자(자가격리자 및 생활치료센터 입소자)
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientVO extends BaseDefaultVO {

	private static final long serialVersionUID = 8089040921719440204L;
	
	/**
	 * 환자ID
	 */
	private String patientId;
	/**
	 * 이름
	 */
	private String name;
	/**
	 * 생년월일
	 */
	private Date birthDate;
	/**
	 * 연락처
	 */
	private String contact;
	/**
	 * 성별
	 */
	private String sex;
}
