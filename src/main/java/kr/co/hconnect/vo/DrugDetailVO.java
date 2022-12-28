package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import kr.co.hconnect.repository.DrugDao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 환자상세 투약내역 vo
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DrugDetailVO implements Serializable {


    /**
     * 복약일자
     */
    private String noticeDd;

    /**
     * 복약 알람 이름
     */
    private String noticeName;
    /**
     * 복약 알람 시간
     */
    private String noticeTime;
    /**
     * 복약 알람 시작 날짜
     */
    private String noticeStartDate;
    /**
     * 복약 알람 종료 일자
     */
    private String noticeEndDate;
    /**
     * 복약내역순번
     */
    private String drugDoseSeq;




}
