package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;
import kr.co.hconnect.vo.*;

import javax.validation.Valid;
import java.util.List;

@Repository
public class SupportDao extends EgovAbstractMapper{
    public List<NoticeSupportListVO> selectNoticeList(NoticeSupportSearchVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.selectNoticeList", vo);
    }
}
