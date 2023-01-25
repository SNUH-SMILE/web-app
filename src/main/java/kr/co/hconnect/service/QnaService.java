package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.common.HttpUtil;
import kr.co.hconnect.repository.QnaDao;
import kr.co.hconnect.repository.UserDao;
import kr.co.hconnect.vo.QnaListResponseVO;
import kr.co.hconnect.vo.QnaListSearchVO;
import kr.co.hconnect.vo.QnaSaveByReplyVO;
import kr.co.hconnect.vo.QnaVO;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

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

    private final UserDao userDao;

    @Value("${push.url}")
    private String push_url;

    /**
     * 생성자
     *
     * @param qnaDao 문의사항 Dao
     */
    public QnaService(QnaDao qnaDao, UserDao userDao) {
        this.qnaDao = qnaDao;
        this.userDao = userDao;
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

        int udp =  qnaDao.updateQnaByReply(qnaVO);

        System.out.println("getPatientId   >>>>>>>>>>>>>>>>>" + vo.getPatientId());



        if (udp != 0) {

            String CUID = userDao.selectLoginId(vo.getPatientId());   //환자아이디로 아이디 찾기
            System.out.println("CUID >>>>>>>>>>>>>>>>>" + CUID);
            HashMap<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("CUID", CUID);
            mapValue.put("MESSAGE", "답변이 완료 되었습니다.");
            mapValue.put("QUESTIONSEQ", vo.getQuestionSeq());  //순번

            //#푸시내역 생성
            int ret = sendPush(mapValue);

        }

        return udp;

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


    public int sendPush (HashMap<String, Object> mapValue){
        int rtn = 0;
        try {

            String cuid = mapValue.get("CUID").toString();
            String msg =  mapValue.get("MESSAGE").toString();
            String seq =  mapValue.get("QUESTIONSEQ").toString();

            String message = "{\n" +
                "    \"title\": \" 안녕하세요 \",\n" +
                "    \"body\": \"" + msg + "\"\n" +
                "}";

            String attendeeToken = "{\n" +
                "    \"action\": \"question\",\n" +
                "    \"questionSeq\" : \"" + seq + "\", \n" +
                "    }\n" +
                "}";


            HashMap<String, Object> params = new HashMap<String , Object>();
            params.put("CUID", cuid);
            params.put("MESSAGE", message);
            params.put("PRIORITY", "3");
            params.put("BADGENO", "0");
            params.put("RESERVEDATE", "");
            params.put("SERVICECODE", "ALL");
            params.put("SOUNDFILE", "alert.aif");
            params.put("EXT", attendeeToken);
            params.put("SENDERCODE", "smile");
            params.put("APP_ID", "iitp.infection.pm");
            params.put("TYPE", "E");
            params.put("DB_IN", "Y");



            HashMap<String, Object> result = new HttpUtil()
                .url(push_url)
                .method("POST")
                .body(params)
                .build();
        } catch (Exception e){
            rtn=1;
            System.out.println(e.getMessage());

        }

        return rtn;
    }


}
