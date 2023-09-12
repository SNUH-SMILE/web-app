package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 환자의 위치정보 VO
 */
@Getter
@Setter
@ToString
public class PatientLocationVO {

    /**
     * 로그인ID
     */
    @NotNull(message = "{validation.null.loginId}")
    private String loginId;
    /**
     * 측정일자
     */
    @NotNull(message = "{validation.null.resultDate}")
    @JsonFormat(pattern = "yyyyMMdd")
    private String resultDate;
    /**
     * 측정일시
     */
    @NotNull(message = "{validation.null.resultTime}")
    @JsonFormat(pattern = "HHmmss")
    private String resultTime;
    /**
     * 위도
     */
    @NotNull
    @JsonProperty("lat")
    private String latitude;
    /**
     * 경도
     */
    @NotNull
    @JsonProperty("lng")
    private String longitude;

}
