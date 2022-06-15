package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 문의사항 리스트 조회 결과
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QnaListResponseVO implements Serializable {

    private static final long serialVersionUID = -2418175135092778613L;

    /**
     * 페이징 정보
     */
    private PaginationInfoVO paginationInfoVO;
    /**
     * 문의사항 리스트 조회 결과
     */
    private List<QnaVO> qnaVOList;
}
