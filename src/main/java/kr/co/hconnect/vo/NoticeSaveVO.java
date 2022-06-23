package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 신규 알린 저장 정보 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class NoticeSaveVO implements Serializable {

    private static final long serialVersionUID = 8839109078018923084L;

    /**
     * 격리/입소내역ID
     */
    @NotBlank(message = "{validation.null.admissionId}")
    private String admissionId;
    /**
     * 내용
     */
    @NotBlank(message = "{validation.null.notice}")
    @Size(max = 500, message = "{validation.size.notice}")
    private String notice;

}
