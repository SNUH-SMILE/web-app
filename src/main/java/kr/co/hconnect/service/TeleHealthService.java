package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.controller.HealthController;
import kr.co.hconnect.repository.ArchiveDao;
import kr.co.hconnect.repository.UserDao;
import kr.co.hconnect.repository.TeleHealthDao;
import kr.co.hconnect.vo.*;
import kr.co.hconnect.common.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.opentok.Archive.OutputMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.opentok.*;
import com.opentok.exception.OpenTokException;
import com.opentok.Archive;

/**
 * 화상상담 서비스
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class TeleHealthService extends EgovAbstractServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(HealthController.class);
    private final ArchiveDao archiveDao;
    private final UserDao userDao;
    private final TeleHealthDao teleHealthDao;

    @Autowired
    public TeleHealthService(ArchiveDao archiveDao, UserDao userDao, TeleHealthDao teleHealthDao) {
        this.archiveDao = archiveDao;
        this.userDao = userDao;
        this.teleHealthDao = teleHealthDao;
    }

    int apikey = 47595911;
    String apiSecret = "2ddde1eb92a2528bd22be0c465174636daca363d";

    /**
     *화상상담
     * @param vo 측정항목VO
     */
    @Transactional(rollbackFor = Exception.class)
    public TeleHealthConnectVO selectConnection (TeleHealthConnectVO vo){
        TeleHealthConnectVO  teleVO = new TeleHealthConnectVO();
        BeanUtils.copyProperties(vo, teleVO);

        OpenTok openTok = null;
            try {
                vo.setApiKey(apikey);
                vo.setApiSecret(apiSecret);

                //# 세션 생성
                openTok = new OpenTok(vo.getApiKey(), vo.getApiSecret());
                SessionProperties sessionProperties = new SessionProperties.Builder()
                    .mediaMode(MediaMode.ROUTED)
                    .build();
                Session session = openTok.createSession(sessionProperties);
                String ssid  = session.getSessionId();


                String guardianMetaDataFormat = "loginId=%s&admissionId=%s&clientType=pc";
                String metaData;

                metaData = String.format(guardianMetaDataFormat
                    , vo.getLoginId()
                    , ""
                );

                //화상상담 개설자  토큰생성
                TokenOptions tokenOptions = new TokenOptions.Builder()
                    .role(Role.MODERATOR)
                    .data(metaData)
                    .build();

                String ofToken = openTok.generateToken(ssid, tokenOptions);

                log.info("화상상담 시작정보 저장");

                //# 화상상담 시작정보 저장
                teleVO.setLoginId(vo.getLoginId());
                teleVO.setSessionId(ssid);
                teleVO.setOfficerToken(ofToken);
                teleVO.setAdmissionId(vo.getAdmissionId());

                int rtn = teleHealthDao.insertSession(teleVO);


                //# 참석자(대상자 & 보호자)에게 푸시내역 생성
                createTelehealthStartPush(teleVO);

            } catch (OpenTokException e){
                System.out.println(e.getMessage());
            }
        return teleVO;
    }
    @Transactional(rollbackFor = Exception.class)
    public TeleHealthConnectVO getSubscriberToken (TeleHealthConnectVO vo){
        TeleHealthConnectVO teleEntity = new TeleHealthConnectVO();

        BeanUtils.copyProperties(vo, teleEntity);

        String metaData = vo.getLoginId();
        String sessionid = vo.getSessionId();
        System.out.println("sessionid=================================================");
        System.out.println(sessionid);
        System.out.println("=================================================");
        OpenTok openTok = null;
        try {

            vo.setApiKey(apikey);
            vo.setApiSecret(apiSecret);

            openTok = new OpenTok(vo.getApiKey(), vo.getApiSecret());

            //# 담당자 또는 참석자의 토큰 생성
            TokenOptions tokenOptions = new TokenOptions.Builder()
                .role(Role.MODERATOR)
                .data(metaData)
                .build();

            System.out.println("generateToken =================================================");
            System.out.println(openTok.generateToken(sessionid
                , tokenOptions));
            System.out.println("=================================================");


            //화상상담 참가자  토큰생성
            teleEntity.setAttendeeToken(openTok.generateToken(sessionid));


            //# 화상상담 시작정보 저장
            //saveStartTelehealthInfo(teleEntity);

            //# 참석자(대상자 & 보호자)에게 푸시내역 생성
            //createTelehealthStartPush(teleEntity);


        } catch (OpenTokException e){
            System.out.println(e.getMessage());
        }
        return teleEntity;
    }
    @Transactional(rollbackFor = Exception.class)
    public String archive (TeleHealthConnectVO vo) {
        OpenTok openTok =null;
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
        Archive archive;
        try {

            vo.setApiKey(apikey);
            vo.setApiSecret(apiSecret);

            //# 세션 생성
            openTok = new OpenTok(vo.getApiKey(), vo.getApiSecret());
            OutputMode outputMode = OutputMode.INDIVIDUAL;
            ArchiveLayout layout = null;

            //outputMode = OutputMode.COMPOSED;
            //layout = new ArchiveLayout(ArchiveLayout.Type.HORIZONTAL);

            archive = openTok.startArchive(vo.getSessionId(), new ArchiveProperties.Builder()
                .name(vo.getAdmissionId())
                .hasAudio(true)
                .hasVideo(true)
                .outputMode(outputMode)
                .layout(layout)
                .build());
            String archiveId = archive.getId();

            TeleHealthArchiveVO teleHealthArchiveVO =new TeleHealthArchiveVO();
            teleHealthArchiveVO.setArchiveId(archiveId);
            teleHealthArchiveVO.setName(archive.getName());
            teleHealthArchiveVO.setReason(archive.getReason());
            teleHealthArchiveVO.setSize(archive.getSize());
            Date archiveDate = new Date(archive.getCreatedAt());
            teleHealthArchiveVO.setCreateAt(archiveDate);
            teleHealthArchiveVO.setPartnerId(archive.getPartnerId());
            teleHealthArchiveVO.setSessionId(archive.getSessionId());
            teleHealthArchiveVO.setStatus(archive.getStatus().toString());
            teleHealthArchiveVO.setOutputMode(archive.getOutputMode().toString());

            archiveDao.archiveInsert(teleHealthArchiveVO);

        } catch (OpenTokException e) {
            e.printStackTrace();
            return null;
        }
        return archive.getId();
    }
    @Transactional(rollbackFor = Exception.class)
    public void archiveStop (TeleHealthArchiveVO vo) {

        OpenTok openTok =null;
        Archive archive;
        //# 세션 생성
        try {
            openTok = new OpenTok(apikey, apiSecret);
            archive = openTok.stopArchive(vo.getArchiveId());
            vo.setArchiveId(archive.getId());
            vo.setStatus(archive.getStatus().toString());
            vo.setSize(archive.getSize());
            vo.setReason(archive.getReason());

            archiveDao.updateArchive(vo);

        }catch (OpenTokException e){
            e.printStackTrace();
        }
    }


    /**
     * 화상 접속시 푸시발송
     * @param vo
     */
    public void createTelehealthStartPush(TeleHealthConnectVO vo){
        //텔레헬스

        log.info(("화상접속 클라이언트 토큰생성 및 푸시 발송"));

        String admissionId = vo.getAdmissionId();
        log.info("admissionId" + admissionId);

        String CUID = userDao.selectPationtLoginId(admissionId);
        log.info("CUID" + CUID);

        String message = "화상진료를 시작합니다. 참여 부탁드립니다!";
        String sessionId  = vo.getSessionId();

        TeleHealthConnectVO  teleSsubVO = new TeleHealthConnectVO();

        OpenTok openTok = null;
        try {
            vo.setApiKey(apikey);
            vo.setApiSecret(apiSecret);

            String subjectMetaDataFormat = "loginId=%s&admissionId=%s&clientType=mobile";
            String metaData;
            //보호자면
                metaData = String.format(subjectMetaDataFormat
                    , ""
                    , admissionId
                );

            openTok = new OpenTok(vo.getApiKey(), vo.getApiSecret());

            TokenOptions tokenOpts = new TokenOptions.Builder()
                .role(Role.MODERATOR)
                .data(metaData)
                .build();

            String attendeeToken = openTok.generateToken(sessionId, tokenOpts);

            log.info("sessionId" + sessionId);
            log.info("attendeeToken" + attendeeToken);

            //생성된 구독자 토큰 저장
            teleSsubVO.setLoginId(vo.getLoginId());
            teleSsubVO.setApiKey(apikey);
            teleSsubVO.setSessionId(sessionId);
            teleSsubVO.setAttendeeToken(attendeeToken);
            teleSsubVO.setAdmissionId(vo.getAdmissionId());

            int rtn = teleHealthDao.udpSubscriberToken(teleSsubVO);

            HashMap<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("CUID", CUID);
            mapValue.put("MESSAGE", message);
            mapValue.put("apikey", apikey);
            mapValue.put("sessionId", vo.getSessionId());
            mapValue.put("attendeeToken", attendeeToken);

            //#푸시내역 생성
            int ret = sendPush(mapValue);

        } catch (OpenTokException e){
            log.error(e.getMessage());
        }



    }


    /**
     * 푸시발송
     * 화상시작시 고객에게 푸시 발송한다.
     * @param mapValue
     * @return
     */

    public int sendPush (HashMap<String, Object> mapValue){
        int rtn = 0;

        try {

            String cuid = mapValue.get("CUID").toString();
            String msg =  mapValue.get("MESSAGE").toString();

            String apikey =  mapValue.get("apikey").toString();
            String sessionID =  mapValue.get("sessionId").toString();
            String token =  mapValue.get("attendeeToken").toString();

            String message = "{\n" +
                "    \"title\": \" 안녕하세요 \",\n" +
                "    \"body\": \"" + msg + "\"\n" +
                "}";

            String attendeeToken = "{\n" +
                "    \"action\": \"doctor\",\n" +
                "    \"infomation\": {\n" +
                "        \"apikey\": \"" + apikey + "\",\n" +
                "        \"sessionId\": \""+ sessionID + "\",\n" +
                "        \"token\": \""+ token +"\"\n" +
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
                .url("http://192.168.42.193:8380/upmc/rcv_register_message.ctl")
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
