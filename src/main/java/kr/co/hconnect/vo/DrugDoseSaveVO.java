package kr.co.hconnect.vo;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DrugDoseSaveVO implements Serializable {

    private static final long serialVersionUID = -4710820414009850797L;
    /**
     * 복약 관리 내용
     * 알림과 연결 해서
     */
    private String loginId;
    private int  drugSeq;
    private int drugAlarmSeq;
    private String admissionId;
    private String resultDate;
    private String resultTime;
    private String takeResult;

    /**
     * 알림없이 저장 할때
     */
    private String noticeName;
    private String drugName;
    private String drugCount;
    private String drugType;
    private String RegId;
}
