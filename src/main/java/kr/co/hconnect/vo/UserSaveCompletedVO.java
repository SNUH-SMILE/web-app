package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 사용자 저장 완료 정보 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserSaveCompletedVO implements Serializable {

    private static final long serialVersionUID = -5068184917253494098L;

    /**
     * 사용자 저장 정보
     */
    private UserVO userVO;
    /**
     * 사용자 리스트
     */
    private List<UserVO> userVOList;
}
