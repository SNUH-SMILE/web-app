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
 * 문의사항 답변 저장 정보 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QnaSaveByReplyVO implements Serializable {

    private static final long serialVersionUID = -1610212378236888652L;

    /**
     * 문의사항 리스트 조회 조건 VO
     */
    @NotNull(message = "{validation.null.refresh.searchInfo}", groups = { VoValidationGroups.modify.class, VoValidationGroups.delete.class })
    private QnaListSearchVO qnaListSearchVO;
    /**
     * 문의순번
     */
    @NotNull(message = "{validation.null.questionSeq}", groups = { VoValidationGroups.modify.class, VoValidationGroups.delete.class })
    private int questionSeq;
    /**
     * 답변내용
     */
    @NotBlank(message = "{validation.null.replyContent}", groups = { VoValidationGroups.modify.class })
    @Size(max = 1000, message = "{validation.size.replyContent}", groups = { VoValidationGroups.modify.class })
    private String replyContent;
    /**
     * 답변자ID
     */
    private String replyId;

}
