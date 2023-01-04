package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;
import kr.co.hconnect.vo.*;

import java.util.List;

@Repository
public class AiInferenceDao extends EgovAbstractMapper {

    public void insInf (AiInferenceVO vo) {
        insert("kr.co.hconnect.sqlmapper.insInf", vo);
    }

    public void insInf_log (AiInferenceVO vo) {
        insert("kr.co.hconnect.sqlmapper.insInfLog", vo);
    }

    public void delInf (AiInferenceVO vo) {
        delete("kr.co.hconnect.sqlmapper.delInf", vo);
    }

    public List<ScoreVO> scoreList() {
        return selectList("kr.co.hconnect.sqlmapper.scoreList");
    }

    public List<ArchiveVO> archiveList(ArchiveVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.archiveList", vo);
    }
    public int udpArchiveDown (ArchiveVO vo) {
        return update("kr.co.hconnect.sqlmapper.udpArchiveDown", vo);
    }

    public List<TemperListVO> temperList() {
        return selectList("kr.co.hconnect.sqlmapper.temperList");
    }

    public List<DepressListVO> depressList() {
        return selectList("kr.co.hconnect.sqlmapper.depressList");
    }

    public int insArchiveDown(ArchiveDownVO vo) {
        return insert("kr.co.hconnect.sqlmapper.insArchiveDown", vo);
    }
    public List<ArchiveDownVO> selectVoiceList() {
        return selectList("kr.co.hconnect.sqlmapper.selectVoiceList");
    }

    //퇴소일이 오늘 보다 큰 입소자
    public List<BioCheckVO> bioAdmissionId() {
        return selectList("kr.co.hconnect.sqlmapper.bioAdmissionId");
    }

    //퇴소일이 오늘 보다 큰 입소자의 바이오 데이터 체크
    public int bioCheck(BioCheckVO vo) {
        return selectOne("kr.co.hconnect.sqlmapper.bioCheck", vo);
    }

    //퇴소일이 오늘 보다 큰 입소자의 문진 데이터
    public int interviewCheck(BioCheckVO vo) {
        return selectOne("kr.co.hconnect.sqlmapper.interviewCheck", vo);
    }

}
