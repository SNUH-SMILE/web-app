package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 환자 현황 대시보드 환자정보 조회 조건
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientStatusDashboardDetailSearchVO implements Serializable {

    private static final long serialVersionUID = -1687856176963275824L;

    /**
     * 격리/입소 구분 (CD004)
     * 	1: 자가격리
     * 	2: 생활치료센터
     */
    private String qantnDiv;
    /**
     * 센터ID
     */
    private String centerId;

}
