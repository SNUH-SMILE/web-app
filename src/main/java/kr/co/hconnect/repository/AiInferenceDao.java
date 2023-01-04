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

    public List<ScoreVO> scoreList(BatchVO vo) {

        return selectList("kr.co.hconnect.sqlmapper.scoreList", vo);
    }

    public List<ArchiveVO> archiveList(ArchiveVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.archiveList", vo);
    }
    public int udpArchiveDown (ArchiveVO vo) {
        return update("kr.co.hconnect.sqlmapper.udpArchiveDown", vo);
    }

    public List<TemperListVO> temperList(BatchVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.temperList", vo);
    }

    public List<DepressListVO> depressList(BatchVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.depressList", vo);
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

    //2시간안에 데이터가 있는지 체크
    public int bioTimeCheck(BioCheckVO vo) {

        return selectOne("kr.co.hconnect.sqlmapper.bioTimeCheck", vo);
    }


    //퇴소일이 오늘 보다 큰 입소자의 문진 데이터
    public int interviewCheck(BioCheckVO vo) {
        return selectOne("kr.co.hconnect.sqlmapper.interviewCheck", vo);
    }

    public int insBioError(BioErrorVO vo) {
        return insert("kr.co.hconnect.sqlmapper.insBioError", vo);
    }



}
