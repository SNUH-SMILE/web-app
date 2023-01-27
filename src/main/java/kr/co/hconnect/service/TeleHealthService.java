package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.common.zipUtil;
import kr.co.hconnect.controller.HealthController;
import kr.co.hconnect.repository.ArchiveDao;
import kr.co.hconnect.repository.UserDao;
import kr.co.hconnect.repository.TeleHealthDao;
import kr.co.hconnect.vo.*;
import kr.co.hconnect.common.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.opentok.Archive.OutputMode;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opentok.*;
import com.opentok.exception.OpenTokException;
import com.opentok.Archive;
import org.springframework.util.StringUtils;
import ws.schild.jave.DefaultFFMPEGLocator;

/**
 * 화상상담 서비스
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class TeleHealthService extends EgovAbstractServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(TeleHealthService.class);

    @Value("${ai.local.video.path}")
    private String ai_local_video_path;
    @Value("${ai.local.explorer.path}")
    private String ai_local_explorer_path;


    private final ArchiveDao archiveDao;
    private final UserDao userDao;
    private final TeleHealthDao teleHealthDao;

    @Value("${push.url}")
    private String push_url;

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

                openTok.close();

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

            openTok.close();

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
            openTok.close();
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
     * 화상 상담 파일 파일 다운로드
     * @param vo
     * @return
     * @throws IOException
     * @throws OpenTokException
     * @throws MalformedURLException
     */
    public String getTeleArchiveDown(TeleReqArchiveDownVO vo) throws IOException, OpenTokException , MalformedURLException {

        String rtn = "";
        String metaDataFormat = "화상상담 파일 다운로드가 완료 되었습니다. 경로=%s .";
        String msg;

        //log.info("ai_local_video_path  >>>  " + ai_local_video_path);

        String outputDir  = "/usr/local/apache-tomcat-8.5.79/python/video/";

        log.info("outputDir  >>>  " + outputDir);


        String FILE_URL ="";         // "리소스 경로";
        //String outputDir  = ai_local_video_path;
        Archive archive = null;

        InputStream is = null;
        FileOutputStream os = null;

        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = nowDate.format(formatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        String timeFormatedNow = nowTime.format(timeFormatter);

        vo.setCDate(nowDate);

        String archiveId = teleHealthDao.getArchiveId(vo);

        if (StringUtils.isEmpty(archiveId)){
            return rtn ="31";
        }
        String admissionId = vo.getAdmissionId();
        String patientId = userDao.selectPatientId(admissionId);

        outputDir +=  patientId;
        log.info("outputDir  >>>  " + outputDir);

        //디렉토리 생성하기
        File dir = new File(outputDir);

        log.info("dir.exists()  >>>  " + dir.exists());

        if (dir.exists()){

            File newFile = new File(dir+"_"+formatedNow );
            dir.renameTo(newFile);

            File dirNew = new File(outputDir);
            dirNew.mkdir();

        } else {
            try{
                dir.mkdir();
                log.info("dir.mkdir() >>>>  폴더가 생성 되었습니다." + dir );
            } catch (Exception e){


                System.out.println(e.getMessage());
                return "";
            }

        }
        //디렉토리 생성하기
        try{

            OpenTok openTok = new OpenTok(apikey, apiSecret);
            archive =openTok.getArchive(archiveId);

            FILE_URL = archive.getUrl();

            //rtn = FILE_URL;

            log.info("archive.getUrl  >>>  " + FILE_URL);
            if (StringUtils.isEmpty(FILE_URL)){
                log.info("다운로드 할 파일이 없어서 리턴함  " );

                openTok.close();

                return rtn = "31";
            }

            //해당 url
            URL url = new URL(FILE_URL);
            String outputFile = outputDir + "\\" + patientId +".zip";

            log.info(" Archive local Down outputFile >>>>" +  outputFile);

            File f = new File(outputFile);

            log.info(" Archive local Down file create >>>>" + f );

            //파일 다운 로드
            //FileUtils.copyURLToFile(url, f );  //허가거부
            //폴더의 문제도 아니고

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = "";
                String disposition = conn.getHeaderField("Content-Disposition");
                String contentType = conn.getContentType();

                // 일반적으로 Content-Disposition 헤더에 있지만
                // 없을 경우 url 에서 추출해 내면 된다.
                // if (disposition != null) {
                //     String target = "filename=";
                //     int index = disposition.indexOf(target);
                //     if (index != -1) {
                //         fileName = disposition.substring(index + target.length() + 1);
                //     }
                // } else {
                //     fileName = patientId + ".zip";
                //
                // }

                fileName = patientId + ".zip";
                log.info(" Archive local Down outputDir >>>>" + outputDir);
                log.info(" Archive local Down fileName >>>>" + fileName);

                is = conn.getInputStream();
                os = new FileOutputStream(new File(outputDir, fileName));

                log.info(" Archive local getInputStream 시작 >>>>" + is);
                log.info(" Archive local FileOutputStream 시작 >>>>" + os);

                final int BUFFER_SIZE = 4096;
                int bytesRead;
                byte[] buffer = new byte[BUFFER_SIZE];

                log.info(" Archive local Down while 시작 >>>>" );

                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                is.close();
                System.out.println("File downloaded");

            }
            conn.disconnect();
            log.info(" Archive local Down file Down load >>>>" );

/*
            //압축파일 풀기
            zipUtil ziputil = new zipUtil();
            String zipFile = patientId + ".zip";
            //outputDir +="\\"  ;
            System.out.println("outputDir >> " + outputDir);
            System.out.println("zipFile >> "+ zipFile);


            //압축해제
            log.error(" Archive local Down 압축해제  >>>>" + zipFile);
            if(ziputil.unZip(outputDir, zipFile, outputDir)){

                msg= String.format(metaDataFormat
                    , outputDir
                );

                rtn = msg;
            }  //zip 파일 압축해제

            //파일 다운로드 폴더 열기
            exeProcessbuilder(outputDir);
*/

            openTok.close();
        } catch (OpenTokException e) {

            log.error(" Archive local Down OpenTokException >>>>" + e.getMessage());

        // } catch (InterruptedException e) {
        //     log.error(" Archive local Down InterruptedException >>>>" + e.getMessage());
        //     throw new RuntimeException(e);
        }
/*
        //정삭적이지
        if (StringUtils.isEmpty(rtn)){
            rtn= "31";
        }
*/
        return rtn;

    }

    public String getArchiveUrl(TeleReqArchiveDownVO vo) throws IOException, OpenTokException , MalformedURLException {

        String rtn = "";

        String archiveId = teleHealthDao.getArchiveId(vo);

        Archive archive = null;

        OpenTok openTok = new OpenTok(apikey, apiSecret);
        archive =openTok.getArchive(archiveId);
        rtn =  archive.getUrl();

        log.info("archive.getUrl()" + rtn );

        openTok.close();
        return rtn;

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


    /**
     * 파일 탐색기 열기
     * @return
     */
    public String exeProcessbuilder( String arg1) throws IOException, InterruptedException {

        log.info("========파일 탐색기 Processbuilder ================");

        String bool = "";
        ProcessBuilder builder;
        BufferedReader br;

        log.info(" 탐색기 실행 프로세스 arg1 >>>> " + arg1);

        try{
            builder = new ProcessBuilder(ai_local_explorer_path,arg1); //python3 error

            builder.redirectErrorStream(true);
            Process process = builder.start();

            // 자식 프로세스가 종료될 때까지 기다림
            int exitval = process.waitFor();

            //// 서브 프로세스가 출력하는 내용을 받기 위해
            br = new BufferedReader(new InputStreamReader(process.getInputStream(),"UTF-8"));

            log.info("표준출력1  >>>   "+ br );

            String line;
            while ((line = br.readLine()) != null) {
                log.info("표준출력 loop  >>>   "+line );
            }
        } catch (IOException e){
            bool = e.getMessage();
            log.error(e.getMessage());
        }

        return bool;
    }


}
