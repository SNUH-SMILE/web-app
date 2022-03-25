package kr.co.hconnect.common;

/**
 * 공통코드 열거자
 */
public enum ComCd {
    /**
     * 병원코드
     */
    HOSPITAL_CD("CD002"),
    /**
     * 한양대병원 코드
     */
    HANYANG_HOSP_CODE("CD007"),
    /**
     * 센터내 위치
     */
    LOCATION_IN_CENTER("CD005"),        // 센터내 위치

    /**
     * 테스트 com cd
     */
    TEST_CD("CD999");

    /**
     * DB값
     */
    private final String dbValue;

    /**
     * 생성자
     * @param dbValue DB값
     */
    ComCd(String dbValue) {
        this.dbValue = dbValue;
    }

    /**
     * DB값 조회
     * @return DB값
     */
    public String getDbValue() {
        return dbValue;
    }

}
