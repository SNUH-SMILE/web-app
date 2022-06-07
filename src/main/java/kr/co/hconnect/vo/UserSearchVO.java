package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 사용자 검색 정보 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserSearchVO implements Serializable {

    private static final long serialVersionUID = 2707445944276658259L;

    /**
     * 사용자ID
     */
    private String userId;
    /**
     * 사용자명
     */
    private String userNm;
    /**
     * 생활치료센터 ID
     */
    private String centerId;
}
