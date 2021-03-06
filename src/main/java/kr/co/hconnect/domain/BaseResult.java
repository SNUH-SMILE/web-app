package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull(message = "{validation.null.resultDate}")
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate resultDate;
    /**
     * 측정시간
     */
    @NotNull(message = "{validation.null.resultTime}")
    @JsonFormat(pattern = "HHmmss")
    private LocalTime resultTime;
    /**
     * 디바이스ID
     */
    @NotNull(message = "{validation.null.deviceId}")
    @Size(max = 20, message = "{validation.size.deviceId}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String deviceId;

}
