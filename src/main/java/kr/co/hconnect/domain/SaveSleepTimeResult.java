package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 수면 구성정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveSleepTimeResult implements Serializable {

    private static final long serialVersionUID = -1047867605598679950L;

    /**
     * 격리/입소내역ID
     */
    private String admissionId;
    /**
     * 측정시작일자
     */
    @NotNull
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate resultStartDate;
    /**
     * 측정시작시간
     */
    @NotNull
    @JsonFormat(pattern = "HHmm")
    private LocalTime resultStartTime;
    /**
     * 측정종료일자
     */
    @NotNull
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate resultEndDate;
    /**
     * 측정종료시간
     */
    @NotNull
    @JsonFormat(pattern = "HHmm")
    private LocalTime resultEndTime;
    /**
     * 수면유형 (0:깊은잠, 1:얕은잠, 2:기상)
     */
    @NotNull
    @Pattern(regexp = "^[012]$", message = "{validation.sleepType.patternMismatch}")
    private String sleepType;
    /**
     * 디바이스ID
     */
    @NotNull
    private String deviceId;
}
