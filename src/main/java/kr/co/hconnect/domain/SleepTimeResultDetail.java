package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 수면시간 측정결과 상세
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SleepTimeResultDetail extends BaseResponse {

    private static final long serialVersionUID = 1233864927452295030L;

    /**
     * 총수면시간
     */
    @JsonFormat(pattern = "HHmm")
    private LocalTime totalSleepTime;
    /**
     * 측정시작일시
     */
    @JsonFormat(pattern = "yyyyMMddHH")
    private LocalDateTime resultStartDateTime;
    /**
     * 측정종료일시
     */
    @JsonFormat(pattern = "yyyyMMddHH")
    private LocalDateTime resultEndDateTime;
    /**
     * 수면시간 측정결과 목록
     */
    private List<SleepTimeResult> sleepTimeList;
}
