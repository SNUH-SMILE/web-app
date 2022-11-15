package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class InterviewDetail extends BaseResponse {

    /**
     * 문진상세순번
     */
    private int detailSeq;
    /**
     * 문진순번
     */
    private int seq;
    /**
     * 문항번호
     */
    @NotBlank(message = "{validation.null.answerSeq}")
    private String answerSeq;
    /**
     * 답변
     */
    private String answerValue;
    /**
     * 등록자id
     */
    private String regId;
}
