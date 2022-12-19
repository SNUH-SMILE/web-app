package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 복약리스트
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DrugResult implements Serializable {


    /**
     * 복약알림이름
     */

    private String drugName;
    /**
     * 복약알림시각
     */
    @JsonFormat(pattern = "HHmm")
    private LocalTime drugTime;
}
