package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.HealthVO;
import org.springframework.stereotype.Repository;
import kr.co.hconnect.vo.*;

import javax.validation.Valid;
import java.util.List;

@Repository
public class HealthDao extends EgovAbstractMapper{
    /**
     * 몸 운동 상태 저장
     * @param vo
     */
    public void inserthelth(HealthVO vo) {
        insert("kr.co.hconnect.sqlmapper.inserthelth", vo);
    }

    public HealthResponseVO selectHelth(HealthSearchVO vo)  {
        return selectOne("kr.co.hconnect.sqlmapper.selectHelth", vo);
    }



}
