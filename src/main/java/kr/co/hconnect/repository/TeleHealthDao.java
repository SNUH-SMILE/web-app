package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;
import kr.co.hconnect.vo.*;

import javax.validation.Valid;
import java.util.List;

@Repository
public class TeleHealthDao extends EgovAbstractMapper{

    /**
     * 신규세션 저장
     * @param vo
     */
    public int insertSession(TeleHealthConnectVO vo) {
        System.out.println("insertSession");
        return insert("kr.co.hconnect.sqlmapper.insertSession", vo);
    }

    /**
     * 자택격리자 토큰 업데이트
     * @param vo
     */
    public int  udpSubscriberToken(TeleHealthConnectVO vo) {
        return update("kr.co.hconnect.sqlmapper.udpSubscriberToken", vo);
    }

    /**
     * 기존 세션이 있는지 확인 쿼리
     * @param vo
     * @return
     */
    public TeleHealthConnectVO selectTeleSession(TeleHealthConnectVO vo) {
        return selectOne("kr.co.hconnect.sqlmapper.selectTeleSession", vo);
    }

    /**
     * 구독자 ( 자택격리자 의 생성된 토큰 조회 쿼리)
     * @param vo
     * @return
     */
    public TeleHealthConnectVO selectSubscriber(TeleHealthConnectVO vo) {
        return selectOne("kr.co.hconnect.sqlmapper.selectSubscriber", vo);
    }

    /**
     * 구독자 ( 자택격리자 의 생성된 토큰 조회 쿼리)
     * @param vo
     * @return
     */
    public int endSession(TeleHealthSearchVO vo) {
        return update("kr.co.hconnect.sqlmapper.endSession", vo);
    }

    /**
     * 화상다운로드 아카이브 아이디 찾기
     * @param vo
     * @return
     */
    public String getArchiveId(TeleReqArchiveDownVO vo) {
        return selectOne("kr.co.hconnect.sqlmapper.getArchiveId", vo);
    }


}
