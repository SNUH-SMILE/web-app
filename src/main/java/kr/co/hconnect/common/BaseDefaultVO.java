package kr.co.hconnect.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 기본 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BaseDefaultVO implements Serializable {

    private static final long serialVersionUID = 4291691059197640408L;

    /**
     * 등록자ID
     */
    private String regId;

    /**
     * 등록자명
     */
    private String regNm;

    /**
     * 등록일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp regDt;

    /**
     * 수정자ID
     */
    private String updId;

    /**
     * 수정자명
     */
    private String updNm;
    
    /**
     * 수정일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updDt;

}
