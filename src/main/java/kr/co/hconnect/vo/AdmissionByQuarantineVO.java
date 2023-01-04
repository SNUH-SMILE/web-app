package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdmissionByQuarantineVO extends VitalResultVO {

    private static final long serialVersionUID = 6997235003961363599L;

    /**
     * 격리/입소내역ID
     */
    private String admissionId;
    /**
     * 환자ID
     */
    private String patientId;
    /**
     * 환자명
     */
    private String patientNm;

    /**
     * 시작일-(격리시작일)
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate admissionDate;

    /**
     * 격리/입소 구분 (CD004)
     * 	0: 대상자 아님
     * 	1: 자가격리
     * 	2: 생활치료센터
     */
    private String qantnDiv;
    /**
     * 격리일수
     */
    private int qantnDay;
    /**
     * 입소/격리 상태
     * 	입소
     * 		1: 재원중, 2: 퇴소
     * 	격리
     * 		1: 격리중, 2: 격리해제
     */
    private String qantnStatus;

    private String searsAccount;

    private String videoDown;

    private String aiExe;



}
