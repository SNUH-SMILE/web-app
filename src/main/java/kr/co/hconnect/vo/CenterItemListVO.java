package kr.co.hconnect.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CenterItemListVO implements Serializable {

    private static final long serialVersionUID = 2560155452598200664L;

    /**
     * 혈압 ITEM ID
     */
    private String bpId;
    /**
     * 혈압 ITEM 명
     */
    private String bpNm;
    /**
     * 혈압 ITEM UNIT
     */
    private String bpUnit;
    /**
     * 맥박 ITEM ID
     */
    private String hrId;
    /**
     * 맥박 ITEM 명
     */
    private String hrNm;
    /**
     * 맥박 ITEM UNIT
     */
    private String hrUnit;
    /**
     * 체온 ITEM ID
     */
    private String btId;
    /**
     * 체온 ITEM 명
     */
    private String btNm;
    /**
     * 체온 ITEM UNIT
     */
    private String btUnit;
    /**
     * 호흡 ITEM ID
     */
    private String rrId;
    /**
     * 호흡 ITEM 명
     */
    private String rrNm;
    /**
     * 호흡 ITEM UNIT
     */
    private String rrUnit;
    /**
     * 산소포화도 ITEM ID
     */
    private String spo2Id;
    /**
     * 산소포화도 ITEM 명
     */
    private String spo2Nm;
    /**
     * 산소포화도 ITEM UNIT
     */
    private String spo2Unit;
}
