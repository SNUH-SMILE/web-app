package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 입소내역 정보 VO
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
	 * 환자명
	 */
	private String patientNm;
	/**
	 * 생년월일
	 */
	@JsonFormat(pattern = "yyyyMMdd")
	private LocalDate birthDate;
	/**
	 * 성별
	 */
	private String sex;
	/**
	 * 휴대폰
	 */
	private String cellPhone;
	/**
	 * 담당자
	 */
	private String personCharge;
	/**
	 * 시작일
	 */
	@JsonFormat(pattern = "yyyyMMdd")
	private LocalDate admissionDate;
	/**
	 * 종료예정일
	 */
	@JsonFormat(pattern = "yyyyMMdd")
	private LocalDate dschgeSchdldDate;
	/**
	 * 종료일
	 */
	@JsonFormat(pattern = "yyyyMMdd")
	private LocalDate dschgeDate;
	/**
	 * 센터ID
	 */
	private String centerId;
	/**
	 * 호실(위치)
	 */
	private String room;
	/**
	 * 격리/입소구분 - CD004
	 */
	private String qantnDiv;
	/**
	 * 삭제여부
	 */
	private String delYn;

    private String searsAccount;
}
