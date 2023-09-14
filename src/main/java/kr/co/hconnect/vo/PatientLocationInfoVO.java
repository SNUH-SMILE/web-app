package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PatientLocationInfoVO {

    /**
     * 측정일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime resultDt;
    /**
     * 위도
     */
    private String latitude;
    /**
     * 경도
     */
    private String longitude;

}
