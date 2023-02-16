package kr.co.hconnect.service;

import com.opentok.Archive;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.common.HttpUtil;
import kr.co.hconnect.common.zipUtil;
import kr.co.hconnect.vo.*;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import kr.co.hconnect.repository.*;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


import com.opentok.*;
import com.opentok.exception.OpenTokException;
import org.springframework.util.StringUtils;
import ws.schild.jave.*;

@Service
public class BatchService extends EgovAbstractServiceImpl{
    private static final Logger log = LoggerFactory.getLogger(BatchService.class);

    private String ai_path="/usr/local/apache-tomcat-8.5.79/python/";

    private String ai_video_path="/usr/local/apache-tomcat-8.5.79/python/video/";


    @Value("${push.url}")
    private String push_url;


    private  final  AiInferenceDao aiInferenceDao;

    private  final  InterviewDao interviewDao;

    private  final  NoticeDao noticeDao;

    private int apikey = 47595911;

    private String apiSecret = "2ddde1eb92a2528bd22be0c465174636daca363d";


    @Autowired
    public BatchService(AiInferenceDao dao, InterviewDao interviewDao, NoticeDao noticeDao)
    {
        this.aiInferenceDao = dao;
        this.interviewDao = interviewDao;
        this.noticeDao = noticeDao;
    }


