package kr.co.hconnect.vo;
import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class InferenceErrorLogVO implements Serializable {
    private static final long serialVersionUID = 7213678122152589791L;

    private String admissionId;

    private String cdata;

    private String infDiv;

    private String infDivNm;

    private String message;


}
