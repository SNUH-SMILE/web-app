package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 문진
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class InterviewContentVO implements Serializable {

    private Integer interviewSeq;

    private Integer interseq;

    private String interCategori;

    private String interNo;

    private String interContent;

    private String interType;

    private Integer interOrder;

    private String interviewType;

    private String regId;

    private Date regDt;

    private String val01;

    private String val02;

    private String val03;

    private String val04;

    private String val05;

    private String val06;

    private String val07;

    private String val08;

    private String val09;

    private String val10;

    private String val11;

    private String val12;

    /*interview detail*/


    /**
     * 문진상세순번
     */
    private int interviewDetailSeq;

    /**
     * 문항번호
     */
    @NotBlank(message = "{validation.null.answerSeq}")
    private String answerSeq;
    /**
     * 답변
     */
    private String answerValue;


}
