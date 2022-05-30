package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 생활치료센터 저장 완료 정보 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TreatmentCenterSaveCompletedVO implements Serializable {

    private static final long serialVersionUID = 6985667055382565173L;

    /**
     * 생활치료센터 저장 정보
     */
    private TreatmentCenterVO data;
    /**
     * 생활치료센터 리스트
     */
    private List<TreatmentCenterVO> list;

}
