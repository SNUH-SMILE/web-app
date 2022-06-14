package kr.co.hconnect.common;

/**
 * 격리/입소구분 COM_CD='CD004'
 */
public enum QantnDiv {
    /**
     * 대상자 아님
     */
    NONE("0"),
    /**
     * 자각겨리
     */
    ISOLATION("1"),
    /**
     * 생활치료센터 입소자
     */
    CENTER("2");

    /**
     * DB값
     */
    private final String dbValue;

    /**
     * 생서자
     * @param dbValue DB값
     */
    QantnDiv(String dbValue) {
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
