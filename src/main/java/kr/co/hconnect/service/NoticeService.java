package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.HttpUtil;
import kr.co.hconnect.exception.NotFoundAdmissionInfoException;
import kr.co.hconnect.repository.AdmissionDao;
import kr.co.hconnect.repository.NoticeDao;
import kr.co.hconnect.repository.UserDao;
import kr.co.hconnect.vo.AdmissionInfoVO;
import kr.co.hconnect.vo.NoticeListSearchVO;
import kr.co.hconnect.vo.NoticeListVO;
import kr.co.hconnect.vo.NoticeVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * 알림 Service
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class NoticeService extends EgovAbstractServiceImpl {

    /**
     * 알림 Dao
     */
    private final NoticeDao noticeDao;
    /**
     * 입소내역 Dao
     */
    private final AdmissionDao admissionDao;
    /**
     * MessageSource
     */
    private final MessageSource messageSource;

    private final UserDao userDao;

    @Value("${push.url}")
    private String push_url;

    /**
     * 생성자
     * @param noticeDao 알림 Dao
     * @param admissionDao 입소내역 Dao
     * @param messageSource MessageSource
     */
    public NoticeService(NoticeDao noticeDao, AdmissionDao admissionDao, MessageSource messageSource, UserDao userDao) {
        this.noticeDao = noticeDao;
        this.admissionDao = admissionDao;
        this.messageSource = messageSource;
        this.userDao = userDao;
    }

    /**
     * 신규 알림 내역 추가
     *
     * @param vo 신규 알림 저장 정보
     * @return int 알림순번(NOTICE_SEQ)
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertNotice(NoticeVO vo) throws NotFoundAdmissionInfoException {
        // 입소내역 존재여부 확인
        AdmissionInfoVO admissionInfoVO = admissionDao.selectAdmissionInfo(vo.getAdmissionId());
        if (admissionInfoVO == null || admissionInfoVO.getDelYn().equals("Y")) {
            throw new NotFoundAdmissionInfoException(ApiResponseCode.NOT_FOUND_ADMISSION_INFO.getCode()
                    , messageSource.getMessage("message.notfound.admissionInfo"
                    , null, Locale.getDefault()));
        }

        noticeDao.insertNotice(vo);



        String admissionId = vo.getAdmissionId();
        String cuid = userDao.selectPationtLoginId(admissionId);

        HashMap<String, Object> mapValue = new HashMap<String, Object>();
        mapValue.put("CUID", cuid);
        mapValue.put("MESSAGE", vo.getNotice());

        //푸시서비스
        int rtn = sendPush(mapValue);


        return vo.getNoticeSeq();
    }

    /**
     * 알림 리스트 조회 - 격리/입소내역ID 기준
     *
     * @param vo 알림 리스트 조회 조건
     * @return List&lt;NoticeVO&gt; 알림 리스트
     */
    public List<NoticeListVO> selectnoticeAppList(NoticeListSearchVO vo) {
        return noticeDao.selectnoticeAppList(vo);
    }


    /**
     * 알림 리스트 조회 - 격리/입소내역ID 기준
     *
     * @param vo 알림 리스트 조회 조건
     * @return List&lt;NoticeVO&gt; 알림 리스트
     */
    public List<NoticeVO> selectNoticeList(NoticeListSearchVO vo) {
        return noticeDao.selectNoticeListByAdmissionId(vo);
    }

    /**
     * 알람 발송 푸시 서비스
     */

    public int sendPush (HashMap<String, Object> mapValue){
        int rtn = 0;

        try {

            String cuid = mapValue.get("CUID").toString();
            String msg =  mapValue.get("MESSAGE").toString();

            String message =
                "{\n" +
                    "    \"title\": \"안녕하세요\",\n" +
                    "    \"body\": \"" + msg + "\"\n" +
                    "}";

            HashMap<String, Object> params = new HashMap<String , Object>();
            params.put("CUID", cuid);
            params.put("MESSAGE", message);
            params.put("PRIORITY", "3");
            params.put("BADGENO", "0");
            params.put("RESERVEDATE", "");
            params.put("SERVICECODE", "ALL");
            params.put("SOUNDFILE", "alert.aif");
            params.put("EXT", "");
            params.put("SENDERCODE", "smile");
            params.put("APP_ID", "iitp.infection.pm");
            params.put("TYPE", "E");
            params.put("DB_IN", "Y");

/*
            HashMap<String, Object> result = new HttpUtil()
                .url("http://192.168.42.193:8380/upmc/rcv_register_message.ctl")
                .method("POST")
                .body(params)
                .build();
*/

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
