package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 입소내역 정보 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdmissionInterviewListVO extends BaseDefaultVO {
	
	private String intervewSeq;

}
