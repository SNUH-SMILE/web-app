package kr.co.hconnect.vo;

import egovframework.rte.fdl.idgnr.impl.EgovTableIdGnrServiceImpl;
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
public class TeleHealthResponseVO implements Serializable {


    private String sessionId;               // 세션ID

    private String officerToken;            // 담당자 토큰

    private String attendeeToken;           // 참석자 토큰

}
