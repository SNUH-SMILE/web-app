package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.domain.Interview;
import kr.co.hconnect.domain.InterviewDetail;
import kr.co.hconnect.domain.QantnStatus;
import kr.co.hconnect.domain.SaveInformaionInfo;
import org.springframework.stereotype.Repository;

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


}
