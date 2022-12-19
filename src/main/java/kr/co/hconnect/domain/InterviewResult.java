package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 문진 목록
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class InterviewResult implements Serializable {

    /**
     * 문진타입 (
     * 00	확진당일문진
     * 01	오전문진
     * 02	오후문진
     * 03	격리해제문진
     * 04	격리해제30일후문진
     * )
     */
    private String interviewType;

    /**
     * 문진상태 ( 0: 작성하기, 1: 작성하기 비활성화, 2: 작성불가, 3: 작성완료)
     */
    private String interviewStatus;

}
