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
 * 화상상담 파일 다운로드 Response
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TeleResArchiveDownVO implements Serializable{

    private static final long serialVersionUID = 722291528871703594L;


    private String msg;


}
