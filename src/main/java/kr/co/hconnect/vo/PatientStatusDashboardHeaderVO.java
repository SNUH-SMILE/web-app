package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 환자 현황 대시보드 상단 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientStatusDashboardHeaderVO implements Serializable {

    private static final long serialVersionUID = 4574839310998620804L;

    /**
     * 조회시간 정보(MM월 dd일 HH:mm)
     */
    @JsonFormat(pattern = "M월 d일 HH:mm")
    private LocalDateTime searchDateInfo;
    /**
     * 대시보드 명칭
     */
    private String dashboardTitle;
    /**
     * 전체 인원
     */
    private int totalCount;
    /**
     * 당일 입실/격리 인원
     */
    private int todayAdmissionCount;
    /**
     * 당일 퇴실/해제 인원
     */
    private int todayDischargeCount;
}
