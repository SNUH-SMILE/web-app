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
 * 환자 복약 관리
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DrugVO extends BaseDefaultVO {

    private static final long serialVersionUID = 4615326485413018414L;


    private String LoginId;
    /** 복약관리 seq *
     *
     */
    private int drugSeq;

    /**
     * 격리 입소내역
     */
    private String admissionId;

    /**
     * 복약시작일자
     */
    private String noticeStartDate;

    /**
     * 복약종료일자
     */
    private String noticeEndDate;
    /** 복약일수
     *
     */
    private String noticeDate;
    /**
     * 알림이름
     */
    private String noticeName;


}
