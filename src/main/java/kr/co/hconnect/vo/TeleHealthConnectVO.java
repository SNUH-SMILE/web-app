package kr.co.hconnect.vo;

import kr.co.hconnect.common.VoValidationGroups;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
/**
 * 화상상담 연결
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TeleHealthConnectVO implements Serializable {

    private static final long serialVersionUID = -1932626880281734278L;

    private String loginId;

    private Integer apiKey;                 // API Key

    private String apiSecret;               // Api Secret

    private String sessionId;               // 세션ID

    private String officerToken;            // 담당자 토큰

    private String attendeeToken;           // 참석자 토큰

    private String admissionId;

}
