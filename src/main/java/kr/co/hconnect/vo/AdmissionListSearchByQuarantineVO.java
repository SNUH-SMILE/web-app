package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 자가격리 리스트 조회조건 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdmissionListSearchByQuarantineVO extends PaginationInfoVO {

    private static final long serialVersionUID = 4828464872898885732L;

    /**
     * 환자ID
     */
    private String patientId;
    /**
     * 환자명
     */
    private String patientNm;
}
