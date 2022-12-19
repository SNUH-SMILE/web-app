package kr.co.hconnect.vo;

import java.io.Serializable;
import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 추론엔진
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ArchiveVO implements Serializable{
    private static final long serialVersionUID = 7497122173201726013L;

    private String archiveId;
    private String createAt;

    private String partnerId;
    private String name;

    private String sessionId;

    private BigInteger size;

    private String status;  //start , stop

    private String output_mode;

    private String reason;

    private String down_yn;
}
