package kr.co.hconnect.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 환자 대쉬보드-센터정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientDashboardCenterInfoVO implements Serializable  {

	private static final long serialVersionUID = -1813414277267572630L;
	
    /**
     * 생활치료센터ID
     */
    private String centerId;
	/**
	 * 생활치료센터명 
	 */
    private String centerNm;
    /**
     * 생활치료센터위치
     */
    private String centerLocation;
    /**
     * 입소자수
     */
	private Integer admissionCount;
	/**
	 * 병원명
	 */
	private String hospitalNm;
}
