package kr.co.hconnect.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.hconnect.common.BaseDefaultVO;
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
public class AiInferenceVO implements Serializable{

    /**
     * 격리 입소 id
     */
    private String admissionId;
    /**
     * 추론엔진 구분값
     * 10: 스코어 20:체온 30:우울
     */
    private String infDiv;

    /**
     * 추론엔진에서 나온 결과값
     */
    private  String  infValue;

}
