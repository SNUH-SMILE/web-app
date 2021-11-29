package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @Pattern(regexp = "^[AM]$", message = "가입/수정 구분을 확인하세요.")
    private String flag;
    /**
     * 환자ID
     */
    private String patientId;
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
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate birthDate;
    /**
     * 성별
     */
    @NotNull
    @Pattern(regexp = "^[MF]$", message = "성별을 확인하세요.")
    private String sex;
    /**
     * 휴대폰
     */
    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String cellPhone;
    /**
     * 우편번호
     */
    @NotNull
    private String zipCode;
    /**
     * 주소
     */
    @NotNull
    private String address1;
    /**
     * 상세주소
     */
    @NotNull
    private String address2;
}
