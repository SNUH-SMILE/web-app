package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * 환자
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Patient extends BaseResponse {

    private static final long serialVersionUID = 6789973998668288818L;

    /**
     * 가입/수정 구분 (A : 가입, M : 수정)
     */
    @NotNull(message = "가입/수정 구분이 누락되었습니다.")
    @Pattern(regexp = "^[AM]$", message = "{validation.flag.patternMismatch}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String flag;
    /**
     * 환자ID
     */
    @JsonIgnore
    private String patientId;
    /**
     * 아이디
     */
    @NotNull(message = "{validation.loginId.null}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String loginId;
    /**
     * 비밀번호
     */
    @NotNull(message = "{validation.password.null}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    /**
     * 성명
     */
    @NotNull(message = "{validation.name.null}")
    private String patientNm;
    /**
     * 주민번호
     */
    @NotNull(message = "{validation.ssn.null}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String ssn;
    /**
     * 생년월일
     */
    @NotNull(message = "{validation.birthday.null}")
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate birthDate;
    /**
     * 성별
     */
    @NotNull(message = "{validation.sex.null}")
    @Pattern(regexp = "^[MF]$", message = "{validation.sex.patternMismatch}")
    private String sex;
    /**
     * 휴대폰
     */
    @NotNull(message = "{validation.cellphone.null}")
    @Pattern(regexp = "^[0-9]+$",message = "{validation.cellphone.patternMismatch}")
    private String cellPhone;
    /**
     * 우편번호
     */
    @NotNull(message = "{validation.zipcode.null}")
    private String zipCode;
    /**
     * 주소
     */
    @NotNull(message = "{validation.address.null}")
    private String address1;
    /**
     * 상세주소
     */
    @NotNull(message = "{validation.addressDetail.null}")
    private String address2;
}