    /**
     * 스코어 데이터 파일 생성
     * @param vo
     * @return
     */
    public String scoreCreate(BatchVO vo) {
        String rtn ="";
        int resultCount = 0;

        List<String> targetString  = new ArrayList<>();

        BioErrorVO bioErrorVO ;
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        vo.setCDate(nowDate.toString());

        //스코어 대상 리스트
        log.info("스코어 대상 리스트 >>>> ");
        List<BioCheckVO> bvo = aiInferenceDao.bioAdmissionIdBefore();

        // 생체데이터 체크
        //itemid = I0002 심박수 I0003 산소포와도  ㅑ001체온

        String bioMetaDataFormat = "상급병원 전원 예측 id=%s & 일자=%s & 생체데이터=%s 데이터가 없습니다.";
        String msg;
        for (BioCheckVO bcvo: bvo){
            int biochkint =0;
            String sBiochk;
            //날짜 / 생체 데이터
            bcvo.setBioDate(nowDate.toString());

            bcvo.setItemId("I0002");
            sBiochk = aiInferenceDao.bioCheck(bcvo);
            if (sBiochk == null || sBiochk.equals("0")){
                msg= String.format(bioMetaDataFormat
                    , bcvo.getAdmissionId()
                    , nowDate.toString()
                    ,"  심박수  "
                );


                //에러 메세지 저장
                bioErrorVO = new BioErrorVO();
                bioErrorVO.setAdmissionId(bcvo.getAdmissionId());
                bioErrorVO.setInfDiv("10");
                bioErrorVO.setCDate(nowDate.toString());
                bioErrorVO.setMessage(msg);
                aiInferenceDao.insBioError(bioErrorVO);

                targetString.add(bcvo.getAdmissionId());
            }

            bcvo.setItemId("I0003");
            sBiochk = aiInferenceDao.bioCheck(bcvo);
            if (sBiochk == null || sBiochk.equals("0")){
                msg= String.format(bioMetaDataFormat
                    ,bcvo.getAdmissionId()
                    , nowDate.toString()
                    ,"산소포화도 "
                );
                System.out.println(msg);
                //에러 메세지 저장
                bioErrorVO = new BioErrorVO();
                bioErrorVO.setAdmissionId(bcvo.getAdmissionId());
                bioErrorVO.setInfDiv("10");
                bioErrorVO.setCDate(nowDate.toString());
                bioErrorVO.setMessage(msg);
                aiInferenceDao.insBioError(bioErrorVO);
                targetString.add(bcvo.getAdmissionId());
            }

            bcvo.setItemId("I0001");
            sBiochk = aiInferenceDao.bioCheck(bcvo);
            if (sBiochk == null || sBiochk.equals("0")){
                msg= String.format(bioMetaDataFormat
                    ,bcvo.getAdmissionId()
                    , nowDate.toString()
                    ,"체온 "
                );
                //에러 메세지 저장
                bioErrorVO = new BioErrorVO();
                bioErrorVO.setAdmissionId(bcvo.getAdmissionId());
                bioErrorVO.setInfDiv("10");
                bioErrorVO.setCDate(nowDate.toString());
                bioErrorVO.setMessage(msg);
                aiInferenceDao.insBioError(bioErrorVO);
                targetString.add(bcvo.getAdmissionId());
            }

            String interviewMetaDataFormat = "id=%s & 문진 =%s & 데이터가 없습니다.";
            bcvo.setInterviewType("01");
            sBiochk = aiInferenceDao.interviewCheck(bcvo);
            if (sBiochk == null || sBiochk.equals("0")){
                msg= String.format(interviewMetaDataFormat
                    , bcvo.getAdmissionId()
                    ,"확진당일 "
                );
                System.out.println(msg);
                //에러 메세지 저장
                bioErrorVO = new BioErrorVO();
                bioErrorVO.setAdmissionId(bcvo.getAdmissionId());
                bioErrorVO.setInfDiv("10");
                bioErrorVO.setCDate(nowDate.toString());
                bioErrorVO.setMessage(msg);
                aiInferenceDao.insBioError(bioErrorVO);

                targetString.add(bcvo.getAdmissionId());

            }

        }


        List list= null;
        String filePath = vo.getFilePath();
        String outFilePath = vo.getOutFilePath();
        log.info("파일 대상 >>>> " + filePath);

        //데이터를 받아오고 파일로 쓰기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = nowDate.format(formatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        String timeFormatedNow = nowTime.format(timeFormatter);
        try {

            File ofile = new File(outFilePath);
            if( ofile.exists() ) {
                File newFile = new File(ofile+"_"+formatedNow + timeFormatedNow );
                ofile.renameTo(newFile);
            }

            //입력 데이터 파일
            File file = new File(filePath);
            if( file.exists() ) {
                //file.delete();
                File newFile = new File(file+"_"+formatedNow + timeFormatedNow );
                file.renameTo(newFile);
            }

            //csv 파일의 기존 값에 이어쓰려면 위처럼 tru를 지정하고 기존갑을 덮어 쓰려면 true를 삭제한다
            BufferedWriter fw = new BufferedWriter(new FileWriter(filePath));

            List<ScoreVO> dataList = aiInferenceDao.scoreList(vo);

            log.info(" 스코어 대상 파일 수 " +  dataList.size());
            if (dataList.size() > 0 ) {
                //타이틀 넣기
                String tData = "";
                tData = "Patient_id";   //환자 id
                tData += "," + "age";    // 나이
                tData += "," + "hr";     //심박수
                tData += "," + "spo2";   // 산소포화도
                tData += "," + "temper";     //체온
                tData += "," + "hpx";    //고혈압 여부

                fw.write(tData);
                fw.newLine();

                for (ScoreVO dt : dataList) {
                    if(!targetString.contains(dt.getAdmissionId())) {
                        String aData = "";
                        aData = dt.getAdmissionId();   //환자 id
                        aData += "," + dt.getAge();    // 나이
                        aData += "," + dt.getPr();     //심박수
                        aData += "," + dt.getSpo2();   //산소포화도
                        aData += "," + dt.getBt();     //체온
                        aData += "," + dt.getHyp();    //고혈압 여부

                        fw.write(aData);
                        fw.newLine();
                    }
                }
            }
            fw.flush();
            //객체 닫기
            fw.close();

        } catch (IOException e) {
            rtn = e.getMessage();
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return rtn;
    }

    /**
     * 스코어 output  데이터  테이블 import
     * @param vo
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public String scoreInsert(BatchVO vo) throws IOException, InterruptedException {

        String rtn = "";
        //현재시간 설정
        LocalDate nowDate = LocalDate.now();

        AiInferenceVO logVO = new AiInferenceVO();
        logVO.setInfDiv("10");
        //로그로 데이터 복사
        aiInferenceDao.insInf_log(logVO);
        //데이터 삭제
        aiInferenceDao.delInf(logVO);

        File csv = new File(vo.getOutFilePath());

        if(!csv.exists() ) {
            log.info(" 상급병원 전원 예측 알고리즘 결과 파일이 없습니다.");
            return "";
        }

        BioErrorVO  bioSuccessVO ;
        String msg= "상급병원 전원 예측 성공";

        BufferedReader br = null;
        String line = "";
        try{
            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null){
                String[] lineArr = line.split(",");

                AiInferenceVO entityVO = new AiInferenceVO();

                String str = fillZero(10,lineArr[0]);
                entityVO.setAdmissionId(str);
                entityVO.setInfDiv("10");
                entityVO.setInfValue(lineArr[1]);

                System.out.println(lineArr[0]);
                System.out.println(lineArr[1]);

                if (lineArr[0].equals("Patient_id")) {
                    continue;
                }

                aiInferenceDao.insInf(entityVO);  // 인서트

                //성공 로그 보여주기
                bioSuccessVO = new BioErrorVO();
                bioSuccessVO.setAdmissionId(str);
                bioSuccessVO.setInfDiv("10");
                bioSuccessVO.setCDate(nowDate.toString());
                bioSuccessVO.setMessage(msg);
                aiInferenceDao.insBioError(bioSuccessVO);
            }


        }catch (FileNotFoundException e){
            rtn = e.getMessage();
            log.error(e.getMessage());
        }catch ( IOException e){
            rtn = e.getMessage();
            log.error(e.getMessage());
        }finally {
            try{
                if(br != null){
                    br.close();

                }
            }catch (IOException e) {
                e.printStackTrace();
                rtn = e.getMessage();
                log.error(e.getMessage());
            }
        }
        return rtn;

    }

    /**
     * 체온 격리 데이터 생성
     * @param vo
     * @return
     */
    public String temperCreate(BatchVO vo) {
        log.info("체온 상승 데이터 파일 만들기");
        int resultCount = 0;
        String result="";

        String testFlag="";
        testFlag = vo.getTestFlag();   //테스트릉 위한 flag


        List<String> targetString  = new ArrayList<>();

        BioErrorVO bioErrorVO ;
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        vo.setCDate(nowDate.toString());

        //스코어 대상 리스트
        List<BioCheckVO> bvo = aiInferenceDao.bioAdmissionId();

        // 생체데이터 체크
        //itemid = I0006 호흡 I0002 심박수   I0001 체온

        String bioMetaDataFormat = "체온상승 예측 알고리름 id=%s & 일자=%s & 생체데이터=%s 데이터가 없습니다.";
        String msg;
        for (BioCheckVO bcvo: bvo){
            int biochkint =0;
            String bioch;

            bcvo.setBioDate(nowDate.toString());
            bcvo.setBioTime(nowTime.toString());

            bcvo.setItemId("I0006");                             //호흡
            bioch = aiInferenceDao.bioTimeCheck(bcvo);
            if (bioch == null || bioch.equals("0")){
                msg= String.format(bioMetaDataFormat
                    ,bcvo.getAdmissionId()
                    ,nowDate.toString()
                    ," 호흡  "
                );

                //에러 메세지 저장
                BioErrorVO bioErrorVO06 = new BioErrorVO();
                bioErrorVO06.setAdmissionId(bcvo.getAdmissionId());
                bioErrorVO06.setInfDiv("20");
                bioErrorVO06.setCDate(nowDate.toString());
                bioErrorVO06.setMessage(msg);
                aiInferenceDao.insBioError(bioErrorVO06);

                targetString.add(bcvo.getAdmissionId());

            }

            bcvo.setItemId("I0002");
            bioch = aiInferenceDao.bioTimeCheck(bcvo);
            if (bioch == null || bioch.equals("0")){
                msg= String.format(bioMetaDataFormat
                    ,bcvo.getAdmissionId()
                    ,nowDate.toString()
                    ," 심박수 "
                );
                //에러 메세지 저장
                BioErrorVO bioErrorVO02 = new BioErrorVO();
                bioErrorVO02.setAdmissionId(bcvo.getAdmissionId());
                bioErrorVO02.setInfDiv("20");
                bioErrorVO02.setCDate(nowDate.toString());
                bioErrorVO02.setMessage(msg);
                aiInferenceDao.insBioError(bioErrorVO02);
                targetString.add(bcvo.getAdmissionId());
            }

            bcvo.setItemId("I0001");
            bioch = aiInferenceDao.bioTimeCheck(bcvo);
            if (bioch == null || bioch.equals("0") ){
                msg= String.format(bioMetaDataFormat
                    ,bcvo.getAdmissionId()
                    ,nowDate.toString()
                    ," 체온 "
                );
                //에러 메세지 저장
                BioErrorVO bioErrorVO01 = new BioErrorVO();
                bioErrorVO01.setAdmissionId(bcvo.getAdmissionId());
                bioErrorVO01.setInfDiv("20");
                bioErrorVO01.setCDate(nowDate.toString());
                bioErrorVO01.setMessage(msg);
                aiInferenceDao.insBioError(bioErrorVO01);
                targetString.add(bcvo.getAdmissionId());
            }
            //01 확진당일
            String interviewMetaDataFormat = "id=%s & 문진=%s & 데이터가 없습니다.";
            String endDate = bcvo.getEndDate();  //마지막일

            bcvo.setInterviewType("01");
            bioch = aiInferenceDao.interviewCheck(bcvo);
            if (bioch == null || bioch.equals("0") ){
                msg= String.format(interviewMetaDataFormat
                    ,bcvo.getAdmissionId()
                    ," 확진당일 "
                );
                //에러 메세지 저장
                BioErrorVO bioErrorVOM01 = new BioErrorVO();
                bioErrorVOM01.setAdmissionId(bcvo.getAdmissionId());
                bioErrorVOM01.setInfDiv("20");
                bioErrorVOM01.setCDate(nowDate.toString());
                bioErrorVOM01.setMessage(msg);
                aiInferenceDao.insBioError(bioErrorVOM01);
                targetString.add(bcvo.getAdmissionId());
            }

        }

        log.info("체온상승 파일 만들기 ==================================");

        List list= null;
        String filePath = vo.getFilePath();
        String outFilePath = vo.getOutFilePath();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = nowDate.format(formatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        String timeFormatedNow = nowTime.format(timeFormatter);
        //데이터를 받아오고 파일로 쓰기
        try {
            //출력파일 삭제
            File ofile = new File(outFilePath);
            if( ofile.exists() ) {
                File newFile = new File(ofile+"_"+formatedNow + timeFormatedNow );
                ofile.renameTo(newFile);
            }

            //입력 데이터 파일
            File file = new File(filePath);
            if( file.exists() ) {
                //file.delete();
                File newFile = new File(file+"_"+formatedNow + timeFormatedNow );
                file.renameTo(newFile);
            }

            //csv 파일의 기존 값에 이어쓰려면 위처럼 tru를 지정하고 기존갑을 덮어 쓰려면 true를 삭제한다
            BufferedWriter fw = new BufferedWriter(new FileWriter(filePath));

            //쿼리 를 한다.
            //

            log.info("체온 상승 오류 대상자 리스트  >>>> " + targetString.toString());
            log.info("체온 상스 파일 대상자 리스트  >>>> ");
            log.info("체온 상스 파일 테스트 여부  >>>>  " + testFlag);

            List<TemperListVO> dataList = aiInferenceDao.temperList(vo);

            if (dataList.size() > 0 ) {
                //타이틀 넣기
                String tData = "";
                tData = "Patient_id";   //환자 id
                tData += "," + "rr";      //호흡
                tData += "," + "hr";      //심박수
                tData += "," + "체온";     //
                tData += "," + "가래";     //1
                tData += "," + "발열";    //2
                tData += "," + "인후통";    //3
                tData += "," + "호흡곤란";    //4
                tData += "," + "흉통";    //5
                tData += "," + "오심";    //6
                tData += "," + "구토";    //7
                tData += "," + "변비";    //8
                tData += "," + "설사";    //9
                tData += "," + "복통";    //10
                tData += "," + "수면장애";    //11

                fw.write(tData);
                fw.newLine();

                log.info("체온상승 파일 만들기");

                for (TemperListVO dt : dataList) {
                    if(StringUtils.isEmpty(testFlag)){   //테스트를 위한 flag
                        if (!targetString.contains(dt.getAdmissionId())) {               //최초 문진이 없으면 파일을 만들지 않음
                            String aData = "";
                            aData = dt.getAdmissionId();   //환자 id
                            aData += "," + dt.getRr();     //호흡
                            aData += "," + dt.getPr();     //심박수
                            aData += "," + dt.getBt();     //체온
                            aData += "," + dt.getQ1Yn();   //가래
                            aData += "," + dt.getQ2Yn();   //발열
                            aData += "," + dt.getQ3Yn();   //인후통
                            aData += "," + dt.getQ4Yn();   //호흡곤란
                            aData += "," + dt.getQ5Yn();   //흉통
                            aData += "," + dt.getQ6Yn();   //오심
                            aData += "," + dt.getQ7Yn();   //구토
                            aData += "," + dt.getQ8Yn();   //변비
                            aData += "," + dt.getQ9Yn();   //설사
                            aData += "," + dt.getQ10Yn();   //복통
                            aData += "," + dt.getQ11Yn();   //수면장애

                            fw.write(aData);
                            fw.newLine();
                        }

                    } else {
                        String aData = "";
                        aData = dt.getAdmissionId();   //환자 id
                        aData += "," + dt.getRr();     //호흡
                        aData += "," + dt.getPr();     //심박수
                        aData += "," + dt.getBt();     //체온
                        aData += "," + dt.getQ1Yn();   //가래
                        aData += "," + dt.getQ2Yn();   //발열
                        aData += "," + dt.getQ3Yn();   //인후통
                        aData += "," + dt.getQ4Yn();   //호흡곤란
                        aData += "," + dt.getQ5Yn();   //흉통
                        aData += "," + dt.getQ6Yn();   //오심
                        aData += "," + dt.getQ7Yn();   //구토
                        aData += "," + dt.getQ8Yn();   //변비
                        aData += "," + dt.getQ9Yn();   //설사
                        aData += "," + dt.getQ10Yn();   //복통
                        aData += "," + dt.getQ11Yn();   //수면장애

                        fw.write(aData);
                        fw.newLine();

                    }
                }
            }
            fw.flush();
            fw.close();

        } catch (IOException e) {
            log.error(e.getMessage());
            result= e.getMessage();
        }

        return result;
    }


    public String temperInsert(BatchVO vo) throws IOException, InterruptedException {

        log.info("체온 상승 결과 데이터 데이틀 임포트");
        String rtn = "0";
        LocalDate nowDate = LocalDate.now();

        AiInferenceVO logVO = new AiInferenceVO();
        logVO.setInfDiv("20");
        //로그로 데이터 복사
        aiInferenceDao.insInf_log(logVO);
        //데이터 삭제
        aiInferenceDao.delInf(logVO);

        File csv = new File(vo.getOutFilePath());

        if(!csv.exists() ) {
            log.warn("체온 악화 예측 알고리즘 결과 파일이 없습니다.");
            return "";
        }

        BioErrorVO  bioSuccessVO ;
        String msg= "체온상승 예측 성공";

        BufferedReader br = null;
        String line = "";

        try{

            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null){
                String[] lineArr = line.split(",");

                AiInferenceVO entityVO = new AiInferenceVO();

                String str = fillZero(10, lineArr[0]);

                entityVO.setAdmissionId(str);
                entityVO.setInfDiv("20");
                entityVO.setInfValue(lineArr[1]);

                if (lineArr[0].equals("Patient_id")) {
                    continue;
                }

                aiInferenceDao.insInf(entityVO);  // 인서트

                System.out.println(lineArr[0]);
                System.out.println(lineArr[1]);

                //성공 로그 보여주기
                bioSuccessVO = new BioErrorVO();
                bioSuccessVO.setAdmissionId(str);
                bioSuccessVO.setInfDiv("20");
                bioSuccessVO.setCDate(nowDate.toString());
                bioSuccessVO.setMessage(msg);
                aiInferenceDao.insBioError(bioSuccessVO);

            }


        }catch (FileNotFoundException e){
            log.error(e.getMessage());
        }catch ( IOException e){
            log.error(e.getMessage());
        }finally {
            try{
                if(br != null){
                    br.close();

                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rtn;

    }


    public int depressCreate(BatchVO vo) throws java.text.ParseException {

        int resultCount = 0;
        /**
         * 체크 리스트 확인
         */
        List<String> targetString  = new ArrayList<>();



        BioErrorVO bioErrorVO ;
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        vo.setCDate(nowDate.toString());

        //스코어 대상 리스트
        List<BioCheckVO> bvo = aiInferenceDao.bioAdmissionIdBefore();


        // 생체데이터 체크
        //itemid = I0006 호흡 I0002 심박수   I0001 체온


        String bioMetaDataFormat = "id=%s & 일자=%s & 생체데이터=%s 데이터가 없습니다.";
        String msg;
        for (BioCheckVO bcvo: bvo){
            int biochkint =0;
            String bioch;

            bcvo.setBioDate(nowDate.toString());
            bcvo.setBioTime(nowTime.toString());

            // 2시간 마다
            //01 확진당일
            String interviewMetaDataFormat = "id=%s & 문진 =%s & 데이터가 없습니다.";
            String endDate = bcvo.getEndDate();  //마지막일
            String sNowDate = nowDate.toString();  //마지막일

            //입소당일
            bcvo.setInterviewType("01");
            bioch = aiInferenceDao.interviewCheck(bcvo);

            if (bioch == null || bioch.equals("0")){
                msg= String.format(interviewMetaDataFormat
                    , bcvo.getAdmissionId()
                    ,"확진당일 "
                );
                System.out.println(msg);
                //에러 메세지 저장
                bioErrorVO = new BioErrorVO();
                bioErrorVO.setAdmissionId(bcvo.getAdmissionId());
                bioErrorVO.setInfDiv("30");
                bioErrorVO.setCDate(nowDate.toString());
                bioErrorVO.setMessage(msg);
                aiInferenceDao.insBioError(bioErrorVO);

                targetString.add(bcvo.getAdmissionId());

            }
            //영상다운이 있는지 체크한다.
            bioch = aiInferenceDao.videoCheck(bcvo);
            if (bioch == null || bioch.equals("0")){
                msg= String.format(interviewMetaDataFormat
                    , bcvo.getAdmissionId()
                    ,"화상 녹화  "
                );
                //에러 메세지 저장
                bioErrorVO = new BioErrorVO();
                bioErrorVO.setAdmissionId(bcvo.getAdmissionId());
                bioErrorVO.setInfDiv("30");
                bioErrorVO.setCDate(nowDate.toString());
                bioErrorVO.setMessage(msg);
                aiInferenceDao.insBioError(bioErrorVO);
                targetString.add(bcvo.getAdmissionId());
            }


            //퇴소일이 있는 경우 30일 문진
            /**
             * 티소후 문진만 있고 추론은 돌리지 않는다
            String disChargeDate = bcvo.getEndDate();
            String disChargeDate30 = bcvo.getEndDate30();

            if (StringUtils.hasText(disChargeDate)){
                if (disChargeDate30.equals('Y')){
                    bcvo.setInterviewType("05");
                    bioch = aiInferenceDao.interviewCheck(bcvo);
                    if (bioch == null || bioch.equals("0")){
                        msg= String.format(interviewMetaDataFormat
                            , bcvo.getAdmissionId()
                            ,"퇴소후 30일 후 문진"
                        );
                        log.info(msg);
                        //에러 메세지 저장

                        BioErrorVO EVO = new BioErrorVO();
                        EVO.setAdmissionId(bcvo.getAdmissionId());
                        EVO.setInfDiv("30");
                        EVO.setCDate(nowDate.toString());
                        EVO.setMessage(msg);
                        aiInferenceDao.insBioError(EVO);
                    }

                }
            }
             */


        }
        log.info(" 우울 추론 데이터 시작");

        String filePath = vo.getFilePath();
        String outFilePath = vo.getOutFilePath();
        List list= null;
        //데이터를 받아오고 파일로 쓰기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = nowDate.format(formatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        String timeFormatedNow = nowTime.format(timeFormatter);

        try {

            File ofile = new File(outFilePath);
            if( ofile.exists() ) {
                File newFile = new File(ofile+"_"+formatedNow + timeFormatedNow );
                ofile.renameTo(newFile);
            }

            //입력 데이터 파일
            File file = new File(filePath);
            if( file.exists() ) {
                //file.delete();
                File newFile = new File(file+"_"+formatedNow + timeFormatedNow );
                file.renameTo(newFile);
            }

            //csv 파일의 기존 값에 이어쓰려면 위처럼 tru를 지정하고 기존갑을 덮어 쓰려면 true를 삭제한다
            BufferedWriter fw = new BufferedWriter(new FileWriter(filePath));

            //쿼리 를 한다.
            List<DepressListVO> dataList = aiInferenceDao.depressList(vo);
            log.info(" 우울 추론 데이터 대상 갯 수 : " + dataList.size());

            if (dataList.size() > 0 ) {
                log.info("우울 추론 데이터 파일 만들기");

                String tData = " ";
                tData = ","  + "patient";   //환자ID
                tData += "," + "stress_1";      //호흡
                tData += "," + "stress_2";      //심박수
                tData += "," + "stress_3";     //
                tData += "," + "stress_4";     //1
                tData += "," + "stress_5";    //2
                tData += "," + "stress_6";    //3
                tData += "," + "stress_7";    //4
                tData += "," + "stress_8";    //5
                tData += "," + "stress_9";    //6
                tData += "," + "gad7_total";    //7
                tData += "," + "phq9_1";      //호흡
                tData += "," + "phq9_2";      //심박수
                tData += "," + "phq9_3";     //
                tData += "," + "phq9_4";     //1
                tData += "," + "phq9_5";    //2
                tData += "," + "phq9_6";    //3
                tData += "," + "phq9_7";    //4
                tData += "," + "phq9_8";    //5
                tData += "," + "phq9_9";    //6
                tData += "," + "video";    //11
                tData += "," + "tag";    //11

                fw.write(tData);
                fw.newLine();
                int sno = 0;
                for (DepressListVO dt : dataList) {
                    if (!targetString.contains(dt.getAdmissionId())) {               //최초 문진이 없으면 파일을 만들지 않음
                        String aData = " ";
                        aData =  Integer.toString(sno);    //환자 id
                        aData += "," + dt.getAdmissionId();   //환자 id
                        aData += "," + dt.getSt1Yn();   //스트레스설문 1번
                        aData += "," + dt.getSt2Yn();   //스트레스설문 2번
                        aData += "," + dt.getSt3Yn();   //스트레스설문 3번
                        aData += "," + dt.getSt4Yn();   //스트레스설문 4번
                        aData += "," + dt.getSt5Yn();   //스트레스설문 5번
                        aData += "," + dt.getSt6Yn();   //스트레스설문 6번
                        aData += "," + dt.getSt7Yn();   //스트레스설문 7번
                        aData += "," + dt.getSt8Yn();   //스트레스설문 8번
                        aData += "," + dt.getSt9Yn();   //스트레스설문 9번
                        aData += "," + dt.getGadTotal();  //gad7 설문총점수
                        aData += "," + dt.getPhq1Yn();  //정신건강 우울 1
                        aData += "," + dt.getPhq2Yn();  //정신건강 우울 2
                        aData += "," + dt.getPhq3Yn();  //정신건강 우울 3
                        aData += "," + dt.getPhq4Yn();  //정신건강 우울 4
                        aData += "," + dt.getPhq5Yn();  //정신건강 우울 5
                        aData += "," + dt.getPhq6Yn();  //정신건강 우울 6
                        aData += "," + dt.getPhq7Yn();  //정신건강 우울 7
                        aData += "," + dt.getPhq8Yn();  //정신건강 우울 8
                        aData += "," + dt.getPhq9Yn();  //정신건강 우울 9
                        aData += "," + dt.getVideo();     //환자음성파일위치
                        aData += "," + dt.getTag();       //실제악화값
                        fw.write(aData);
                        fw.newLine();

                        sno += 1;
                    }
                }
            } else {
                log.info("우울 추론 데이터 베이스 데이터가 없습니다.");
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return resultCount;

    }

    /**
     * 우울 추론 결과파일 테이블 임포트
     * @param vo
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public String depressInsert(BatchVO vo) throws IOException, InterruptedException {

        String rtn = "0";
        LocalDate nowDate = LocalDate.now();

        AiInferenceVO logVO = new AiInferenceVO();
        logVO.setInfDiv("30");
        //로그로 데이터 복사
        aiInferenceDao.insInf_log(logVO);
        //데이터 삭제
        aiInferenceDao.delInf(logVO);

        File csv = new File(vo.getOutFilePath());
        if(!csv.exists() ) {
            log.error("정신건강 악화 (우울) 예측 알고리즘 결과 파일이 없습니다");
            return "";
        }

        BioErrorVO  bioSuccessVO ;
        String msg= "정신건강 악화 (우울) 예측 성공";

        BufferedReader br = null;
        String line = "";
        try{
            br = new BufferedReader(new FileReader(csv));
            log.info("정신건강 악화 (우울) 예측 알고리즘 결과 파일 업로드 시작 ");
            while ((line = br.readLine()) != null){
                String[] lineArr = line.split(",");
                //타이틀이면 스킵
                if (lineArr[1].equals("patient")) {
                    continue;
                }

                AiInferenceVO entityVO = new AiInferenceVO();

                String str = fillZero(10, lineArr[1]);  // 0으로 자릿수 채우기

                entityVO.setAdmissionId(str);
                entityVO.setInfDiv("30");                     //우울
                entityVO.setInfValue(lineArr[23]);            //실제 예측값

                log.info("우울 환자 번호 >>> " + lineArr[1]);
                log.info("우울 추론 결과 데이터 >>> " + lineArr[23]);

                aiInferenceDao.insInf(entityVO);  // 인서트

                //성공 로그 보여주기
                bioSuccessVO = new BioErrorVO();
                bioSuccessVO.setAdmissionId(str);
                bioSuccessVO.setInfDiv("30");
                bioSuccessVO.setCDate(nowDate.toString());
                bioSuccessVO.setMessage(msg);
                aiInferenceDao.insBioError(bioSuccessVO);



            }
            log.info("우울 예측 알고리즘 결과 파일 업로드 완료 ");

        }catch (FileNotFoundException e){
            log.error("FileNotFoundException  " + e.getMessage());
            rtn= e.getMessage();
        }catch ( IOException e){
            log.error("IOException  " + e.getMessage());
            rtn= e.getMessage();
        }finally {
            try{
                if(br != null){
                    br.close();
                }
            }catch (IOException e) {
                log.error("finally IOException  " + e.getMessage());
                rtn= e.getMessage();
            }
        }
        return rtn;

    }

    /**
     * 파이썬 소스 파일 실행하기
     * @return
     */
    public String pythonProcessbuilder( String arg1) throws IOException, InterruptedException {

        log.info("========pythonProcessbuilder ================");

        String bool = "";
        ProcessBuilder builder;
        BufferedReader br;
        log.info(" 파이썬 실행 프로세스 arg1 >>>> " + arg1);
        try{
            builder = new ProcessBuilder("python3.8",arg1);

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

            /*
            if(exitval !=0){
                //비정상종료
                log.warn("파이썬 프로세스 비정상 종료" );
            }
            */
        } catch (IOException e){
            bool = e.getMessage();
            log.error(e.getMessage());
        }

        return bool;
    }

    public String vonageArchiveList() throws IOException, OpenTokException {
        log.info("영상녹화 파일 다운로드 시작 >>>");
        String rtn ="";

        ArchiveVO avo = new ArchiveVO();

        List<ArchiveVO> rtnvoList = new ArrayList<>();
        rtnvoList = aiInferenceDao.archiveList(avo);

        if (rtnvoList != null) {
            String admissionId ="";
            log.info("rtnvoList.size :  " + rtnvoList.size());

            for (ArchiveVO rvo : rtnvoList) {
                log.info("download  admissionId :  " + rvo.getName());
                try{
                    fileDownload(rvo);
                } catch (Exception e){
                    rtn = e.getMessage();
                    log.error("vonageArchiveList   >>>  " + e.getMessage());
                }

            }

        }
        return rtn;
    }

    public String fileDownload(ArchiveVO vo) throws IOException, OpenTokException , MalformedURLException {

        String rtn = "";

        log.debug("ai_path  >>>  " + ai_path);
        log.debug("ai_video_path  >>>  " + ai_video_path);

        String FILE_URL ="";         // "리소스 경로";
        String outputDir  = ai_video_path;
        Archive archive = null;
        String vFileName ="";
        String getJsonPath ="";
        String getVideoPath ="";

        InputStream is = null;
        FileOutputStream os = null;

        String archiveId = vo.getArchiveId();
        String admissionId = vo.getName();   //admissionId

        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = nowDate.format(formatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        String timeFormatedNow = nowTime.format(timeFormatter);


        outputDir += admissionId;

        //디렉토리 생성하기
        File dir = new File(outputDir);
        if (dir.exists()){

            File newFile = new File(dir+"_"+formatedNow );
            dir.renameTo(newFile);

            File dirNew = new File(outputDir);
            dirNew.mkdir();

        } else {
            try{
                dir.mkdir();
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

            rtn = FILE_URL;

            //해당 url
            URL url = new URL(FILE_URL);
            String outputFile = outputDir + "/" + admissionId +".zip";

            File f = new File(outputFile);
            //파일 다운 로드
            FileUtils.copyURLToFile(url, f );

            //압축파일 풀기
            zipUtil ziputil = new zipUtil();
            String zipFile = admissionId + ".zip";
            outputDir +="/"  ;
            System.out.println("outputDir >> " + outputDir);
            System.out.println("zipFile >> "+ zipFile);
            //압축해제
            if(ziputil.unZip(outputDir, zipFile, outputDir)){

                /* json  파일 찾아서 읽기 */
                getJsonPath = outputDir +  admissionId + "/";
                getVideoPath = getJsonPath;

                System.out.println("getJsonPath  " + getJsonPath);
                File[] jsonFiles = getFileNames(getJsonPath, "json");

                System.out.println("jsonFiles.length  " + jsonFiles.length);
                String jsonfileName="";
                for(int i=0; i<jsonFiles.length; i++ ){
                    System.out.println("= jsonfile   " + i );
                    System.out.println("===================================================="  );

                    jsonfileName = jsonFiles[i].getName();
                    System.out.println("jsonfileName  " + jsonfileName);
                }

                getJsonPath += jsonfileName;

                //json 파일에서 환자의 파일이름을 읽는다.
                // jackson objectmapper 객체 생성
                JSONParser parser = new JSONParser();

                try(Reader reader = new FileReader(getJsonPath)){

                    JSONObject jsonObject = (JSONObject) parser.parse(reader);
                    System.out.println(jsonObject);

                    String name = (String) jsonObject.get("name");
                    System.out.println(name);

                    JSONArray files = (JSONArray) jsonObject.get("files");
                    System.out.println(files);

                    for ( int i=0; i<files.size(); i++){

                        System.out.println("=files  " + i +" ===========================================");
                        JSONObject dataObject = (JSONObject) files.get(i);
                        System.out.println("files: connectionData==>"+dataObject.get("connectionData"));

                        String connData = (String) dataObject.get("connectionData");
                        //파일명 찾기
                        if (connData.contains(admissionId)){
                            System.out.println("files: connectionData==>");
                            vFileName = (String) dataObject.get("filename");
                            System.out.println(vFileName);
                        } else {
                            //파일 삭제 하기
                            String wFileName = (String) dataObject.get("filename");

                        }

                    }

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }

            System.out.println("vFileName  >>>>>>>>>>  "+ vFileName);

            //기존  mp3 변환 한 내용 delete
            ArchiveDownVO archiveDownDelete = new ArchiveDownVO();
            archiveDownDelete.setAdmissionId(vo.getName());
            aiInferenceDao.udpArchiveDownYn(archiveDownDelete);

            //mp3 파일 추출
            if (!StringUtils.isEmpty(vFileName)) {
                //mpe3파일 추출
                System.setProperty("java.io.tmpdir", ai_path);   //비디오 실행 파일

                DefaultFFMPEGLocator locator= new  DefaultFFMPEGLocator();
                String exePath= locator.getFFMPEGExecutablePath();
                log.info("ffmpeg executable found in <"+exePath+">");


                String sourcePath = getVideoPath + vFileName;
                String targetPath = getVideoPath + admissionId +".mp3";

                log.info("video sourcePath" + sourcePath);
                log.info("video targetPath" + targetPath);

                Map<String, Object> cvAudio = new HashMap<String, Object>();
                cvAudio.put("sourcePath", sourcePath);
                cvAudio.put("targetPath", targetPath);

                log.info("video convert start");

                String r = convertMp3(cvAudio);

                if (StringUtils.isEmpty(r)){

                    log.info("결과출력");
                    log.info(formatedNow);  // 20221229
                    log.info(timeFormatedNow);  // 1500

                    //제대로 변환이 되었으면 디비에 넣기
                    ArchiveDownVO archiveDownVO = new ArchiveDownVO();

                    archiveDownVO.setArchiveId(vo.getArchiveId());
                    archiveDownVO.setAdmissionId(vo.getName());
                    archiveDownVO.setDnDateVoice(formatedNow);
                    archiveDownVO.setDnTimeVoice(timeFormatedNow);
                    archiveDownVO.setDnFolderVoice(targetPath);
                    archiveDownVO.setDnYnVoice("Y");

                    aiInferenceDao.insArchiveDown(archiveDownVO);

                }
                log.info("video convert end");

            }
        } catch (OpenTokException e) {
            log.error("오픈톡 오류 >>>>  " +  e.getMessage());
        }

        return rtn;

    }


    public void interviewAlarm(){
        //확진당일
        interviewAlarmToDay();
        //매일 문진
        interviewAlarmDay();

        //퇴소일문진
        selectInterviewDischarge();
        //퇴소후30일 문진
        selectInterviewDischarge30();

    }

    public void interviewAlarmDischarge(){

        //퇴소일문진
        selectInterviewDischarge();
        //퇴소후30일 문진
        selectInterviewDischarge30();

    }

    /**
     * 확진당일문진결과에 대하여 알람리스트
     * @return
     */
    public String interviewAlarmToDay() {
        log.info("01 확진당일 문진 결과 알람 리스트");
        String rtn="";
        List<InterviewAlarmList> entityVO = interviewDao.selectInterviewToday();

        try {
            if (entityVO.size()>0){
                for(InterviewAlarmList vo : entityVO){
                    if (StringUtils.isEmpty(vo.getInterviewType())){
                        HashMap<String, Object> mapValue = new HashMap<String, Object>();
                        String CUID = vo.getLoginId();
                        mapValue.put("CUID", CUID);
                        mapValue.put("MESSAGE", "확진당일 문진이 없습니다.");
                        mapValue.put("INTERVIEWTYPE", "01");  //문진타입
                        //#푸시내역 생성
                         int ret = InterviewSendPush(mapValue);

                         NoticeVO noticeVO = new NoticeVO();
                        noticeVO.setAdmissionId(vo.getAdmissionId());
                        noticeVO.setNotice("확진당일 문진이 없습니다.");
                        noticeVO.setRegId("admin");
                        // 알림 내역 저장
                        noticeDao.insertNotice(noticeVO);

                    }
                }

            }
        } catch( Exception e ){
            rtn = e.getMessage();
            log.error(e.getMessage());
        }
        return rtn;
    }

    /**
     * 매일 문진결과에 대하여 알람리스트
     * @return
     */
    public String interviewAlarmDay(){
        log.info("02 매일 문진 결과 알람 리스트");
        String rtn="";
        List<InterviewAlarmList> entityVO = interviewDao.selectInterviewDay();
        try {
            if (entityVO.size()>0){
                for(InterviewAlarmList vo : entityVO){
                    if (StringUtils.isEmpty(vo.getInterviewType())){
                        HashMap<String, Object> mapValue = new HashMap<String, Object>();
                        String CUID = vo.getLoginId();
                        mapValue.put("CUID", CUID);
                        mapValue.put("MESSAGE", "매일 문진이 없습니다.");
                        mapValue.put("INTERVIEWTYPE", "02");  //문진타입
                        //#푸시내역 생성
                        int ret = InterviewSendPush(mapValue);

                        NoticeVO noticeVO = new NoticeVO();
                        noticeVO.setAdmissionId(vo.getAdmissionId());
                        noticeVO.setNotice("매일 문진이 없습니다.");
                        noticeVO.setRegId("admin");
                        // 알림 내역 저장
                        noticeDao.insertNotice(noticeVO);
                    }
                }
            }
        } catch( Exception e ){
            rtn = e.getMessage();
            log.error(e.getMessage());
        }
        return rtn;
    }

    /**
     * 퇴소일 문진결과에 대하여 알람리스트
     * @return
     */
    public String selectInterviewDischarge(){
        log.info("03 퇴소일 문진 결과 알람 리스트");
        String rtn="";
        List<InterviewAlarmList> entityVO = interviewDao.selectInterviewDischarge();
        try {
            if (entityVO.size()>0){
                for(InterviewAlarmList vo : entityVO){
                    if (StringUtils.isEmpty(vo.getInterviewType())){
                        HashMap<String, Object> mapValue = new HashMap<String, Object>();
                        String CUID = vo.getLoginId();
                        mapValue.put("CUID", CUID);
                        mapValue.put("MESSAGE", "격리해제 당일 문진이 없습니다.");
                        mapValue.put("INTERVIEWTYPE", "04");  //문진타입
                        //#푸시내역 생성
                        int ret = InterviewSendPush(mapValue);

                        NoticeVO noticeVO = new NoticeVO();
                        noticeVO.setAdmissionId(vo.getAdmissionId());
                        noticeVO.setNotice("격리해제 당일 문진이 없습니다.");
                        noticeVO.setRegId("admin");
                        // 알림 내역 저장
                        noticeDao.insertNotice(noticeVO);
                    }
                }
            }
        } catch( Exception e ){
            rtn = e.getMessage();
            log.error(e.getMessage());
        }
        return rtn;
    }

    /**
     * 퇴소후 30일 문진결과에 대하여 알람리스트
     * @return
     */
    public String selectInterviewDischarge30(){
        log.info("04 퇴소일 후 30일 문진 결과 알람 리스트");
        String rtn="";
        List<InterviewAlarmList> entityVO = interviewDao.selectInterviewDischarge30();
        try {
            if (entityVO.size()>0){
                for(InterviewAlarmList vo : entityVO){
                    if (StringUtils.isEmpty(vo.getInterviewType())){
                        HashMap<String, Object> mapValue = new HashMap<String, Object>();
                        String CUID = vo.getLoginId();
                        mapValue.put("CUID" , CUID);
                        mapValue.put("MESSAGE", "격리 해제 후 30일 이후 문진이 없습니다.");
                        mapValue.put("INTERVIEWTYPE", "05");  //문진타입
                        //#푸시내역 생성
                        int ret = InterviewSendPush(mapValue);

                        NoticeVO noticeVO = new NoticeVO();
                        noticeVO.setAdmissionId(vo.getAdmissionId());
                        noticeVO.setNotice("격리 해제 후 30일 이후 문진이 없습니다.");
                        noticeVO.setRegId("admin");
                        // 알림 내역 저장
                        noticeDao.insertNotice(noticeVO);

                    }
                }
            }
        } catch( Exception e ){
            rtn = e.getMessage();
            log.error(e.getMessage());
        }
        return rtn;
    }


    public String updateAdmissionDischarge(){
        log.info("격리헤제 일괄 배치 시작");
        String rtn = "";

        try {
            //일괄처리 함
            aiInferenceDao.udpAdmissionDischarge();
        } catch ( Exception e){
            rtn=e.getMessage();
            log.error(e.getMessage());
        }
        log.info("격리헤제 일괄 배치 종료");
        return rtn;
    }

    //확장로 파일 이름 가져오기
    private File[] getFileNames(String targetDirName, String fileExt) {
        File dir = new File(targetDirName);

        File[] files = null;
        if (dir.isDirectory()) {
            final String ext = fileExt.toLowerCase();
            files = dir.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    if (file.isDirectory()) {
                        return false;
                    }
                    return file.getName().toLowerCase().endsWith("." + ext);
                }
            });
        }

        return files;
    }

    //webm to mp3 convert
    private String convertMp3(Map<String, Object> cvAudio) {

        String rtn ="";
        //mpe3파일 추출
        //System.setProperty("java.io.tmpdir", "E:\\python\\");

        String sourcePath =(String)cvAudio.get("sourcePath");
        String targetPath = (String)cvAudio.get("targetPath");


        log.info(" convertMp3 video sourcePath" + sourcePath);
        log.info(" convertMp3 video targetPath" + targetPath);

        File source = new File(sourcePath);
        File target = new File(targetPath);

        try {
            //Audio Attributes
            log.info(" convertMp3  Audio Attributes");
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(128000);
            audio.setChannels(2);
            audio.setSamplingRate(44100);

            //Encoding attributes
            log.info(" convertMp3  Encoding attributes");
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat("mp3");
            attrs.setAudioAttributes(audio);

            //Encode
            //Encoder encoder = new Encoder(new MyFFMPEGExecutableLocator())
            log.info(" convertMp3  Encoding ");
            Encoder encoder = new Encoder(new DefaultFFMPEGLocator());
            encoder.encode(new MultimediaObject(source), target, attrs);

        } catch (Exception ex) {
            rtn = ex.getMessage();
            log.error("convertMp3  >>> " + ex.getMessage());
            ex.printStackTrace();

        }
        return rtn;
    }

    //webm to mp4 convert
    private String convertMp4(Map<String, Object> cvVideo) {

        String rtn ="";
        //mpe3파일 추출
        System.setProperty("java.io.tmpdir", "E:\\python\\");

        String sourcePath =(String)cvVideo.get("sourcePath");
        String targetPath = (String)cvVideo.get("targetPath");


        System.out.println("video sourcePath" + sourcePath);
        System.out.println("video targetPath" + targetPath);

        File source = new File(sourcePath);
        File target = new File(targetPath);

        try {
            //Audio Attributes
            System.out.println("Audio Attributes");
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(128000);
            audio.setChannels(2);
            audio.setSamplingRate(44100);

            //Encoding attributes
            System.out.println("Encoding attributes");
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat("mp3");
            attrs.setAudioAttributes(audio);

            //Encode
            //Encoder encoder = new Encoder(new MyFFMPEGExecutableLocator())
            System.out.println("Encoding ");
            Encoder encoder = new Encoder(new DefaultFFMPEGLocator());
            encoder.encode(new MultimediaObject(source), target, attrs);

        } catch (Exception ex) {
            rtn = ex.getMessage();
            ex.printStackTrace();
        }
        return rtn;
    }

    public static String fillZero(int length, String value) {

        if (value == null)
            return "";

        if(value.length() >= 10)
            return value;

        char[] cValue = value.toCharArray();
        for (int i = 0; i < cValue.length; i++) {
            if (!Character.isDigit(cValue[i])) {
                return "";
            }
        }

        String result = value;
        int intLength = getStringLength(result);

        if (intLength == length) {
            return result;
        } else if (intLength > length) {
            return hanSubstr(length, value);
        }

        for (int i = 0; i < length; i++) {
            result = "0" + result;
            i = getStringLength(result) - 1;
        }
        return result;
    }

    public static String hanSubstr(int length, String value) {

        if (value == null || value.length() == 0) {
            return "";
        }

        int szBytes = value.getBytes().length;

        if (szBytes <= length) {
            return value;
        }

        String result = new String(value.getBytes(), 0, length);
        if (result.equals("")) {
            result = new String(value.getBytes(), 0, length - 1);
        }

        return result;
    }

    public static int getStringLength(String str) {
        char ch[] = str.toCharArray();
        int max = ch.length;
        int count = 0;

        for (int i = 0; i < max; i++) {
            // 0x80: 문자일 경우 +2
            if (ch[i] > 0x80)
                count++;
            count++;
        }
        return count;
    }
    public int InterviewSendPush (HashMap<String, Object> mapValue){
        int rtn = 0;
        try {

            String cuid = mapValue.get("CUID").toString();
            String msg =  mapValue.get("MESSAGE").toString();
            String interviewType =  mapValue.get("INTERVIEWTYPE").toString();

            String message = "{\n" +
                "    \"title\": \" 안녕하세요 \",\n" +
                "    \"body\": \"" + msg + "\"\n" +
                "}";

            String attendeeToken = "{\n" +
                "    \"action\": \"interview\",\n" +
                "    \"interviewType\": \"" + interviewType + "\"\n" +
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


