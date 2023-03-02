package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 환자 상세 대시보드 상단 헤더 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientDetailDashboardHeaderBaseVO implements Serializable {

    private static final long serialVersionUID = -305046372421709971L;

    /**
     * 환자 상태 알림
     */
    private PatientHealthSignalVO healthSignalVO;
    /**
     * 격리/입소내역ID
     */
    private String admissionId;
    /**
     * 환자ID
     */
    private String patientId;
    /**
     * 성명
     */
    private String patientNm;
    /**
     * 나이-만
     */
    private Integer age;
    /**
     * 생년월일
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    /**
     * 성별
     */
    private String sex;
    /**
     * 성별 명칭
     */
    private String sexNm;
    /**
     * 휴대폰
     */
    private String cellPhone;
    /**
     * 격리/입소구분 - CD004
     */
    private String qantnDiv;
    /**
     * 센터ID
     */
    private String centerId;
    /**
     * 센터명
     */
    private String centerNm;
    /**
     * 호실
     */
    private String room;
    /**
     * 호실명
     */
    private String roomNm;
    /**
     * 시작일
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate admissionDate;
    /**
     * 종료예정일
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dschgeSchdldDate;
    /**
     * 종료일
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dschgeDate;
    /**
     * 퇴소/해제 여부
     */
    private String dschgeYn;


    /**
     * 생체데이타 마지막 날
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate vitalDate;

}
