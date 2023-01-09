package kr.co.hconnect.vo;

import lombok.*;

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

    private String bioDate;

    private String bioTime;

    private String interviewType;   //문진타입

    private String endDate;        //퇴소일자

    private String endDate30;        //퇴소일자

}
