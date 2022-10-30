package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 아이디 검색 조건 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchLoginIdInfo implements Serializable {

    private static final long serialVersionUID = 225855391158373717L;

    /**
     * 성명
     */
    @NotNull(message = "{validation.null.name}")
    private String patientNm;
    /**
     * 휴대폰
     */
    @NotNull(message = "{validation.null.cellphone}")
    @Pattern(regexp = "^[0-9]+$",message = "{validation.patternMismatch.cellphone}")
    private String cellPhone;
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
    @Pattern(regexp = "^[MF]$", message = "{validation.patternMismatch.sex}", groups = { PatientValidationGroups.add.class })
    private String sex;
}
