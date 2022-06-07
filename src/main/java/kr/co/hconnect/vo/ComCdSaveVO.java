package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 공통코드 저장 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ComCdSaveVO implements Serializable {

    private static final long serialVersionUID = -7471135376491261981L;

    /**
     * 공통코드 조회조건 VO
     */
    private ComCdSearchVO comCdSearchVO;
    /**
     * 공통코드 저장 데이터 리스트
     */
    @Valid
    @NotNull(message = "{validation.null.comCd}")
    private List<ComCdVO> comCdVOList;

}
