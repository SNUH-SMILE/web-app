package kr.co.hconnect.vo;

import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ItemSaveCompleteVO implements Serializable {

    private static final long serialVersionUID = 7561534040202619171L;

    /**
     * 측정항목 저장 정보
     */
    private ItemVO data;
    /**
     * 측정항목 리스트
     */
    private List<ItemVO> list;
}
