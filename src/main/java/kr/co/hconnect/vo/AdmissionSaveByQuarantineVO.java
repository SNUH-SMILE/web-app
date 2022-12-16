package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.hconnect.common.BaseDefaultVO;
import kr.co.hconnect.common.VoValidationGroups;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * 자가격리자 등록/수정 내역 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdmissionSaveByQuarantineVO extends BaseDefaultVO {

    private static final long serialVersionUID = -4213226563205944483L;

    /**
     * 자가격리자 리스트 조회조건 VO
     */
    @NotNull(message = "{validation.null.refresh.searchInfo}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    private AdmissionListSearchByQuarantineVO admissionListSearchByQuarantineVO;

    /**
     * 격리/입소내역ID
     */
    @NotBlank(message = "{validation.null.admissionId}", groups = VoValidationGroups.modify.class)
    private String admissionId;
    /**
     * 환자ID
     */
    @NotBlank(message = "{validation.null.patientId}", groups = VoValidationGroups.modify.class)
    private String patientId;
    /**
     * 환자명
     */
    @NotBlank(message = "{validation.null.name}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    @Size(max = 50, message = "{validation.size.patientNm}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    private String patientNm;
    /**
     * 생년월일
     */
    @NotNull(message = "{validation.null.birthday}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate birthDate;
    /**
     * 성별
     */
    @NotBlank(message = "{validation.null.sex}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    @Pattern(regexp = "^[MF]$", message = "{validation.patternMismatch.sex}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    private String sex;
    /**
     * 연락처
     */
    @NotBlank(message = "{validation.null.cellphone}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    @Pattern(regexp = "^[0-9]+$",message = "{validation.patternMismatch.cellphone}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    @Size(max = 15, message = "{validation.size.cellPhone}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    private String cellPhone;

    /**
     * 시작일
     */
    @NotNull(message = "{validation.null.admissionDate}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class })
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate admissionDate;
    /**
     * 종료예정일
     */
    @NotNull(message = "{validation.null.dschgeSchdldDate}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class})
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate dschgeSchdldDate;
    /**
     * 담당자
     */
    @NotBlank(message = "{validation.null.personCharge}", groups = { VoValidationGroups.add.class, VoValidationGroups.modify.class})
    private String personCharge;

    /**
     * 시어스 체온계 계정
     */
    private String searsAccount;
}
