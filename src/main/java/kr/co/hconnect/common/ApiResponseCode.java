package kr.co.hconnect.common;

/**
 * API 결과코드 정보
 */
public enum ApiResponseCode {

    /**
     * 정상 처리
     */
    SUCCESS("00"),
    /**
     * 로그인 비밀번호 불일치
     */
    NOT_MATCH_PATIENT_PASSWORD("10"),
    /**
     * 환자정보가 존재하지 않는 경우
     */
    NOT_FOUND_PATIENT_INFO("11"),
    /**
     * 동일한 환자정보가 존재하는 경우
     */
    DUPLICATE_PATIENT_INFO("12"),
    /**
     * 동일한 로그인ID 존재하는 경우
     */
    DUPLICATE_PATIENT_LOGIN_ID("13"),
    /**
     * 격리상태 내역이 존재하지 않는 경우
     */
    NOT_FOUND_QUARANTINE_INFO("14"),
    /**
     * 사용자 정보가 존재하지 않는 경우
     */
    NOT_FOUND_USER_INFO("15"),
    /**
     * 동일한 휴대폰 번호가 존재하는 경우
     */
    DUPLICATE_PATIENT_CELL_PHONE("16"),

    /**
     * 내원중인 격리/입소내역이 존재하지 않는 경우
     */
    NOT_FOUND_ADMISSION_INFO("21"),


    /**
     * 화상상담 다운로드 파일이 존해 하지 않을 경우
     */
    NOT_FOUND_ARCHIVE_INFO("31"),
    /**
     * 내원중인 격리/입소내역이 중복된 경우
     */
    DUPLICATE_ACTIVE_ADMISSION_INFO("22"),

    /**
     * 토큰정보 만료
     */
    EXPIRED_TOKEN("80"),
    /**
     * 토큰정보가 존재하지 않습니다
     */
    INVALID_TOKEN("81"),
    /**
     * 토큰정보가 유효하지 않습니다
     */
    ILLEGAL_TOKEN("82"),


    /**
     * 유효하지 않은 요청 파라메터 코드
     */
    CODE_INVALID_REQUEST_PARAMETER("99");

    private final String code;

    /**
     * 생성자
     * @param code 반환 코드
     */
    ApiResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
