package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.repository.ComCdDao;
import kr.co.hconnect.vo.ComCdDetailSearchVO;
import kr.co.hconnect.vo.ComCdDetailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 공통코드관리 Service
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class ComCdService extends EgovAbstractServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComCdService.class);

    /**
     * 공통코드관리 Dao
     */
    private final ComCdDao comCdDao;


    /**
     * 생성자
     *
     * @param comCdDao 공통코드관리 Dao
     */
    public ComCdService(ComCdDao comCdDao) {
        this.comCdDao = comCdDao;
    }

    /**
     * 공통코드상세 리스트 조회
     *
     * @param vo 공통코드상세 조회조건 VO
     * @return List&lt;ComCdDetailVO&gt; 공통코드상세 리스트
     */
    public List<ComCdDetailVO> selectComCdDetailList(ComCdDetailSearchVO vo) {
        return comCdDao.selectComCdDetailList(vo);
    }
}
