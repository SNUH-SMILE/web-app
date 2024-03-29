package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 자가격리자 퇴소 처리 정보 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdmissionDischargeCancleVO extends BaseDefaultVO {

    private static final long serialVersionUID = -9030171077145293591L;

    /**
     * 자가격리자 리스트 조회조건 VO
     */
    @Valid
    @NotNull(message = "{validation.null.refresh.searchInfo}")
    private AdmissionListSearchByQuarantineVO admissionListSearchByQuarantineVO;

    /**
     * 격리/입소내역ID
     */
    private String admissionId;

}
