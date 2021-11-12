package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 환자 상세 대쉬보드 입소내역정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientDetailDashboardPatientInfoVO implements Serializable {
	
	private static final long serialVersionUID = -3082175022917272118L;
	
	/**
	 * 입소내역ID
	 */
	private String admissionId;
	/**
	 * 입소일자
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date admitDate;
	/**
	 * 센터명
	 */
	private String centerNm;
	/**
	 * 이름
	 */
	private String name;
	/**
	 * 위치명
	 */
	private String locationNm;
	/**
	 * 환자ID
	 */
	private String patientId;
	/**
	 * 생년월일
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;
	/**
	 * 연락처
	 */
	private String contact;
	/**
	 * 성별
	 */
	private String sexNm;
	/**
	 * 나이-만
	 */
	private Integer age;
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
	 * 혈압 측정일시
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp bpResultDt;
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
	 * 맥박 측정일시 
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp prResultDt;
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
	 * 체온 측정일시 
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp btResultDt;
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
	 * 호흡 측정일시 
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp rrResultDt;
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
	/**
	 * 산소포화도 측정일시 
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp spo2ResultDt;

}
