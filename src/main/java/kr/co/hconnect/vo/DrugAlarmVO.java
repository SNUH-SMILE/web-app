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

/**
 * 환자 복약 알림 
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DrugAlarmVO extends BaseDefaultVO{

    private static final long serialVersionUID = 2314074066063819256L;
    /**
     * 복약 알람순번
     */
    private int drugAlarmSeq;
    /**
     * 복약 알림시간
     */
    private String noticeTime;
    /**
     * 복약 관리 순번
     */
    private int drugSeq;

}
