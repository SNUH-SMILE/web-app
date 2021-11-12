package kr.co.hconnect.vo;

import java.util.Date;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 입소내역
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdmissionVO extends BaseDefaultVO {

	private static final long serialVersionUID = 7561048307184224750L;

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
	 * 입소일자
	 */
	private Date admitDate;
	/**
	 * 퇴소일자
	 */
	private Date dischargeDate;
	/**
	 * 위치
	 */
	private String location;
	/**
	 * 병원코드
	 */
	private String hospitalCd;
	/**
	 * 병원환자ID
	 */
	private String hospitalPatientId;
	/**
	 * 리마크
	 */
	private String remark;
	
}
