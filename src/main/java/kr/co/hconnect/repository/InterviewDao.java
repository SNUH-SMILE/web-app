package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.domain.Interview;
import kr.co.hconnect.domain.InterviewDetail;
import kr.co.hconnect.vo.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InterviewDao  extends EgovAbstractMapper {

    /**
     * 문진 저장
     */
    public int insertInterview(Interview interview) {
        return insert("kr.co.hconnect.sqlmapper.insertInterview", interview);
    }

    /**
     * 문진 내역 답변 저장
     * @param detail
     * @return
     */
    public int insertInterviewDetail(InterviewDetail detail) {
        return insert("kr.co.hconnect.sqlmapper.insertInterviewDetail", detail);
    }

    /**
     * 해당고객 문진 리스트 조회
     * @param interview
     * @return
     */
    public List<InterviewList> selectInterviewForDateList(Interview interview) {
        return selectList("kr.co.hconnect.sqlmapper.selectInterviewForDateList", interview);
    }
    /**
     * 해당고객 문진 리스트 조회
     * @param interview
     * @return
     */
    public List<InterviewList> selectInterviewList(Interview interview) {
        return selectList("kr.co.hconnect.sqlmapper.selectInterviewList", interview);
    }
    /**
     * 해당고객 문진 답변 증상 리스트 조회
     * @param id
     * @return
     */
    public List<SymptomList> selectInterviewDetailSymptomList(int id) {
        return selectList("kr.co.hconnect.sqlmapper.selectInterviewDetailSymptomList", id);
    }

    /**
     * 문진 질문 리스트 조회
     *
     * @return
     */
    public List<InterviewContentVO> selectInterviewContentList(InterviewSearchVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.selectInterviewContentList", vo);
    }


    public InterviewDetailVO selectInterviewDetailList(InterviewSearchVO vo) {
        return selectOne("kr.co.hconnect.sqlmapper.selectInterviewDetailList", vo);
    }

    /**
     * 확진 당일 문진 리스트
     */
    public List<InterviewAlarmList> selectInterviewToday() {
        return selectList("kr.co.hconnect.sqlmapper.selectInterviewToday");
    }

    /**
     * 매일 문진 리스트
     */
    public List<InterviewAlarmList> selectInterviewDay() {
        return selectList("kr.co.hconnect.sqlmapper.selectInterviewDay");
    }

    /**
     * 퇴소 당일 문진 리스트
     */
    public List<InterviewAlarmList> selectInterviewDischarge() {
        return selectList("kr.co.hconnect.sqlmapper.selectInterviewDischarge");
    }

    /**
     * 퇴소 당일 문진 리스트
     */
    public List<InterviewAlarmList> selectInterviewDischarge30() {
        return selectList("kr.co.hconnect.sqlmapper.selectInterviewDischarge30");
    }

}
