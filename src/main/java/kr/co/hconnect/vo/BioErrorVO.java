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
public class BioErrorVO implements Serializable{
    private static final long serialVersionUID = 6074535059683152292L;

    private String admissionId;

    private String cDate;

    private String infDiv;

    private String message;

}
