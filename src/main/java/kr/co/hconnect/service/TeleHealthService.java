package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.repository.ArchiveDao;
import kr.co.hconnect.vo.TeleHealthArchiveVO;
import kr.co.hconnect.vo.TeleHealthConnectVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.opentok.Archive.OutputMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.opentok.*;
import com.opentok.exception.OpenTokException;
import com.opentok.Archive;

/**
 * 화상상담 서비스
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class TeleHealthService extends EgovAbstractServiceImpl {


    private final ArchiveDao archiveDao;
    @Autowired
    public TeleHealthService(ArchiveDao archiveDao) {
        this.archiveDao = archiveDao;
    }
    int apikey = 47595911;
    String apiSecret = "2ddde1eb92a2528bd22be0c465174636daca363d";

    /**
     *화상상담
     * @param vo 측정항목VO
     */
    @Transactional(rollbackFor = Exception.class)
    public TeleHealthConnectVO selectConnection (TeleHealthConnectVO vo){
        TeleHealthConnectVO teleEntity = new TeleHealthConnectVO();

        BeanUtils.copyProperties(vo, teleEntity);

/*
        String format = "name=[%s]%s&clientType=web&serialNumber=%s&profileImgUrl=%s";
        String metaData = String.format(format
            , webUser.getManagementCtnm()
            , webUser.getManagementNm()
            , condition.getLoginUserSno()
            , FilePathConfig.getFilePathConfig(FilePathConfig.BASE_MOBILE_CONTENTS_URL) + webUser.ge
            tFileSaveNm());
*/
        String metaData = vo.getLoginId();

        OpenTok openTok = null;
            try {

                vo.setApiKey(apikey);
                vo.setApiSecret(apiSecret);
                teleEntity.setApiKey(apikey);
                //# 세션 생성
                openTok = new OpenTok(vo.getApiKey(), vo.getApiSecret());
                SessionProperties sessionProperties = new SessionProperties.Builder()
                    .mediaMode(MediaMode.ROUTED)
                    .build();
                Session session = openTok.createSession(sessionProperties);
                System.out.println("session =================================================");
                System.out.println(session.getSessionId());
                System.out.println("=================================================");
                teleEntity.setSessionId(session.getSessionId());

                //# 담당자 또는 참석자의 토큰 생성
                TokenOptions tokenOptions = new TokenOptions.Builder()
                    .role(Role.MODERATOR)
                    .data(metaData)
                    .build();

                System.out.println("generateToken =================================================");
                System.out.println(openTok.generateToken(teleEntity.getSessionId()
                    , tokenOptions));
                System.out.println("=================================================");


                //화상상담 개설자  토큰생성
                teleEntity.setOfficerToken(openTok.generateToken(teleEntity.getSessionId()
                    , tokenOptions));

              //  teleEntity.setAttendeeToken(openTok.generateToken(sessionid));

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
        Archive archive;
        try {

            vo.setApiKey(apikey);
            vo.setApiSecret(apiSecret);

            //# 세션 생성
            openTok = new OpenTok(vo.getApiKey(), vo.getApiSecret());
            OutputMode outputMode = OutputMode.INDIVIDUAL;
            ArchiveLayout layout = null;

            outputMode = OutputMode.COMPOSED;
            layout = new ArchiveLayout(ArchiveLayout.Type.HORIZONTAL);

            archive = openTok.startArchive(vo.getSessionId(), new ArchiveProperties.Builder()
                .name(vo.getAdmissionId())
                .hasAudio(true)
                .hasVideo(false)
                .outputMode(outputMode)
                .layout(layout)
                .build());
            String archiveId = archive.getId();

            TeleHealthArchiveVO teleHealthArchiveVO =new TeleHealthArchiveVO();
            teleHealthArchiveVO.setArchiveId(archiveId);
            teleHealthArchiveVO.setName(archive.getName());
            teleHealthArchiveVO.setReason(archive.getReason());
            teleHealthArchiveVO.setSize(archive.getSize());
            Date date = new Date(archive.getCreatedAt());
            teleHealthArchiveVO.setCreateAt(date);
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
}
