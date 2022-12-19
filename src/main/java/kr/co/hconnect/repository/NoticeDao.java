package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.NoticeListSearchVO;
import kr.co.hconnect.vo.NoticeListVO;
import kr.co.hconnect.vo.NoticeVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 알림 Dao
 */
@Repository
public class NoticeDao extends EgovAbstractMapper {

    /**
     * 신규 알람 카운트 조회 - 격리/입소내역ID 기준
     *
     * @param admissionId 격리/입소내역ID
     * @return int - 신규 알림 Count
     */
    public int selectNoticeUnReadCountByAdmissionId(String admissionId) {
        return selectOne("kr.co.hconnect.sqlmapper.selectNoticeUnReadCountByAdmissionId", admissionId);
    }

    /**
     * 알림 리스트 조회 - 격리/입소내역ID 기준
     *
     * @param vo 알림 리스트 조회 조건
     * @return List&lt;NoticeVO&gt; 알림 리스트
     */
    public List<NoticeVO> selectNoticeListByAdmissionId(NoticeListSearchVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.selectNoticeListByAdmissionId", vo);
    }

    /**
     * 알림 리스트 조회 - 격리/입소내역ID 기준
     *
     * @param vo 알림 리스트 조회 조건
     * @return List&lt;NoticeVO&gt; 알림 리스트
     */
    public List<NoticeListVO> selectnoticeAppList(NoticeListSearchVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.selectnoticeAppList", vo);
    }

    /**
     * 신규 알림 내역 생성
     *
     * @param vo 신규 알림 저장 정보
     * @return affectedRow
     */
    public int insertNotice(NoticeVO vo) {
        return insert("kr.co.hconnect.sqlmapper.insertNotice", vo);
    }

}
