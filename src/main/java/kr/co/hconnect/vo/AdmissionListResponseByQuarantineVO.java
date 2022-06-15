package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 자가격리 리스트 조회 결과
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdmissionListResponseByQuarantineVO implements Serializable {

    private static final long serialVersionUID = 6195454582805628948L;

    /**
     * 페이징 정보
     */
    private PaginationInfoVO paginationInfoVO;
    /**
     * 자가격리자 리스트 결과
     */
    private List<AdmissionByQuarantineVO> admissionByQuarantineVOList;
}
