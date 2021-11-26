package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 측정결과 검색 조건
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchResultInfo {

    /**
     * 아이디
     */
    private String loginId;
    /**
     * 측정일자
     */
    private LocalDate resultDate;
    
}
