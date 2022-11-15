package kr.co.hconnect.vo;

/**
 * 문진내역조회 리스트 조회조건 VO
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class InterviewListSearchVO implements Serializable {

    @NotNull(message = "{validation.null.loginId}")
    private String loginId;

    @NotNull(message = "{validation.null.requestDate}")
    private String requestDate;
}
