package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 아이디 검색 조건 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchLoginIdInfo {

    /**
     * 성명
     */
    @NotNull
    private String patientNm;
    /**
     * 휴대폰
     */
    @NotNull
    private String cellPhone;
}
