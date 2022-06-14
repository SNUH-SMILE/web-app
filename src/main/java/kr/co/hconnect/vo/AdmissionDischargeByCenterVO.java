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
 * 퇴소 처리 정보 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdmissionDischargeByCenterVO extends BaseDefaultVO {

    private static final long serialVersionUID = -4268335974205198399L;

    /**
     * 생활치료센터 입소내역 리스트 조회조건 VO
     */
    @Valid
    private AdmissionListSearchByCenterVO admissionListSearchByCenterVO;

    /**
     * 격리/입소내역ID
     */
    @NotBlank(message = "{validation.null.admissionId}")
    private String admissionId;
    /**
     * 종료일
     */
    @NotNull(message = "{validation.null.dschgeDate}")
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate dschgeDate;

}
