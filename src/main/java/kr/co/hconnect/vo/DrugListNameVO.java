package kr.co.hconnect.vo;

import java.io.Serializable;
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
import java.util.List;

/**
 * 환자 복약 알림
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DrugListNameVO implements Serializable {
    private static final long serialVersionUID = 5636265909701115057L;


    /**
     * 약물이름
     */
    private String drugName;

    /**
     * 복용량
     */
    private String drugCount;

    /**
     *  복용량 단위
     */
    private String drugType;


}
