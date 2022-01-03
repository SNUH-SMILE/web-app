package kr.co.hconnect.domain;

import java.util.List;

/**
 * 측정결과 저장정보 인터페이스
 */
public interface SaveResult {

    /**
     * 로그인ID
     * @return String LOGIN_ID
     */
    String getLoginId();

    /**
     * 측정결과 리스트
     * @return List&lt;ResultValue&gt;
     */
    List<ResultValue> getResultList();
}
