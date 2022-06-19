package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 환자 현황 대시보드
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientStatusDashboardVO implements Serializable {

    private static final long serialVersionUID = -6563810569423066706L;

    /**
     * 환자 현황 대시보드 헤더 정보
     */
    private PatientStatusDashboardHeaderVO header;
    /**
     * 환자 현황 대시보드 환자 리스트 정보
     */
    private List<PatientStatusDashboardDetailVO> patientList;

}
