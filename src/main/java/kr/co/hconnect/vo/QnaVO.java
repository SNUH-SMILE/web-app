package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 문의사항 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QnaVO implements Serializable {

    private static final long serialVersionUID = 1593323417926788783L;

    /**
     * 문의순번
     */
    private int questionSeq;
    /**
     * 센터ID
     */
    private String centerId;
    /**
     * 환자ID
     */
    private String patientId;
    /**
     * 환자명
     */
    private String patientNm;
    /**
     * 문의유형 (CD009)
     */
    private String questionType;
    /**
     * 문의유형명
     */
    private String questionTypeNm;
    /**
     * 문의내용
     */
    private String questionContent;
    /**
     * 등록일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp regDt;
    /**
     * 답변자ID
     */
    private String replyId;
    /**
     * 답변자명
     */
    private String replyNm;
    /**
     * 답변여부
     */
    private String replyYn;
    /**
     * 답변내용
     */
    private String replyContent;
    /**
     * 답변일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp replyDt;
}
