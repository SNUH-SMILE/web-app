package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *  알림
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class NoticeListVO implements Serializable {

    /** 알림 내용 */
    private String noticeBody;

    /** 알림 등록일
     * yyyyMMdd
     * */
    private String noticeDate;
}
