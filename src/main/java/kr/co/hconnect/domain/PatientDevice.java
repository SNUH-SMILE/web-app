package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 환자별 장비
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientDevice implements Serializable {

    private static final long serialVersionUID = -6242820788394809935L;

    /**
     * 장비순번
     */
    private long deviceSeq;
    /**
     * 격리/입소내역ID
     */
    private String admissionId;
    /**
     * 장비ID
     */
    @NotNull(message = "{validation.null.deviceId}")
    @Size(max = 100, message = "{validation.size.deviceId}")
    private String deviceId;
    /**
     * 장비명
     */
    @NotNull(message = "{validation.null.deviceNm}")
    @Size(max = 500, message = "{validation.size.deviceNm}")
    private String deviceNm;
    /**
     * 사용여부
     */
    private String useYn;
}
