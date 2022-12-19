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
public class DrugSearchVO implements Serializable {
    private static final long serialVersionUID = -6597864335450250824L;

    private String loginId;
    private String requestDate;

    private String admissionId;

    private int drugSeq;

    private int drugDoseSeq;

}
