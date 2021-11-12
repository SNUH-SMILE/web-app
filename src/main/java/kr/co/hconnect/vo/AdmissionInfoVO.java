package kr.co.hconnect.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 입소내역 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdmissionInfoVO extends BaseDefaultVO {
	
	private static final long serialVersionUID = 5831529643034808242L;

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
	 * 센터명
	 */
	private String centerNm;
	/**
	 * 입소일자
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date admitDate;
	/**
	 * 퇴소일자
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dischargeDate;
	/**
	 * 위치
	 */
	private String location;
	/**
	 * 위치명
	 */
	private String locationNm;
	/**
	 * 병원환자ID
	 */
	private String hospitalPatientId;
	/**
	 * 리마크
	 */
	private String remark;
	
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
	 * 성별
	 */
	private String sex;
	

	
}
