package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.repository.QnaDao;
import kr.co.hconnect.vo.QnaListResponseVO;
import kr.co.hconnect.vo.QnaListSearchVO;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 문의사항 관리 Service
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class QnaService extends EgovAbstractServiceImpl {

    /**
     * 문의사항 Dao
     */
    private final QnaDao qnaDao;
    /**
     * MessageSource
     */
    private final MessageSource messageSource;

    /**
     * 생성자
     *
     * @param qnaDao 문의사항 Dao
     * @param messageSource MessageSource
     */
    public QnaService(QnaDao qnaDao, MessageSource messageSource) {
        this.qnaDao = qnaDao;
        this.messageSource = messageSource;
    }

    /**
     * 문의사항 리스트 조회
     *
     * @param vo 문의사항 리스트 조회 조건
     * @return QnaListResponseVO 문의사항 리스트 조회 결과
     */
    public QnaListResponseVO selectQnaList(QnaListSearchVO vo) {
        QnaListResponseVO qnaListResponseVO = new QnaListResponseVO();

        // 문의사항 리스트 바인딩
        qnaListResponseVO.setQnaVOList(qnaDao.selectQnaList(vo));
        // 전체 페이징 바인딩
        vo.setTotalRecordCount(qnaDao.selectFoundRowsByQna());
        qnaListResponseVO.setPaginationInfoVO(vo);

        return qnaListResponseVO;
    }

}
