package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.repository.QnaDao;
import kr.co.hconnect.vo.QnaListResponseVO;
import kr.co.hconnect.vo.QnaListSearchVO;
import kr.co.hconnect.vo.QnaSaveByReplyVO;
import kr.co.hconnect.vo.QnaVO;
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
     * 생성자
     *
     * @param qnaDao 문의사항 Dao
     */
    public QnaService(QnaDao qnaDao) {
        this.qnaDao = qnaDao;
    }

    /**
     * 문의사항 내역 조회
     *
     * @param questionSeq 문의순번
     * @return QnaVO 문의사항 내역
     */
    public QnaVO selectQna(int questionSeq) {
        return qnaDao.selectQna(questionSeq);
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

    /**
     * 문의사항 답변 등록
     *
     * @param vo 답변 정보
     * @return affectedRow
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateQnaByReply(QnaSaveByReplyVO vo) {
        QnaVO qnaVO = new QnaVO();
        qnaVO.setQuestionSeq(vo.getQuestionSeq());
        qnaVO.setReplyContent(vo.getReplyContent());
        qnaVO.setReplyId(vo.getReplyId());

        return qnaDao.updateQnaByReply(qnaVO);
    }

    /**
     * 문의사항 답변 삭제
     *
     * @param questionSeq 문의순번
     * @return affectedRow
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteQnaByReply(Integer questionSeq) {
        return qnaDao.deleteQnaByReply(questionSeq);
    }

}
