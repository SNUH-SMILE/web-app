package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 로그인ID
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginId implements Serializable {

    private static final long serialVersionUID = 6553727126635301004L;

    /**
     * 아이디
     */
    @NotNull(message = "사용자 아이디가 누락되었습니다.")
    private String loginId;

}
