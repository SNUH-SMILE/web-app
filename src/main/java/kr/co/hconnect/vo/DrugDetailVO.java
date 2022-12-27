package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import kr.co.hconnect.repository.DrugDao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 환자 복약 알림 
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DrugDetailVO extends BaseDefaultVO{

    private static final long serialVersionUID = 2314074066063819256L;
    /**
     * 복약 알람순번
     */
    private String noticeDd;

    private List<DrugDoseVO> drugDose;


}
