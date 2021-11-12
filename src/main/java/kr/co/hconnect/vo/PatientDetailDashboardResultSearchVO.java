package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 환자 상세 대쉬보드 측정결과 조회 조건
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientDetailDashboardResultSearchVO implements Serializable {

	private static final long serialVersionUID = 1520658923199811837L;

	/**
	 * 입소ID
	 */
	private String admissionId;
	/**
	 * 측정결과 시작일시
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date resultFromDt;
	/**
	 * 측정결과 종료일시
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date resultToDt;
}
