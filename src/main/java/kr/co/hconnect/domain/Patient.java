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
    @Pattern(regexp = "^[AM]$", message = "가입/수정 구분을 확인하세요.")
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
    @NotNull(message = "아이디가 누락되었습니다.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String loginId;
    /**
     * 비밀번호
     */
    @NotNull(message = "비밀번호가 누락되었습니다.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    /**
     * 성명
     */
    @NotNull(message = "성명이 누락되었습니다.")
    private String patientNm;
    /**
     * 주민번호
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String ssn;
    /**
     * 생년월일
     */
    @NotNull(message = "생년월일이 누락되었습니다.")
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate birthDate;
    /**
     * 성별
     */
    @NotNull(message = "성별이 누락되었습니다.")
    @Pattern(regexp = "^[MF]$", message = "성별을 확인하세요.")
    private String sex;
    /**
     * 휴대폰
     */
    @NotNull(message = "휴대폰 번호가 누락되었습니다.")
    @Pattern(regexp = "^[0-9]+$")
    private String cellPhone;
    /**
     * 우편번호
     */
    @NotNull(message = "우편번호가 누락되었습니다.")
    private String zipCode;
    /**
     * 주소
     */
    @NotNull(message = "주소가 누락되었습니다.")
    private String address1;
    /**
     * 상세주소
     */
    @NotNull(message = "상세주소가 누락되었습니다.")
    private String address2;
}
