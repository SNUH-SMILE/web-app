package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * 생활치료센터 입소내역 리스트 조회조건 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdmissionListSearchByCenterVO extends PaginationInfoVO {

    private static final long serialVersionUID = -7010802416848710401L;

    /**
     * 센터ID
     */
    @NotBlank(message = "{validation.null.centerId}")
    private String centerId;
    /**
     * 환자ID
     */
    private String patientId;
    /**
     * 환자명
     */
    private String patientNm;
    /**
     * 재원상태
     *
     * */
    private String qantnStatus;

}
