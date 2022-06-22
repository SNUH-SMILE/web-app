package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 환자 상세 대시보드 전체 조회 내역
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientDetailDashboardVO implements Serializable {

    private static final long serialVersionUID = -252919356391171650L;

    /**
     * 격리/입소내역ID
     */
    private String admissionId;
    /**
     * 환자 상세 대시보드 상단 헤더 정보
     */
    private PatientDetailDashboardHeaderVO headerVO;
    /**
     * 알림 내역 리스트
     */
    private List<NoticeVO> noticeVOList;

}
