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
public class ArchiveDownVO implements Serializable{
    private static final long serialVersionUID = -3877740144195045721L;

    private String admissionId;
    private String dnDate;
    private String dnTime;
    private String dnDateVoice;
    private String dnTimeVoice;
    private String dnFolder;
    private String dnFolderVoice;
    private String archiveId;
    private String sessionId;

    private String dnFileName;

    private String dnFileNameVoice;

    private String dnYn;  //성공 Y 실패 n

    private String dnYnVoice;

}
