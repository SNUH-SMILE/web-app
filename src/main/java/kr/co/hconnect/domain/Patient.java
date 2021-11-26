package kr.co.hconnect.domain;

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
public class Patient {

    /**
     * 가입/수정 구분 (A : 가입, M : 수정)
     */
    @Pattern(regexp = "^[AM]$")
    private String flag;
    /**
     * 아이디
     */
    @NotNull
    private String loginId;
    /**
     * 비밀번호
     */
    @NotNull
    private String password;
    /**
     * 성명
     */
    @NotNull
    private String patientNm;
    /**
     * 주민번호
     */
    @NotNull
    private String ssn;
    /**
     * 생년월일
     */
    @NotNull
    private LocalDate birthDate;
    /**
     * 성별
     */
    @NotNull
    @Pattern(regexp = "^[MF]$")
    private String sex;
    /**
     * 휴대폰
     */
    @NotNull
    private String cellPhone;
    /**
     * 우편번호
     */
    private String zipCode;
    /**
     * 주소
     */
    private String address1;
    /**
     * 상세주소
     */
    private String address2;
}
