package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 증상목록
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveInformationAnswerListInfo implements Serializable {

    private String answerNumber;

    private String answerValue;

}
