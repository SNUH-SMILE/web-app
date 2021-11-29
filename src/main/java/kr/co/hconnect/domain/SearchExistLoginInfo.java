package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 개인정보 확인 검색 조건
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchExistLoginInfo {

    /**
     * 아이디
     */
    @NotNull
    private String loginId;
    /**
     * 성명
     */
    @NotNull
    private String patientNm;
    /**
     * 휴대폰
     */
    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String cellPhone;
}
