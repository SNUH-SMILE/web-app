
package kr.co.hconnect.vo;

import kr.co.hconnect.domain.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 응답 정보
 * @param <T> 결과 정보 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResponseBaseVO<T> extends BaseResponse {


    private static final long serialVersionUID = 6530149282985635216L;
    private T result;

}
