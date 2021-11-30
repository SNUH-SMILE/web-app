package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 측정결과 세부
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResultDetail implements Serializable {

    private static final long serialVersionUID = -5965366699841059749L;

    /**
     * 측정결과순번
     */
    private long resultSeq;
    /**
     * 측정결과유형
     */
    private String resultType;
    /**
     * 측정결과
     */
    private String result;
}
