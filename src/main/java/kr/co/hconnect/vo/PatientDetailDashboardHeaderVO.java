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
public class PatientDetailDashboardHeaderVO implements Serializable {

    private static final long serialVersionUID = -2739206614492681679L;

    /**
     * 환자 상태 알림
     */
    private PatientHealthSignalVO healthSignalVO;
    /**
     * 환자 마지막 신체계측, 수면, 예측결과 VO
     */
    private PatientDetailDashboardRecentResultVO recentResultInfo;
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
     * 연락처
     * @return - 표현된 연락처 String
     */
    public String getDispCellPhoneInfo() {
        String cellPhone = this.cellPhone;

        if (cellPhone.length() == 11 || cellPhone.length() == 10) {
            String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
            cellPhone = this.cellPhone.replaceAll(regEx, "$1-$2-$3");
        }

        return cellPhone;
    }
    
    /**
     * 성별 및 나이 표현 문구
     */
    public String getDispNameDetailInfo() {
        return String.format("%s 만 %d세", getSexNm(), getAge());
    }
    
    /**
     * 생년월일 표현 문구
     */
    public String getDispBirthDateInfo() {
        return String.format("%s (%d/%s)", getBirthDate(), getAge(), getSex());
    }

    /**
     * 환자 센터 및 호실, 자택격리 표현 문구
     */
    public String getDispLocationInfo() {
        String locationInfo = "";

        if (getQantnDiv().equals("1")) {
            locationInfo = "자택격리자";
        } else if (getQantnDiv().equals("2")) {
            locationInfo = String.format("%s %s", getCenterNm(), getRoomNm());
        }

        return locationInfo;
    }

    /**
     * 재원/격리 기간 표현 문구
     */
    public String getDispAdmissionPeriodInfo() {
        String admissionPeriodInfo;

        if (getDschgeYn().equals("Y")) {
            admissionPeriodInfo = String.format("%s ~ %s", getAdmissionDate(), getDschgeDate() );
        } else {
            admissionPeriodInfo = String.format("%s ~ %s", getAdmissionDate(), getDschgeSchdldDate() );
        }
        return admissionPeriodInfo;
    }

    /**
     * 재원/격리, 퇴소/해재 표현 문구
     */
    public String getDispDschgeInfo() {
        String dschgeInfo;

        if (getDschgeYn().equals("Y")) {
            dschgeInfo = getQantnDiv().equals("1") ? "해제" : "퇴소";
        } else {
            dschgeInfo = getQantnDiv().equals("1") ? "격리" : "재원";
        }
        
        return dschgeInfo;
    }
}
