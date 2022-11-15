package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class InterviewTest extends BaseDefaultVO {

    private String interviewTitle;

    private String interviewType;

    private String interviewTime;

    private String interviewStatus;

}
