package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 자가격리자 등록/수정 완료 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdmissionSaveResponseByQuarantineVO implements Serializable {

    private static final long serialVersionUID = 3799642640228682026L;

    /**
     * 신규,수정 된 격리/입소 ID
     */
    private String admissionId;
    /**
     * 자가격리자 리스트 조회 결과
     */
    private AdmissionListResponseByQuarantineVO admissionListResponseByQuarantineVO;
}
