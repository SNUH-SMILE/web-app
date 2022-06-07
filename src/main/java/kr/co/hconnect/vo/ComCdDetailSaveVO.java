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
 * 공통코드상세 저장 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ComCdDetailSaveVO implements Serializable {

    private static final long serialVersionUID = 1975635651054377809L;

    /**
     * 공통코드상세 조회조건 VO
     */
    private ComCdDetailSearchVO comCdDetailSearchVO;
    /**
     * 공통코드상세 저장 데이터 리스트
     */
    @Valid
    @NotNull(message = "{validation.null.save.comCdDetailList}")
    private List<ComCdDetailVO> comCdDetailVOList;
}
