package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DrugListVO  implements Serializable {
    private static final long serialVersionUID = 8600655561924912829L;
    /**
     * 복약ID
     */
    private int drugSeq;
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
