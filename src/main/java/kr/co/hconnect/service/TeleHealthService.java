package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.hconnect.vo.TeleHealthConnectVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.opentok.*;
import com.opentok.exception.OpenTokException;

/**
 * 화상상담 서비스
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class TeleHealthService extends EgovAbstractServiceImpl{



    @Autowired
    public TeleHealthService() {
    }

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
                int apikey = 47595911;
                vo.setApiKey(apikey);
                vo.setApiSecret("2ddde1eb92a2528bd22be0c465174636daca363d");

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
            int apikey = 47595911;
            vo.setApiKey(apikey);
            vo.setApiSecret("2ddde1eb92a2528bd22be0c465174636daca363d");

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


}
