package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 환자 복약  타임  리스트
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DrugTimeVO implements Serializable{

    private static final long serialVersionUID = -7987454682434058094L;

    private String noticeId;

    private String noticeName;

    private String takeDate;

    private String takeTime;

    private String noAlarm;

    private int drugSeq;

    private int drugDoseSeq;

    private List<DrugListNameVO> drugList;

}
