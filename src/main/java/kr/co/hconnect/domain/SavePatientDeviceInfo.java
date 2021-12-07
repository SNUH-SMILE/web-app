package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 환자별 장비 저장 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SavePatientDeviceInfo implements Serializable {

    private static final long serialVersionUID = 7575949722835147515L;

    /**
     * 로그인ID
     */
    @NotNull(message = "{validation.loginId.null}")
    private String loginId;
    /**
     * 장비리스트
     */
    @NotNull
    @JsonProperty(value = "devices")
    private List<PatientDevice> patientDeviceList;
}
