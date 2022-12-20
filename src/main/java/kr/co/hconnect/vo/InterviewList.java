package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import kr.co.hconnect.domain.InterviewContent;
import kr.co.hconnect.domain.InterviewDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
/**
 * 문진 조회 결과 vo - interview
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class InterviewList implements Serializable {

    private Integer interviewSeq;

    private String interviewTitle;

    private String interviewType;

    private String interviewTime;

    private String interviewStatus;

    private List<InterviewContentVO> interviewContents;




}
