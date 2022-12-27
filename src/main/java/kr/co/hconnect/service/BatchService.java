package kr.co.hconnect.service;

import com.opentok.Archive;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.hconnect.controller.AdmissionController;
import kr.co.hconnect.vo.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.hconnect.repository.*;
import org.springframework.util.ResourceUtils;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import java.nio.file.Files;

import com.opentok.*;
import com.opentok.exception.OpenTokException;
import org.springframework.util.StringUtils;

@Service
public class BatchService extends EgovAbstractServiceImpl{
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchService.class);
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

            List<ScoreVO> dataList = aiInferenceDao.scoreList();

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
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            return rtn;
        }
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
            LOGGER.error(e.getMessage());
        }catch ( IOException e){
            rtn = e.getMessage();
            LOGGER.error(e.getMessage());
        }finally {
            try{
                if(br != null){
                    br.close();

                }
            }catch (IOException e) {
                e.printStackTrace();
                rtn = e.getMessage();
                LOGGER.error(e.getMessage());
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

        int resultCount = 0;

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
            List<TemperListVO> dataList = aiInferenceDao.temperList();

            if (dataList.size() > 0 ) {
                for (TemperListVO dt : dataList) {

                    System.out.println("환자 아이디 " + dt.getAdmissionId());

                    String aData = "";
                    aData = dt.getAdmissionId();   //환자 id
                    aData += "," + dt.getRr();     //호흡
                    aData += "," + dt.getPr();     //심박수
                    aData += "," + dt.getBt();     //체온
                    aData += "," + dt.getQ1Yn();   //가래
                    aData += "," + dt.getQ2Yn();   //흉통
                    aData += "," + dt.getQ3Yn();   //구토
                    aData += "," + dt.getQ4Yn();   //설사
                    aData += "," + dt.getQ5Yn();   //호흡곤란
                    aData += "," + dt.getQ6Yn();   //통증

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


    public String temperInsert(BatchVO vo) throws IOException, InterruptedException {

        String rtn = "0";

        File csv = new File(vo.getOutFilePath());


        if(!csv.exists() ) {
            System.out.println(" 체온 악화 예측 알고리즘 결과 파일이 없습니다.");
            return "";
        }

        BufferedReader br = null;
        String line = "";

        try{
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


    public int depressCreate(BatchVO vo) {

        int resultCount = 0;

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
            List<DepressListVO> dataList = aiInferenceDao.depressList();

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
                    aData += "," + dt.getInsTotal();  //ins 설문 총점수
                    aData += "," + dt.getVideo();     //환자음성파일위치
                    aData += "," + dt.getTag();       //실제악화값
                    aData += "," + dt.getSplit();     //fold number
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
        System.out.println("pythonbuilder ");
        String bool = "";
        ProcessBuilder builder;
        BufferedReader br;

        try{
            builder = new ProcessBuilder("python3",arg1); //python3 error

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
                System.out.println("비정상종료");
            }
        } catch (IOException e){
            bool = e.getMessage();
            LOGGER.error(e.getMessage());
        }

        return bool;
    }




    public int vonageArchiveList() {

        ArchiveVO avo = new ArchiveVO();

        List<ArchiveVO> rtnvoList = new ArrayList<>();
        rtnvoList = aiInferenceDao.archiveList(avo);

        if (rtnvoList != null) {

            for (ArchiveVO rvo : rtnvoList) {

                //  String strRtn = fileDownload(rvo);
            }

        }
        return 0;
    }

    /**
     * 아카리브 파일 다운로드
     * @return
     */
    public String fileDownload(String aid, String aName) throws IOException, OpenTokException , MalformedURLException {

        String rtn = "";

        String OUTPUT_FILE_PATH = ""; // "출력 파일 경로";
        String FILE_URL ="";         // "리소스 경로";
        String outputDir  = "/home/administrator/python/depressed/";
        Archive archive = null;

        InputStream is = null;
        FileOutputStream os = null;

        //아카이브 아이디 찾기
        String archiveId = aid;

        try{
            OpenTok openTok = new OpenTok(apikey, apiSecret);
            archive =openTok.getArchive(archiveId);

            FILE_URL = archive.getUrl();

            rtn = FILE_URL;

            //해당 url
            URL url = new URL(FILE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();

            System.out.println("responseCode " + responseCode);
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
                //     //fileName = FILE_URL.substring(FILE_URL.lastIndexOf("/") + 1);
                //     fileName = "archive.mp4";
                //
                // }

                //파일이름 규칙
                // admission_di + "-" + 년월일시분
                //A999999624_202212130737
                SimpleDateFormat format = new SimpleDateFormat ( "yyyyMMddHHmm");
                String formatdate = format.format (System.currentTimeMillis());
                fileName =aName +"_"+ formatdate+ ".mp4";

                System.out.println("Content-Type = " + contentType);
                System.out.println("Content-Disposition = " + disposition);
                System.out.println("fileName = " + fileName);

                is = conn.getInputStream();
                os = new FileOutputStream(new File(outputDir, fileName));

                final int BUFFER_SIZE = 4096;
                int bytesRead;
                byte[] buffer = new byte[BUFFER_SIZE];


                System.out.println("is.read(buffer) = " + is.read(buffer));
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                is.close();
                System.out.println("File downloaded");
            } else {
                System.out.println("No file to download. Server replied HTTP code: " + responseCode);
            }
            conn.disconnect();

        } catch (OpenTokException e) {
            System.out.println(e.getMessage());
        } catch (Exception e){
            System.out.println("An error occurred while trying to download a file.");
            e.printStackTrace();
            try {
                if (is != null){
                    is.close();
                }
                if (os != null){
                    os.close();
                }
            } catch (IOException e1){
                e1.printStackTrace();
            }
        }

        return rtn;

    }


}


