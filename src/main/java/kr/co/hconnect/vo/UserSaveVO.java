package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * 사용자 저장 정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserSaveVO implements Serializable {

    private static final long serialVersionUID = -8461230085871706702L;

    /**
     * 사용자 조회 정보
     */
    private UserSearchVO userSearchVO;
    /**
     * 사용자 정보
     */
    @Valid
    private UserVO userVO;
}
