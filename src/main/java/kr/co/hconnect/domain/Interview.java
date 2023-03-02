package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 문진
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Interview extends BaseResponse {


    /**
     * 아이디
     */
    private Integer id;

    /**
     * 문진이름
     */
    private String title;
    /**
     * 문진일자
     */
    private String dd;
    /**
     * 측정시간
     */
    private String time;
    /**
     * 문진종류
     */
    private String type;
    /**
     * 문진상태
     */
    private String state;
    /**
     * 격리/입소내역 id
     */
    private String admissionId;
    /**
     * 등록자id
     */
    private String regId;
    /**
     * 조회 일자
     */
    private String requestDate;

    /*
    *문진타입
    */
    private String iType;

}
