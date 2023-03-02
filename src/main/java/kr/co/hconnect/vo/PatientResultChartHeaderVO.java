package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.hconnect.common.QantnDiv;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 환자 Vital 측정결과 헤더 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientResultChartHeaderVO extends PatientDetailDashboardHeaderBaseVO {

    private static final long serialVersionUID = 3494259228050456507L;

    /**
     * 환자명/위치 표현 문구
     */
    public String getDispPatientNmInfo() {
        return String.format("%s/%s"
                , getPatientNm()
                , getQantnDiv().equals(QantnDiv.QUARANTINE.getDbValue())
                        ? "자택격리자"
                        : getRoomNm());
    }

    /**
     * 생년월일 표현 문구
     */
    public String getDispBirthDateInfo() {
        return String.format("%s (%d/%s)", getBirthDate(), getAge(), getSex());
    }

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
     * 격리/입소자 측정일 조회기간 정보
     * @return List&lt;LocalDate&gt;
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    public List<LocalDate> getSearchDtList() {
        // 입소일부터 격리해제/퇴소일 or 현재일 까지 일자리스트 역순 데이터 생성
        LocalDate admissionDate = getAdmissionDate();
        //LocalDate endDate = getDschgeYn().equals("Y") ? getDschgeDate() : LocalDate.now();
        LocalDate endDate = getVitalDate() == null ? LocalDate.now() : getVitalDate();

        return Stream.iterate(endDate, d -> d.plusDays(-1))
                .limit(ChronoUnit.DAYS.between(admissionDate, endDate) + 1)
                .collect(Collectors.toList());
    }
}
