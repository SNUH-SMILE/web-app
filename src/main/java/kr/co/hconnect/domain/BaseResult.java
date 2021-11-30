package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 측정결과 기본 구성 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BaseResult implements Serializable {

    private static final long serialVersionUID = 3151632367180058023L;

    /**
     * 측정일자
     */
    private LocalDate resultDate;
    /**
     * 측정시간
     */
    private LocalTime resultTime;
    /**
     * 디바이스ID
     */
    private String deviceId;

}
