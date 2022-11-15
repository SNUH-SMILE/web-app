package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.domain.Interview;
import kr.co.hconnect.domain.InterviewDetail;
import kr.co.hconnect.vo.InterviewList;
import kr.co.hconnect.vo.InterviewListResponseByCenterVO;
import kr.co.hconnect.vo.InterviewListSearchVO;
import kr.co.hconnect.vo.SymptomList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InterviewDao  extends EgovAbstractMapper {

    /**
     * 문진 저장
     */
    /*
    * 어떻게 할깝쇼
    * 1. interview를 저장
    * 2. 문진정보를 그뒤에 저장.
    * */

    public int insertInterview(Interview interview) {
        return insert("kr.co.hconnect.sqlmapper.insertInterview", interview);
    }

    public int insertInterviewDetail(InterviewDetail detail) {
        return insert("kr.co.hconnect.sqlmapper.insertInterviewDetail", detail);
    }

    public List<InterviewList> selectInterviewList(Interview interview) {
        return selectList("kr.co.hconnect.sqlmapper.selectInterviewList", interview);
    }
    public List<SymptomList> selectInterviewDetailList(int id) {
        return selectList("kr.co.hconnect.sqlmapper.selectInterviewDetailList", id);
    }


}
