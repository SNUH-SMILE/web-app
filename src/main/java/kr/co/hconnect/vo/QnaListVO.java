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
 * 문의사항 조회 정보 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QnaListVO implements Serializable {

    private static final long serialVersionUID = 5699446587043599073L;

    private String questionTitle;

    private String questionDate;

    private String questionBody;

    private String answerDate;

    private String answerBody;

    private int questionSeq;

}
