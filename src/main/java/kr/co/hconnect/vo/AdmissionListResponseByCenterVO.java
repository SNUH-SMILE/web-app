package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 생활치료센터 입소내역 리스트 조회 결과
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdmissionListResponseByCenterVO implements Serializable {

    private static final long serialVersionUID = -72926900969779936L;

    /**
     * 페이징 정보
     */
    private PaginationInfoVO paginationInfoVO;
    /**
     * 생화치료센터 리스트 결과
     */
    private List<AdmissionByCenterVO> admissionByCenterVOList;
}
