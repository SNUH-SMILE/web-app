package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * 환자 상세 대시보드 상단 헤더 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientDetailDashboardHeaderVO extends PatientDetailDashboardHeaderBaseVO {

    private static final long serialVersionUID = 6216388462960433727L;

    /**
     * 환자 마지막 신체계측, 수면, 예측결과 VO
     */
    private PatientDetailDashboardRecentResultVO recentResultInfo;

    /**
     * 연락처
     * @return - 표현된 연락처 String
     */
    public String getDispCellPhoneInfo() {
        String cellPhone = this.getCellPhone();

        if (cellPhone.length() == 11 || cellPhone.length() == 10) {
            String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
            cellPhone = this.getCellPhone().replaceAll(regEx, "$1-$2-$3");
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
