package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

/**
 * 알림 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RecordVO extends BaseDefaultVO {

    private static final long serialVersionUID = 7055766674862118335L;

    /**
     * 알림순번
     */
    private Integer medicalSeq;
    /**
     * 격리/입소내역ID
     */
    private String admissionId;
    /**
     * 내용
     */
    private String medicalRecord;
    /**
     * 등록일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp medicalDate;
    /**
     * 진료기록자
     */
    private String medicalRecorder;
    /**
     * 등록자
     */
    private String regId;
    /**
     * 수정아이디
     */
    private String updateId;
    /**
     * 수정자
     */
    private String updateRecorder;
    /**
     * 수정일자
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateDate;
}
