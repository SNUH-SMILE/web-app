package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
/**
 * 문진 조회 결과  vo - interview-detail
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SymptomList implements Serializable {

   private String interviewType;

   private String symptomTitle;

}
