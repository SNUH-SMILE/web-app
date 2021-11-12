package kr.co.hconnect.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 환자별 사용장비 저장정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientEquipSaveVO implements Serializable {
	
	private static final long serialVersionUID = -4193552474657963654L;

	/**
	 * 입소내역ID
	 */
	private String admissionId;
	/**
	 * 센터ID
	 */
	private String centerId;
	/**
	 * 환자ID
	 */
	private String patientId; 
	/**
	 * 환자별 사용장비 VO
	 */
	private List<PatientEquipVO> patientEquipVOs;
	
}
