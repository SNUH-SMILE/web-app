package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 수면 측정결과 검색 조건
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchSleepResultInfo implements Serializable {

    private static final long serialVersionUID = -6786135443215706701L;

    /**
     * 아이디
     */
    @NotNull(message = "{validation.null.loginId}")
    private String loginId;

    /**
     * 측정시작일시
     */
    @NotNull(message = "{validation.null.resultStartDateTime}")
    @JsonFormat(pattern = "yyyyMMddHH")
    private LocalDateTime resultStartDateTime;

    /**
     * 측정종료일시
     */
    @NotNull(message = "{validation.null.resultEndDateTime}")
    @JsonFormat(pattern = "yyyyMMddHH")
    private LocalDateTime resultEndDateTime;
    /**
     * 입소자 Id
     */
    private String admissionId;
}
