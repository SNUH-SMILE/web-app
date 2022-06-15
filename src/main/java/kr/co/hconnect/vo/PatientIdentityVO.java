package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 환자 본인인증 정보 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientIdentityVO implements Serializable {

    private static final long serialVersionUID = 1545352823532494736L;

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
}
