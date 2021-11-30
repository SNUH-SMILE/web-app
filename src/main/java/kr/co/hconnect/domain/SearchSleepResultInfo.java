package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    private String loginId;
    /**
     * 측정시작일시
     */
    private LocalDateTime resultStartDateTime;
    /**
     * 측정종료일시
     */
    private LocalDateTime resultEndDateTime;
}
