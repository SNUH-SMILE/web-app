package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 환자 현황 대시보드 환자정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientStatusDashboardDetailVO extends VitalResultVO {

    private static final long serialVersionUID = 3992570549694315207L;

    /**
     * 환자 상태 알림
     */
    private PatientHealthSignalVO healthSignalVO;
    /**
     * 격리/입소내역ID
     */
    private String admissionId;
    /**
     * 호실
     */
    private String room;
    /**
     * 호실명
     */
    private String roomNm;
    /**
     * 나이 (만나이)
     */
    private Integer age;
    /**
     * 성별
     */
    private String sex;
    /**
     * 성별 명칭
     */
    private String sexNm;
    /**
     * 환자ID
     */
    private String patientId;
    /**
     * 환자명
     */
    private String patientNm;


}
