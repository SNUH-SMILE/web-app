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

    public int udpArchiveDownYn(ArchiveDownVO vo) {
        return update("kr.co.hconnect.sqlmapper.udpArchiveDownYn", vo);
    }
    public int delArchiveDown(ArchiveDownVO vo) {
        return delete("kr.co.hconnect.sqlmapper.delArchiveDown", vo);
    }
    public List<ArchiveDownVO> selectVoiceList() {
        return selectList("kr.co.hconnect.sqlmapper.selectVoiceList");
    }

    //퇴소일이 오늘 보다 큰 입소자
    public List<BioCheckVO> bioAdmissionId() {
        return selectList("kr.co.hconnect.sqlmapper.bioAdmissionId");
    }

    //퇴소일이 오늘 보다 큰 입소자
    public List<BioCheckVO> bioAdmissionIdBefore() {
        return selectList("kr.co.hconnect.sqlmapper.bioAdmissionIdBefore");
    }


    //퇴소일이 오늘 보다 큰 입소자의 바이오 데이터 체크
    public String bioCheck(BioCheckVO vo) {
        return selectOne("kr.co.hconnect.sqlmapper.bioCheck", vo);
    }

    //2시간안에 데이터가 있는지 체크
    public String bioTimeCheck(BioCheckVO vo) {

        return selectOne("kr.co.hconnect.sqlmapper.bioTimeCheck", vo);
    }


    //퇴소일이 오늘 보다 큰 입소자의 문진 데이터
    public String interviewCheck(BioCheckVO vo) {
        return selectOne("kr.co.hconnect.sqlmapper.interviewCheck", vo);
    }

    public int insBioError(BioErrorVO vo) {
        return insert("kr.co.hconnect.sqlmapper.insBioError", vo);
    }

    public String videoCheck(BioCheckVO vo) {

        return selectOne("kr.co.hconnect.sqlmapper.videoCheck", vo);
    }

    public String audioCheck(BioCheckVO vo) {

        return selectOne("kr.co.hconnect.sqlmapper.audioCheck", vo);
    }
    /**
     *  퇴소 예정일 => 퇴소일 로 업데이트
     *  조건 : DSCHGE_SCHDLD_DATE = DATE_FORMAT(DATE_ADD(NOW(), INTERVAL -1 DAY), '%Y-%m-%d')
     *  2023-02-14
     * @return  int
     */
    public int udpAdmissionDischarge() {
        return update("kr.co.hconnect.sqlmapper.udpAdmissionDischarge");
    }
}
