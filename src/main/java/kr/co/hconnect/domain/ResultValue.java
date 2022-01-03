package kr.co.hconnect.domain;

/**
 * 측정결과세부 인터페이스
 */
public interface ResultValue {
    String getResult();

    default String getResult2() {
        return null;
    }
}
