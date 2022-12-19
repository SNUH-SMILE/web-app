package kr.co.hconnect.vo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 스코어
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ScoreVO implements Serializable{

    /**
     * 환자 번호 - 입소id
     */
    private String admissionId;

    /**
     * 나이
     */
    private String age;

    /**
     * 맥박 심박수
     */
    private String pr;

    /**
     * 산소포화도
     */
    private String spo2;

    /**
     * 수축기 혈압
     */
    private String sbp;

    /**
     * 이완기 혈압
     */
    private String dbp;

    /**
     * 체온
     */
    private String bt;

    /**
     * 체온
     */
    private String ache;

    /**
     * 고혈압여부
     */
    private String hyp;

    
}
