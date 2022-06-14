package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 생활치료센터 입소자 등록/수정 완료 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdmissionSaveResponseByCenterVO implements Serializable {

    private static final long serialVersionUID = 338934281862294354L;

    /**
     * 신규,수정 된 격리/입소 ID
     */
    private String admissionId;
    /**
     * 생활치료센터 입소내역 리스트 조회 결과
     */
    private AdmissionListResponseByCenterVO admissionListResponseByCenterVO;

}
