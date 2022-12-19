package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DrugAlarmSaveVO implements Serializable {

    private static final long serialVersionUID = 2811680054468248947L;

    /**
     * 복약 관리 내용
     */
    /**
     * 아이디
     */
    @NotNull(message = "{validation.null.loginId}")
    private String loginId;

    private int  drugSeq;
    private String admissionId;
    /**
     * 복약시작일시
     */
    @NotNull(message = "{validation.null.noticeStartDate}")
    @JsonFormat(pattern = "yyyyMMddHH")
    private String noticeStartDate;

    /**
     * 복약종료일시
     */
    @NotNull(message = "{validation.null.noticeEndDate}")
    @JsonFormat(pattern = "yyyyMMddHH")
    private String noticeEndDate;

    @NotNull
    private String noticeDate;

    @NotNull
    private String noticeName;


    private String drugName;
    private String drugCount;
    private String drugType;
    private String RegId;

    /**
     * 약물 리스트
     */
    private List<DrugListVO> drugList;

    /**
     * 복약 알람 리스트
     */
    private List<DrugAlarmVO> noticeTimeList;

}
