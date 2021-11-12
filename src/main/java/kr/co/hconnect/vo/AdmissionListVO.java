package kr.co.hconnect.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 입소내역 리스트
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdmissionListVO implements Serializable {
	
	private static final long serialVersionUID = -655528968177770972L;

	/**
	 * 입소내역ID
	 */
	private String admissionId;
	/**
	 * 센터ID
	 */
	private String centerId;
	/**
	 * 병원코드
	 */
	private String hospitalCd;
	/**
	 * 병원환자ID
	 */
	private String hospitalPatientId;
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
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;
	/**
	 * 연락처
	 */
	private String contact;
	/**
	 * 성별_조회용
	 */
	private String dispSex;
	
}
