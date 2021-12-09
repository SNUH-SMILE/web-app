package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 수면 측정결과 저장 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveSleepResultInfo implements Serializable {

    private static final long serialVersionUID = -937699048607944682L;

    /**
     * 아이디
     */
    @NotNull(message = "{validation.null.loginId}")
    private String loginId;
    /**
     * 수면 측정 결과
     */
    @JsonProperty("sleepTimeList")
    @NotNull(message = "{validation.null.result}")
    @Valid
    private List<SaveSleepTimeResult> results;
}
