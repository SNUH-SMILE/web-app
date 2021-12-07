package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 수면시간 측정결과
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SleepTimeResult implements Serializable {

    private static final long serialVersionUID = 571618490256658959L;

    /**
     * 수면타입 (0 : 기상, 1 : 얕은잠, 2 : 깊은잠)
     */
    @Pattern(regexp = "^[012]$", message = "{validation.sleepType.patternMismatch}")
    private String sleepType;
    /**
     * 수면 시작일
     */
    @JsonIgnore
    private LocalDate sleepStartDate;
    /**
     * 수면 시작시각
     */
    @JsonFormat(pattern = "HHmm")
    private LocalTime sleepStartTime;
    /**
     * 수면 종료일
     */
    @JsonIgnore
    private LocalDate sleepEndDate;
    /**
     * 수면 종료시각
     */
    @JsonFormat(pattern = "HHmm")
    private LocalTime sleepEndTime;
}
