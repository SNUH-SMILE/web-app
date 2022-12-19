package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 격리/입소내역
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdmissionVO extends BaseDefaultVO {

	private static final long serialVersionUID = 7561048307184224750L;

	/**
	 * 격리/입소내역ID
	 */
	private String admissionId;
	/**
	 * 환자ID
	 */
	private String patientId;
	/**
	 * 시작일
	 */
	private LocalDate admissionDate;
	/**
	 * 종료예정일
	 */
	private LocalDate dschgeSchdldDate;
	/**
	 * 종료일
	 */
	private LocalDate dschgeDate;
	/**
	 * 격리/입소구분 - CD004
	 */
	private String qantnDiv;
	/**
	 * 담당자
	 */
	private String personCharge;
	/**
	 * 센터ID
	 */
	private String centerId;

	/**
	 * 센터명
	 */
	private String centerNm;
	/**
	 * 호실
	 */
	private String room;
	/**
	 * 삭제여부
	 */
	private String delYn;
    /**
     * 퇴소위치
     */
    private String quantLocation;
	
}
