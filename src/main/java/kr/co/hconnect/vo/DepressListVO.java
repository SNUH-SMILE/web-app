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
public class DepressListVO implements Serializable {
    private static final long serialVersionUID = 8079697051819021363L;

    private String admissionId;

    private String st1Yn;
    private String st2Yn;
    private String st3Yn;
    private String st4Yn;
    private String st5Yn;
    private String st6Yn;
    private String st7Yn;
    private String st8Yn;
    private String st9Yn;
    private String gadTotal;

    private String insTotal;

    private String phqTotal;

    private int phq1Yn;
    private int phq2Yn;
    private int phq3Yn;
    private int phq4Yn;
    private int phq5Yn;
    private int phq6Yn;
    private int phq7Yn;
    private int phq8Yn;
    private int phq9Yn;


    private String video;

    private String tag;

    private String split;

}
