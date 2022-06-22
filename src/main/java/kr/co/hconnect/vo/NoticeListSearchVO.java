package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 알림 리스트 조회조건 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class NoticeListSearchVO implements Serializable {

    private static final long serialVersionUID = 4394374251215653155L;

    /**
     * 격리/입소내역ID
     */
    @NotBlank(message = "{validation.null.admissionId}")
    private String admissionId;
    /**
     * 내용
     */
    private String notice;
    /**
     * 확인여부
     */
    private String readYn;
}
