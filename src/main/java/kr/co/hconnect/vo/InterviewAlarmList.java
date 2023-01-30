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
 * 문진  결과 vo - 알람리스트
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class InterviewAlarmList implements Serializable  {

    private static final long serialVersionUID = -7817050792400622824L;

    private String admissionId;
    private String loginId;
    private String interviewType;


}
