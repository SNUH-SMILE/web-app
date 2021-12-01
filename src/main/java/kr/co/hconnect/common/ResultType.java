package kr.co.hconnect.common;

/**
 * 측정결과 유형 - 공통코드(CD007)
 */
public enum ResultType {
    /**
     * 측정결과
     */
    SINGLE_RESULT("01"),
    /**
     * 최저혈압
     */
    MINIMUM_BLOOD_PRESSURE("02"),
    /**
     * 최고혈압
     */
    MAXIMUM_BLOOD_PRESSURE("03"),
    /**
     * 걸음수
     */
    STEP_COUNT("04"),
    /**
     * 거리
     */
    DISTANCE("05");

    /**
     * 측정결과유형
     */
    private final String resultType;

    /**
     * 생성자
     *
     * @param resultType 측정결과유형
     */
    ResultType(String resultType) {
        this.resultType = resultType;
    }

    /**
     * 측정결과유형 조회
     *
     * @return DB값
     */
    public String getResultType() {
        return resultType;
    }
}
