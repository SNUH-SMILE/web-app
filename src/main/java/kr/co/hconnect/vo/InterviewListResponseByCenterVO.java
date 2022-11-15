package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 문진 조회 결과  list vo
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class InterviewListResponseByCenterVO implements Serializable {

    private List<InterviewList> interviewList;

    private List<SymptomList> symptomLists;

}
