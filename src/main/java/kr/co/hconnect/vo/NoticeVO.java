package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 알림 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class NoticeVO extends BaseDefaultVO {

    private static final long serialVersionUID = 7055766674862118335L;

    /**
     * 알림순번
     */
    private Integer noticeSeq;
    /**
     * 격리/입소내역ID
     */
    private String admissionId;
    /**
     * 내용
     */
    private String notice;
    /**
     * 확인여부
     */
    private String readYn;
}
