package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 문진
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class InterviewContent extends BaseResponse {


    private Integer INTER_SEQ;

    private String INTER_CATEGORI;

    private String INTER_NO;

    private String INTER_CONTENT;

    private String INTER_TYPE;

    private Integer INTER_ORDER;

    private String INTERVIEW_TYPE;

    private String REG_ID;

    private Date REG_DT;

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

}
