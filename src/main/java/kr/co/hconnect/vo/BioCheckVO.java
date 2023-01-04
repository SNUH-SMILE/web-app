package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 환자 Vital 차트 표현용 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BioCheckVO implements Serializable{


    private static final long serialVersionUID = -2109096849652375980L;

    private String admissionId;

    private String itemId;

    private String date;

    private String time;

    private String interviewType;   //문진타입

    private String endDate;        //퇴소일자

}
