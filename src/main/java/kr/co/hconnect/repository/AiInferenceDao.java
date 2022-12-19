package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.*;

import java.util.List;

@Repository
public class AiInferenceDao extends EgovAbstractMapper {

    public void insInf (AiInferenceVO vo) {
        insert("kr.co.hconnect.sqlmapper.insInf", vo);
    }

    public void insInf_log (AiInferenceVO vo) {
        insert("kr.co.hconnect.sqlmapper.insInf_log", vo);
    }

    public void delInf (AiInferenceVO vo) {
        delete("kr.co.hconnect.sqlmapper.udpScore", vo);
    }

    public List<ScoreVO> scoreList() {
        return selectList("kr.co.hconnect.sqlmapper.scoreList");
    }

    public List<ArchiveVO> archiveList(ArchiveVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.archiveList", vo);
    }
    public int udpArchive (ArchiveVO vo) {
        return update("kr.co.hconnect.sqlmapper.udpArchive", vo);
    }
}
