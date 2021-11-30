package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 결과 타입이 2개인 측정결과 검색 조건
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchResultInfos extends SearchResultInfo implements Serializable {


    private static final long serialVersionUID = 7383606246095801339L;
    /**
     * 첫번째 결과 타입
     */
    private String firstResultType;
    /**
     * 두번째 결과 타입
     */
    private String secondResultType;
}
