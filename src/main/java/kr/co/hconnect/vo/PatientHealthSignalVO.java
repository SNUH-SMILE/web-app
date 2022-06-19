package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 환자 상태 알림
 * TODO:: 환자 상태 AI 확정 후 변경 필요
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientHealthSignalVO implements Serializable {

    private static final long serialVersionUID = 354402274376021657L;

    private String signal1Yn;
    private String signal2Yn;
}
