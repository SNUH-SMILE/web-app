package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.hconnect.exception.DuplicateDetailCdException;
import kr.co.hconnect.repository.ComCdDao;
import kr.co.hconnect.vo.ComCdDetailSearchVO;
import kr.co.hconnect.vo.ComCdDetailVO;
import kr.co.hconnect.vo.ComCdSearchVO;
import kr.co.hconnect.vo.ComCdVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

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
     * 공통코드 ID 생성 서비스
     */
    private final EgovIdGnrService comCdIdGnrService;

    /**
     * MessageSource
     */
    private final MessageSource messageSource;

    /**
     * 생성자
     *
     * @param comCdDao 공통코드관리 Dao
     * @param comCdIdGnrService 공통코드 ID 생성 서비스
     * @param messageSource MessageSource
     */
    public ComCdService(ComCdDao comCdDao, @Qualifier("comCdGnrService") EgovIdGnrService comCdIdGnrService, MessageSource messageSource) {
        this.comCdDao = comCdDao;
        this.comCdIdGnrService = comCdIdGnrService;
        this.messageSource = messageSource;
    }

    /**
     * 공통코드 리스트 조회
     *
     * @param vo 공통코드 조회 조건 VO
     * @return List&lt;ComCdVO&gt; 공통코드 리스트
     */
    public List<ComCdVO> selectComCdList(ComCdSearchVO vo) {
        return comCdDao.selectComCdList(vo);
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

    /**
     * 공통코드 저장
     * @param comCdVOList 공통코드 저장 정보
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveComCd(List<ComCdVO> comCdVOList) throws FdlException, NullPointerException {
        for (ComCdVO comCdVO : comCdVOList) {
            if (comCdVO.getCudFlag().equals("C")) {
                comCdVO.setComCd(comCdIdGnrService.getNextStringId());
                comCdDao.insertComCd(comCdVO);
            } else if (comCdVO.getCudFlag().equals("U")) {
                // 업데이트 대상 공통코드 정보 존재 여부 확인
                ComCdSearchVO comCdSearchVO = new ComCdSearchVO();
                comCdSearchVO.setComCd(comCdVO.getComCd());
                if (StringUtils.isEmpty(comCdVO.getComCd()) || comCdDao.selectComCd(comCdSearchVO) == null) {
                    if (StringUtils.isNotEmpty(comCdVO.getComCdNm())) {
                        throw new NullPointerException(messageSource.getMessage("message.notfound.comCdInfo.comCdNm"
                                , new Object[] { comCdVO.getComCdNm() }, Locale.getDefault()));
                    } else {
                        throw new NullPointerException(messageSource.getMessage("message.notfound.comCdInfo"
                                , null, Locale.getDefault()));
                    }
                }

                comCdDao.updateComCd(comCdVO);
            }
        }
    }

    /**
     * 공통코드상세 저장
     * @param comCdDetailVOList 공통코드상세 저장 정보
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveComCdDetail(List<ComCdDetailVO> comCdDetailVOList) throws DuplicateDetailCdException {
        for (ComCdDetailVO comCdDetailVO : comCdDetailVOList) {
            if (comCdDetailVO.getCudFlag().equals("C")) {
                // 세부코드 중복 확인
                ComCdDetailSearchVO comCdDetailSearchVO = new ComCdDetailSearchVO();
                comCdDetailSearchVO.setComCd(comCdDetailVO.getComCd());
                comCdDetailSearchVO.setDetailCd(comCdDetailVO.getDetailCd());
                if (comCdDao.selectComCdDetail(comCdDetailSearchVO) != null) {
                    throw new DuplicateDetailCdException(messageSource.getMessage("message.duplicate.detailCd"
                            , new Object[] { comCdDetailVO.getDetailCd() }, Locale.getDefault()));
                }

                comCdDao.insertComCdDetail(comCdDetailVO);
            } else if (comCdDetailVO.getCudFlag().equals("U")) {
                comCdDao.updateComCdDetail(comCdDetailVO);
            }
        }
    }
}
