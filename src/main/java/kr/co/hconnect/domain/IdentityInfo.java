package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 본인인증 확인 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class IdentityInfo implements Serializable {

    private static final long serialVersionUID = 8476775768748997896L;

    /**
     * 주민번호
     */
    @NotNull(message = "{validation.ssn.null}")
    private String ssn;
}
