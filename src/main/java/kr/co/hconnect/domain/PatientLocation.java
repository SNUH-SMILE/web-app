package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 환자의 위치정보
 */
@Getter
@Setter
@ToString
public class PatientLocation {

    /**
     * 격리/입소내역ID
     */
    private String admissionId;
    /**
     * 측정일시
     */
    private LocalDateTime resultDt;
    /**
     * 위도
     */
    private String latitude;
    /**
     * 경도
     */
    private String longitude;
    /**
     * 로그인ID
     */
    private String loginId;

}
