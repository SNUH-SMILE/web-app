package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.AdmissionVO;
import kr.co.hconnect.vo.TeleHealthArchiveVO;
import org.springframework.stereotype.Repository;

@Repository
public class ArchiveDao extends EgovAbstractMapper {
    /**
     * 화상진료 저장데이터 생성
     * @param
     * @return
     */
    public int archiveInsert(TeleHealthArchiveVO vo) {
        return insert("kr.co.hconnect.sqlmapper.insertArchive", vo);
    }
    /**
     * 화상진료 상태 수정
     * @param
     * @return
     */
    public int updateArchive(TeleHealthArchiveVO vo) {
        return update("kr.co.hconnect.sqlmapper.updateArchiveStatus", vo);
    }
}
