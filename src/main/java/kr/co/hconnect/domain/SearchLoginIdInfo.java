package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

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
    @NotNull(message = "{validation.name.null}")
    private String patientNm;
    /**
     * 휴대폰
     */
    @NotNull(message = "{validation.cellphone.null}")
    @Pattern(regexp = "^[0-9]+$",message = "{validation.cellphone.patternMismatch}")
    private String cellPhone;
}
