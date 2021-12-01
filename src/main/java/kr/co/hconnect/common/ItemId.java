package kr.co.hconnect.common;

/**
 * 측정항목 열거자
 */
public enum ItemId {

    /**
     * 혈압
     */
    BLOOD_PRESSURE("I0005"),
    /**
     * 체온
     */
    BODY_TEMPERATURE("I0001"),
    /**
     * 심박수
     */
    HEART_RATE("I0002"),
    /**
     * 산소포화도
     */
    OXYGEN_SATURATION("I0003"),
    /**
     * 걸음수
     */
    STEP_COUNT("I0004"),
    /**
     * 혈압
     */
    RESPIRATION("I0028");


    private final String itemId;

    ItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }
}
