package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DrugDoseVO extends BaseDefaultVO {

    /**복약내역순번' */
    private int drugDoseSeq;
    /**
     * 격리/입소내역ID
     */
    private String admissionId;
    /**
     * 복약일자
     */
    private String  noticeDd;
    /**
     * 복약시간
     */
    private String  noticeTime;
    /**
     * '알림이름'
     */
    private String  noticeName;
    /**
     * 약울이름
     */
    private String  drugName;

    /**
     * 복용량
     */
    private String  drugCount;
    /**
     * 복약 량 단위
     */
    private String  drugType;
    /**
     * 복약구분
     */
    private String  takeResult;

    /**
     * 복약순번
     */
    private int  drugSeq;
    /**
     * 복약알람순번
     */
    private int  drugAlarmSeq;

    private String  resultDate;

    private String   resultTime;

    private String   noAlarm;

}
