package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.domain.TokenHistory;
import org.springframework.stereotype.Repository;

/**
 * 토큰 발급 이력 관리 Dao
 */
@Repository
public class TokenHistoryDao extends EgovAbstractMapper {

    /**
     * 토큰 발급정보 조회
     * @param token 토큰
     * @return TokenHistory 토큰 발급 내역
     */
    public TokenHistory selectTokenHistory(String token) {
        return selectOne("kr.co.hconnect.sqlmapper.selectTokenHistory", token);
    }

    /**
     * 토큰 발급이력 생성
     * @param tokenHistory 토큰 발급 정보
     */
    public void insertTokenHistory(TokenHistory tokenHistory) {
        insert("kr.co.hconnect.sqlmapper.insertTokenHistory", tokenHistory);
    }

    /**
     * 토큰 삭제
     * @param token 토큰정보
     */
    public void deleteTokenHistory(String token) {
        delete("kr.co.hconnect.sqlmapper.deleteTokenHistory", token);
    }

    /**
     * 만료된 토큰 삭제
     * @param tokenHistory 토큰 발급 정보
     */
    public void deleteTokenHistoryById(TokenHistory tokenHistory) {
        delete("kr.co.hconnect.sqlmapper.deleteTokenHistoryById", tokenHistory);
    }

}
