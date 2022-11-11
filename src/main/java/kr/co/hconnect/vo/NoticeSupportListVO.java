package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 환자 복약 알림
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class NoticeSupportListVO implements Serializable{

    private static final long serialVersionUID = 6314245892403550361L;
    /** 공지사항 제목 */
    private String noticeTitle;

    /** 공지사항 내용 */
    private String noticeBody;

    /** 공지사항 등록일
     * yyyyMMdd
     * */
    private String noticeDate;
}
