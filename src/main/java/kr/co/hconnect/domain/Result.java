package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 측정결과
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Result implements Serializable {

    private static final long serialVersionUID = 2135813391753871568L;

    /**
     * 측정결과순번
     */
    private long resultSeq;
    /**
     * 격리/입소내역ID
     */
    private String admissionId;
    /**
     * 측정항목ID
     */
    private String itemId;
    /**
     * 측정일자
     */
    @NotNull(message = "{validation.null.resultDate}")
    private LocalDate resultDate;
    /**
     * 측정시간
     */
    @NotNull(message = "{validation.null.resultTime}")
    private LocalTime resultTime;
    /**
     * 디바이스ID
     */
    @NotNull(message = "{validation.null.deviceId}")
    private String deviceId;

}
