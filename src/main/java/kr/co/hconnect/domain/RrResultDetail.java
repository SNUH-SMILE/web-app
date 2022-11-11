package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 호흡 측정 결과
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RrResultDetail extends BaseResponse {
    
    private static final long serialVersionUID = 6176458342411571172L;

    /**
     * 호흡 측정결과 목록
     */
    private List<RrResult> rrList;
}
