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
public class PatientDeviceUseHistory implements Serializable {

    private static final long serialVersionUID = -8953758851631458704L;

    /**
     * 장비ID
     */
    private String deviceId;
    /**
     * 장비명
     */
    private String deviceNm;
    /**
     * 결과 코드
     */
    private String code;
    /**
     * 결과 메시지
     */
    private String message;
}
