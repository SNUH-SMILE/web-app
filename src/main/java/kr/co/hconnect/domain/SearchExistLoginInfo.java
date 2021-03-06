package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 개인정보 확인 검색 조건
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchExistLoginInfo implements Serializable {

    private static final long serialVersionUID = -2030959890401745340L;

    /**
     * 아이디
     */
    @NotNull(message = "{validation.null.loginId}")
    private String loginId;
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
}
