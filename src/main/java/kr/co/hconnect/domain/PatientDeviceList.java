package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 환자 장비 사용 이력
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientDeviceList implements Serializable {

    /**
     * 장비ID
     */
    private String deviceId;
    /**
     * 장비명
     */
    private String deviceNm;

}
