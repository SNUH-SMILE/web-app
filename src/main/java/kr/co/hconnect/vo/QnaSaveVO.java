package kr.co.hconnect.vo;

import kr.co.hconnect.common.VoValidationGroups;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 문의사항 저장 정보 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QnaSaveVO implements Serializable {
    private static final long serialVersionUID = -7889520515679306045L;

    private String loginId;

    private String questionTitle;

    private String questionBody;

}
