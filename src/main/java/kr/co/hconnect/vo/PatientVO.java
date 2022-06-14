package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * 환자(자가격리자 및 생활치료센터 입소자) VO
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
	 * 아이디
	 */
	@NotNull(message = "{validation.null.loginId}")
	@Size(max = 20, message = "{validation.size.loginId}")
	private String loginId;
	/**
	 * 비밀번호
	 */
	@NotNull(message = "{validation.null.password}")
	@Size(max = 20, message = "{validation.size.password}")
	private String password;
	/**
	 * 성명
	 */
	@NotNull(message = "{validation.null.name}")
	@Size(max = 50, message = "{validation.size.patientNm}")
	private String patientNm;
	/**
	 * 생년월일
	 */
	@NotNull(message = "{validation.null.birthday}")
	@JsonFormat(pattern = "yyyyMMdd")
	private LocalDate birthDate;
	/**
	 * 성별
	 */
	@NotNull(message = "{validation.null.sex}")
	@Pattern(regexp = "^[MF]$", message = "{validation.patternMismatch.sex}")
	private String sex;
	/**
	 * 휴대폰
	 */
	@NotNull(message = "{validation.null.cellphone}")
	@Pattern(regexp = "^[0-9]+$",message = "{validation.patternMismatch.cellphone}")
	@Size(max = 15, message = "{validation.size.cellPhone}")
	private String cellPhone;
	/**
	 * 보호자연락처
	 */
	@NotNull(message = "{validation.null.guardianCellPhone}")
	@Pattern(regexp = "^[0-9]+$",message = "{validation.patternMismatch.guardianCellPhone}")
	@Size(max = 15, message = "{validation.size.guardianCellPhone}")
	private String guardianCellPhone;
	/**
	 * 우편번호
	 */
	@NotNull(message = "{validation.null.zipcode}")
	@Size(max = 6, message = "{validation.size.zipCode}")
	private String zipCode;
	/**
	 * 주소
	 */
	@NotNull(message = "{validation.null.address}")
	@Size(max = 200, message = "{validation.size.address1}")
	private String address1;
	/**
	 * 상세주소
	 */
	@NotNull(message = "{validation.null.addressDetail}")
	@Size(max = 200, message = "{validation.size.address2}")
	private String address2;
}
