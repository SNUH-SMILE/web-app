package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 세션정보 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SessionVO implements Serializable {

    /**
     * 사용자ID
     */
    private String userId;
    /**
     * 사용자명
     */
    private String userNm;
    /**
     * 센터ID
     */
    private String centerId;

}
