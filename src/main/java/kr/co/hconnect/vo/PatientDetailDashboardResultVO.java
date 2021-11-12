package kr.co.hconnect.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 환자 상세 대쉬보드 측정결과 내역
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientDetailDashboardResultVO implements Serializable {

	private static final long serialVersionUID = -2934443340873065954L;

	/**
	 * 측정일시
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp resultDt;
	/**
	 * 혈압
	 */
	private String bp;
	/**
	 * 혈압 위험 여부
	 */
	private String bpRiskYn;
	/**
	 * 혈압-SBP
	 */
	private String sbp;
	/**
	 * 혈압-DBP
	 */
	private String dbp;
	/**
	 * 맥박
	 */
	private String pr;
	/**
	 * 맥박 위험 여부
	 */
	private String prRiskYn;
	/**
	 * 체온
	 */
	private String bt;
	/**
	 * 체온 위험 여부
	 */
	private String btRiskYn;
	/**
	 * 호흡
	 */
	private String rr;
	/**
	 * 호흡 위험 여부
	 */
	private String rrRiskYn;
	/**
	 * 산소포화도
	 */
	private String spo2;
	/**
	 * 산소포화도 위험 여부
	 */
	private String spo2RiskYn;

}
