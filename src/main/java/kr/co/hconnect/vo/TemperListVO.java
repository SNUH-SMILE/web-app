package kr.co.hconnect.vo;
import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 추론엔진
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TemperListVO implements Serializable{
    private static final long serialVersionUID = -3189092348666662134L;

    private String admissionId;

    private float pr;

    private float bt;

    private String q1Yn;

    private String q2Yn;

    private String q3Yn;

    private String q4Yn;

    private String q5Yn;

    private String q6Yn;



}