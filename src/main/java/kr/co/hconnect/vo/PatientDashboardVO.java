package kr.co.hconnect.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 환자 대쉬보드
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientDashboardVO implements Serializable {

	private static final long serialVersionUID = -8946027477836649954L;

	/**
	 * 입소내역ID
	 */
	private String admissionId;
	/**
	 * 환자ID
	 */
	private String patientId;
	/**
	 * 이름
	 */
	private String name;
	/**
	 * 성별-표현용
	 */
	private String sexNm;
	/**
	 * 나이-만
	 */
	private Integer age;
	/**
	 * 위치명
	 */
	private String locationNm;
	/**
	 * 형압 명칭
	 */
	private String bpNm;
	/**
	 * 혈압 단위
	 */
	private String bpUnit;
	/**
	 * 혈압-측정값
	 */
	private String bp;
	/**
	 * 혈압 위험 여부
	 */
	private String bpRiskYn;
	/**
	 * 맥박 명칭
	 */
	private String prNm;
	/**
	 * 맥박 단위
	 */
	private String prUnit;
	/**
	 * 맥박-측정값
	 */
	private String pr;
	/**
	 * 맥박 위험 여부
	 */
	private String prRiskYn;
	/**
	 * 체온 명칭
	 */
	private String btNm;
	/**
	 * 체온 단위
	 */
	private String btUnit;
	/**
	 * 체온-측정값
	 */
	private String bt;
	/**
	 * 체온 위험 여부
	 */
	private String btRiskYn;
	/**
	 * 호흡 명칭
	 */
	private String rrNm;
	/**
	 * 호흡 단위
	 */
	private String rrUnit;
	/**
	 * 호흡-측정값
	 */
	private String rr;
	/**
	 * 호흡 위험 여부
	 */
	private String rrRiskYn;
	/**
	 * 산소포화도 명칭
	 */
	private String spo2Nm;
	/**
	 * 산소포화도 단위
	 */
	private String spo2Unit;
	/**
	 * 산소포화도-측정값
	 */
	private String spo2;
	/**
	 * 산소포화도 위험 여부
	 */
	private String spo2RiskYn;
}
