package kr.co.hconnect.vo;
/**
 * 문진내역조회 리스트 조회조건 VO
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class InterviewListSearchVO implements Serializable {

    private String loginId;

    private String requestDate;
}
