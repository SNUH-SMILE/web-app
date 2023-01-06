package kr.co.hconnect.service;

import com.opentok.Archive;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.common.zipUtil;
import kr.co.hconnect.vo.*;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.jdbc.Null;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.hconnect.repository.*;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.*;
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

    private  final  AiInferenceDao aiInferenceDao;

    private int apikey = 47595911;
    private String apiSecret = "2ddde1eb92a2528bd22be0c465174636daca363d";


    @Autowired
    public BatchService(AiInferenceDao dao) {
        this.aiInferenceDao = dao;
    }


    /**
     * 스코어 데이터 파일 생성
     * @param vo
     * @return
     */
    public String scoreCreate(BatchVO vo) {
        String rtn ="";
        int resultCount = 0;

        BioErrorVO bioErrorVO ;
        LocalDate nowDate = LocalDate.now();
        vo.setCDate(nowDate.toString());

        //스코어 대상 리스트
        List<BioCheckVO> bvo = aiInferenceDao.bioAdmissionId();

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
            if (sBiochk == null && sBiochk.equals("0")){
                msg= String.format(bioMetaDataFormat
                    , bcvo.getAdmissionId()
                    , nowDate.toString()
                    ,"  심박수  "
                );

                System.out.println(msg);

                //에러 메세지 저장
                bioErrorVO = new BioErrorVO();
                bioErrorVO.setAdmissionId(bcvo.getAdmissionId());
                bioErrorVO.setInfDiv("10");
                bioErrorVO.setCDate(nowDate.toString());
                bioErrorVO.setMessage(msg);
                aiInferenceDao.insBioError(bioErrorVO);

            }

            bcvo.setItemId("I0003");
            sBiochk = aiInferenceDao.bioCheck(bcvo);
            if (sBiochk == null && sBiochk.equals("0")){
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
            }

            bcvo.setItemId("I0001");
            sBiochk = aiInferenceDao.bioCheck(bcvo);
            if (sBiochk == null && sBiochk.equals("0")){
                msg= String.format(bioMetaDataFormat
                    ,bcvo.getAdmissionId()
                    , nowDate.toString()
                    ,"체온 "
                );
                System.out.println(msg);
                //에러 메세지 저장
                bioErrorVO = new BioErrorVO();
                bioErrorVO.setAdmissionId(bcvo.getAdmissionId());
                bioErrorVO.setInfDiv("10");
                bioErrorVO.setCDate(nowDate.toString());
                bioErrorVO.setMessage(msg);
                aiInferenceDao.insBioError(bioErrorVO);
            }

            String interviewMetaDataFormat = "id=%s & 문진 =%s & 데이터가 없습니다.";
            bcvo.setInterviewType("01");
            sBiochk = aiInferenceDao.interviewCheck(bcvo);
            if (sBiochk == null && sBiochk.equals("0")){
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
            }

        }


        List list= null;
        String filePath = vo.getFilePath();

        //데이터를 받아오고 파일로 쓰기
        try {

            File file = new File(filePath);

            if( file.exists() ) {
                file.delete();
            }

            //csv 파일의 기존 값에 이어쓰려면 위처럼 tru를 지정하고 기존갑을 덮어 쓰려면 true를 삭제한다
            BufferedWriter fw = new BufferedWriter(new FileWriter(filePath));

            List<ScoreVO> dataList = aiInferenceDao.scoreList(vo);
            log.info(" 스코어 대상 파일 수 " +  dataList.size());
            if (dataList.size() > 0 ) {
                //타이틀 넣기
                /*
                String tData = "";
                tData = "환자 id";   //환자 id
                tData += "," + "나이";    // 나이
                tData += "," + "심박수";     //심박수
                tData += "," + "산소포화도";   // 산소포화도
                tData += "," + "체온";     //체온
                tData += "," + "고혈압여부";    //고혈압 여부

                fw.write(tData);
                fw.newLine();
                */

                for (ScoreVO dt : dataList) {
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
     * 스코어 배치 파일 실행하기
     */



    /**
     * 스코어 output  데이터  테이블 import
     * @param vo
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public String scoreInsert(BatchVO vo) throws IOException, InterruptedException {

        String rtn = "";

        File csv = new File(vo.getOutFilePath());

        if(!csv.exists() ) {
            System.out.println(" 상급병원 전원 예측 알고리즘 결과 파일이 없습니다.");
            return "";
        }

        BufferedReader br = null;
        String line = "";

        try{
            AiInferenceVO logVO = new AiInferenceVO();
            logVO.setInfDiv("10");
            //로그로 데이터 복사
            aiInferenceDao.insInf_log(logVO);
            //데이터 삭제
            aiInferenceDao.delInf(logVO);

            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null){
                String[] lineArr = line.split(",");

                AiInferenceVO entityVO = new AiInferenceVO();
                entityVO.setAdmissionId(lineArr[0]);
                entityVO.setInfDiv("10");
                entityVO.setInfValue(lineArr[1]);

                System.out.println(lineArr[0]);
                System.out.println(lineArr[1]);

                if (lineArr[0].equals("Patient_id")) {
                    continue;
                }

                aiInferenceDao.insInf(entityVO);  // 인서트

                System.out.println(lineArr[0]);
                System.out.println(lineArr[1]);
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
    public int temperCreate(BatchVO vo) {
        log.info("체온 상승 데이터 파일 만들기");
        int resultCount = 0;

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
                    , bcvo.getAdmissionId()
                    , nowDate.toString()
                    ,"  호흡  "
                );

                //에러 메세지 저장
                bioErrorVO = new BioErrorVO();
                bioErrorVO.setAdmissionId(bcvo.getAdmissionId());
                bioErrorVO.setInfDiv("20");
                bioErrorVO.setCDate(nowDate.toString());
                bioErrorVO.setMessage(msg);
                aiInferenceDao.insBioError(bioErrorVO);

            }

            bcvo.setItemId("I0002");
            bioch = aiInferenceDao.bioTimeCheck(bcvo);
            if (bioch == null || bioch.equals("0")){
                msg= String.format(bioMetaDataFormat
                    ,bcvo.getAdmissionId()
                    , nowDate.toString()
                    ," 심박수 "
                );
                System.out.println(msg);
                //에러 메세지 저장
                bioErrorVO = new BioErrorVO();
                bioErrorVO.setAdmissionId(bcvo.getAdmissionId());
                bioErrorVO.setInfDiv("20");
                bioErrorVO.setCDate(nowDate.toString());
                bioErrorVO.setMessage(msg);
                aiInferenceDao.insBioError(bioErrorVO);
            }

            bcvo.setItemId("I0001");
            bioch = aiInferenceDao.bioTimeCheck(bcvo);
            if (bioch == null || bioch.equals("0") ){
                msg= String.format(bioMetaDataFormat
                    ,bcvo.getAdmissionId()
                    , nowDate.toString()
                    ,"체온 "
                );
                System.out.println(msg);
                //에러 메세지 저장
                bioErrorVO = new BioErrorVO();
                bioErrorVO.setAdmissionId(bcvo.getAdmissionId());
                bioErrorVO.setInfDiv("20");
                bioErrorVO.setCDate(nowDate.toString());
                bioErrorVO.setMessage(msg);
                aiInferenceDao.insBioError(bioErrorVO);
            }
            //01 확진당일
            String interviewMetaDataFormat = "id=%s & 문진 =%s & 데이터가 없습니다.";
            String endDate = bcvo.getEndDate();  //마지막일

            bcvo.setInterviewType("01");
            bioch = aiInferenceDao.interviewCheck(bcvo);
            if (bioch == null || bioch.equals("0") ){
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
            }

        }


        List list= null;
        String filePath = vo.getFilePath();

        //데이터를 받아오고 파일로 쓰기
        try {

            File file = new File(filePath);

            if( file.exists() ) {
                file.delete();
            }

            //csv 파일의 기존 값에 이어쓰려면 위처럼 tru를 지정하고 기존갑을 덮어 쓰려면 true를 삭제한다
            BufferedWriter fw = new BufferedWriter(new FileWriter(filePath));

            //쿼리 를 한다.
            //
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

                for (TemperListVO dt : dataList) {

                    System.out.println("환자 아이디 " + dt.getAdmissionId());

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
            fw.flush();
            //객체 닫기
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return resultCount;
    }


    public String temperInsert(BatchVO vo) throws IOException, InterruptedException {

        log.info("체온 상승 결과 데이터 데이틀 임포트");
        String rtn = "0";

        File csv = new File(vo.getOutFilePath());

        if(!csv.exists() ) {
            log.warn("체온 악화 예측 알고리즘 결과 파일이 없습니다.");
            return "";
        }

        BufferedReader br = null;
        String line = "";

        try{
            AiInferenceVO logVO = new AiInferenceVO();
            logVO.setInfDiv("20");
            //로그로 데이터 복사
            aiInferenceDao.insInf_log(logVO);
            //데이터 삭제
            aiInferenceDao.delInf(logVO);

            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null){
                String[] lineArr = line.split(",");

                AiInferenceVO entityVO = new AiInferenceVO();
                entityVO.setAdmissionId(lineArr[0]);
                entityVO.setInfDiv("20");
                entityVO.setInfValue(lineArr[1]);

                System.out.println(lineArr[0]);
                System.out.println(lineArr[1]);

                if (lineArr[0].equals("Patient_id")) {
                    continue;
                }

                aiInferenceDao.insInf(entityVO);  // 인서트

                System.out.println(lineArr[0]);
                System.out.println(lineArr[1]);
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


    public int depressCreate(BatchVO vo) {

        int resultCount = 0;

        BioErrorVO bioErrorVO ;
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        vo.setCDate(nowDate.toString());

        //스코어 대상 리스트
        List<BioCheckVO> bvo = aiInferenceDao.bioAdmissionId();

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
            }

            //퇴소후 1달 뒤
            int bv = 0;
            if (bv != 0){
                bcvo.setInterviewType("05");
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
                }
            }


        }

        List list= null;
        String filePath = vo.getFilePath();

        //데이터를 받아오고 파일로 쓰기
        try {

            File file = new File(filePath);

            if( file.exists() ) {
                file.delete();
            }

            //csv 파일의 기존 값에 이어쓰려면 위처럼 tru를 지정하고 기존갑을 덮어 쓰려면 true를 삭제한다
            BufferedWriter fw = new BufferedWriter(new FileWriter(filePath));

            //쿼리 를 한다.
            //
            List<DepressListVO> dataList = aiInferenceDao.depressList(vo);

            if (dataList.size() > 0 ) {
                for (DepressListVO dt : dataList) {

                    System.out.println("환자 아이디 " + dt.getAdmissionId());

                    String aData = "";
                    aData = dt.getAdmissionId();   //환자 id
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
                }
            }
            fw.flush();
            //객체 닫기
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            return resultCount;
        }

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

        File csv = new File(vo.getOutFilePath());


        if(!csv.exists() ) {
            System.out.println(" 우울 예측 알고리즘 결과 파일이 없습니다.");
            return "";
        }

        BufferedReader br = null;
        String line = "";

        try{
            AiInferenceVO logVO = new AiInferenceVO();
            logVO.setInfDiv("30");
            //로그로 데이터 복사
            aiInferenceDao.insInf_log(logVO);
            //데이터 삭제
            aiInferenceDao.delInf(logVO);

            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null){
                String[] lineArr = line.split(",");

                AiInferenceVO entityVO = new AiInferenceVO();
                entityVO.setAdmissionId(lineArr[0]);          //환자번호
                entityVO.setInfDiv("30");                     //우울
                entityVO.setInfValue(lineArr[22]);            //실제 예측값

                System.out.println(lineArr[0]);
                System.out.println(lineArr[22]);

                if (lineArr[0].equals("Patient_id")) {
                    continue;
                }

                aiInferenceDao.insInf(entityVO);  // 인서트

                System.out.println(lineArr[0]);
                System.out.println(lineArr[22]);
            }


        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch ( IOException e){
            System.out.println(e.getMessage());
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


    /**
     * 파이썬 소스 파일 실행하기
     * @return
     */
    public String pythonProcessbuilder( String arg1) throws IOException, InterruptedException {
        log.info("========pythonProcessbuilder");

        String bool = "";
        ProcessBuilder builder;
        BufferedReader br;
        log.info(" 파이썬 실행 프로세스 >>>> " + arg1);
        try{
            builder = new ProcessBuilder("python3.8",arg1); //python3 error

            builder.redirectErrorStream(true);
            Process process = builder.start();

            // 자식 프로세스가 종료될 때까지 기다림
            int exitval = process.waitFor();

            //// 서브 프로세스가 출력하는 내용을 받기 위해
            br = new BufferedReader(new InputStreamReader(process.getInputStream(),"UTF-8"));

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(">>>  " + line); // 표준출력에 쓴다
            }

            if(exitval !=0){
                //비정상종료
                log.warn("파이썬 프로세스 비정상 종료" );
            }
        } catch (IOException e){
            bool = e.getMessage();
            log.error(e.getMessage());
        }

        return bool;
    }




    public String vonageArchiveList() throws IOException, OpenTokException {

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

            //파일 다운로드 완료 업데이트
            ArchiveVO archiveVO = new ArchiveVO();
            archiveVO.setArchiveId(archiveId);
            aiInferenceDao.udpArchiveDown(archiveVO);



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
                        }

                    }

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }

            System.out.println("vFileName  >>>>>>>>>>  "+ vFileName);

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
                    archiveDownVO.setDnYn("Y");

                    aiInferenceDao.insArchiveDown(archiveDownVO);

                }
                log.info("video convert end");

            }
        } catch (OpenTokException e) {
            log.error("오픈톡 오류 >>>>  " +  e.getMessage());
        }

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


}


