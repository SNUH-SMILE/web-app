package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 문의사항 리스트 조회 조건 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QnaListSearchVO extends PaginationInfoVO {

    private static final long serialVersionUID = -6132750895716087281L;

    private String patientId;



    /**
     * 센터ID -> 환자위치 (자택격리자)
     * 자택겨리 추가됨
     */
    private String centerId;
    /**
     * 답변여부
     */
    private String replyYn;
    /**
     * 조회 조건 구분
     *  patientId:환자ID
     *  patientNm:환자명
     *  questionContent:문의내역
     */
    private String searchGb;
    /**
     * 조회 조건
     */
    private String searchText;


}
