package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.domain.TokenHistory;
import kr.co.hconnect.repository.TokenHistoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 토큰 발급 이력 Service
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class TokenHistoryService extends EgovAbstractServiceImpl {

    private final TokenHistoryDao tokenHistoryDao;

    /**
     * 생성자
     * @param tokenHistoryDao 토큰 발급 이력 Dao
     */
    @Autowired
    public TokenHistoryService(TokenHistoryDao tokenHistoryDao) {
        this.tokenHistoryDao = tokenHistoryDao;
    }

    /**
     * 토큰 발급정보 조회
     * @param token 토큰
     * @return TokenHistory 토큰 발급 내역
     */
    public TokenHistory selectTokenHistory(String token) {
        return tokenHistoryDao.selectTokenHistory(token);
    }

    /**
     * 토큰 삭제
     * @param token 토큰정보
     */
    public void deleteTokenHistory(String token) {
        tokenHistoryDao.deleteTokenHistory(token);
    }

    /**
     * 토큰 발급 이력 생성
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertTokenHistory(TokenHistory tokenHistory) {
        // 01. 만료 토큰 삭제
        tokenHistoryDao.deleteTokenHistoryById(tokenHistory);

        // 02. 발급 이력 생성
        tokenHistoryDao.insertTokenHistory(tokenHistory);
    }
}
