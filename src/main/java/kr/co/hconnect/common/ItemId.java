package kr.co.hconnect.common;

/**
 * 측정항목 열거자
 */
public enum ItemId {

    BLOOD_PRESSURE("I0025"),
    BODY_TEMPERATURE("I0027"),
    HEART_RATE("I0026"),
    OXYGEN_SATURATION("I0029"),
    RESPIRATION("I0028");


    private final String itemId;

    ItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }
}
