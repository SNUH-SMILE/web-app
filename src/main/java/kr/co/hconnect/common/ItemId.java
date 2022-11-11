package kr.co.hconnect.common;

/**
 * 측정항목 열거자
 */
public enum ItemId {

    /**
     * 체온
     */
    BODY_TEMPERATURE("I0001"),
    /**
     * 혈압
     */
    BLOOD_PRESSURE("I0005"),
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
    RESPIRATION("I0005"),
    /**
     * 호흡
     */
    RESPIRATORY_RATE("I0006")
    ;


    private final String itemId;

    ItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }
}
