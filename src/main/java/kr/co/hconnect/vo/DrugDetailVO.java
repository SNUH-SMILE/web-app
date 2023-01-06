package kr.co.hconnect.vo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 환자상세 투약내역 vo
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DrugDetailVO implements Serializable {

    /**
     * 복약 일자
     */
    private String admissionDate;

    /**
     * 날짜별 복약알람
     */
    private List<DrugDoseVO> drugDoseVO;


}
