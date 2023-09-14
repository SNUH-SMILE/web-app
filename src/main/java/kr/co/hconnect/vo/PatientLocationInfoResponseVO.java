package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.hconnect.domain.BaseResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class PatientLocationInfoResponseVO extends BaseResponse {

    private static final long serialVersionUID = -7489587374429705768L;

    /**
     * 환자ID
     */
    private String patientId;
    /**
     * 환자명
     */
    private String patientNm;
    /**
     * 격리/입소구분 명칭
     */
    private String qantnDivNm;
    /**
     * 생년월일
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    /**
     * 성별
     */
    private String sex;
    /**
     * 나이
     */
    private int age;
    /**
     * 휴대폰
     */
    private String cellPhone;
    /**
     * 환자의 위치정보 목록
     */
    private List<PatientLocationInfoVO> patientLocations;

}
