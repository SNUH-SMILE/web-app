package kr.co.hconnect.vo;

import kr.co.hconnect.domain.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResponseExtraVo <T> extends BaseResponse {

    private static final long serialVersionUID = -1127650590798927000L;

    private T result;

}
