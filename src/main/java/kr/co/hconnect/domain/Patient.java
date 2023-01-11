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
import javax.validation.constraints.Size;
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
    @NotNull(message = "{validation.null.loginId}", groups = { PatientValidationGroups.add.class, PatientValidationGroups.modify.class })
    @Size(max = 20, message = "{validation.size.loginId}", groups = { PatientValidationGroups.add.class, PatientValidationGroups.modify.class })
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String loginId;
    /**
     * 비밀번호
     */
    @NotNull(message = "{validation.null.password}", groups = { PatientValidationGroups.add.class })
    @Size(max = 20, message = "{validation.size.password}", groups = { PatientValidationGroups.add.class })
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    /**
     * 성명
     */
    @NotNull(message = "{validation.null.name}", groups = { PatientValidationGroups.add.class })
    @Size(max = 50, message = "{validation.size.patientNm}", groups = { PatientValidationGroups.add.class })
    private String patientNm;
    /**
     * 생년월일
     */
    @NotNull(message = "{validation.null.birthday}", groups = { PatientValidationGroups.add.class })
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate birthDate;
    /**
     * 성별
     */
    @NotNull(message = "{validation.null.sex}", groups = { PatientValidationGroups.add.class })
    @Pattern(regexp = "^[MF]$", message = "{validation.patternMismatch.sex}", groups = { PatientValidationGroups.add.class })
    private String sex;
    /**
     * 휴대폰
     */
    @NotNull(message = "{validation.null.cellphone}", groups = { PatientValidationGroups.add.class, PatientValidationGroups.modify.class })
    @Pattern(regexp = "^[0-9]+$",message = "{validation.patternMismatch.cellphone}", groups = { PatientValidationGroups.add.class, PatientValidationGroups.modify.class })
    @Size(max = 15, message = "{validation.size.cellPhone}", groups = { PatientValidationGroups.add.class, PatientValidationGroups.modify.class })
    private String cellPhone;
    /**
     * 보호자연락처
     */
    @NotNull(message = "{validation.null.guardianCellPhone}", groups = { PatientValidationGroups.add.class, PatientValidationGroups.modify.class })
    @Pattern(regexp = "^[0-9]+$",message = "{validation.patternMismatch.guardianCellPhone}", groups = { PatientValidationGroups.add.class, PatientValidationGroups.modify.class })
    @Size(max = 15, message = "{validation.size.guardianCellPhone}", groups = { PatientValidationGroups.add.class, PatientValidationGroups.modify.class })
    private String guardianCellPhone;
    /**
     * 우편번호
     */
    @NotNull(message = "{validation.null.zipcode}", groups = { PatientValidationGroups.add.class, PatientValidationGroups.modify.class })
    @Size(max = 6, message = "{validation.size.zipCode}", groups = { PatientValidationGroups.add.class, PatientValidationGroups.modify.class })
    private String zipCode;
    /**
     * 주소
     */
    @NotNull(message = "{validation.null.address}", groups = { PatientValidationGroups.add.class, PatientValidationGroups.modify.class })
    @Size(max = 200, message = "{validation.size.address1}", groups = { PatientValidationGroups.add.class, PatientValidationGroups.modify.class })
    private String address1;
    /**
     * 상세주소
     */
    @NotNull(message = "{validation.null.addressDetail}", groups = { PatientValidationGroups.add.class, PatientValidationGroups.modify.class })
    @Size(max = 200, message = "{validation.size.address2}", groups = { PatientValidationGroups.add.class, PatientValidationGroups.modify.class })
    private String address2;

    /**
     * 시어스 체온 로그인 계정
     */
    @Size(max = 50, message = "validation.size.searsAccount", groups = { PatientValidationGroups.add.class, PatientValidationGroups.modify.class })
    private String searsAccount;
}
