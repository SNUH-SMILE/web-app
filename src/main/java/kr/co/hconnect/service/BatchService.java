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

    private String orgPath ="E:\\projects\\snuh_smile\\web-app\\src\\main\\resources\\inference\\score\\";

    @Autowired
    public BatchService(AiInferenceDao dao) {
        this.aiInferenceDao = dao;
    }


    /**
     * 스코어 데이터 파일 생성
     * @param vo
     * @return
     */
    public int scoreCreate(BatchVO vo) {

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
            List<ScoreVO> dataList = aiInferenceDao.scoreList();

            if (dataList.size() > 0 ) {
                //타이틀 넣기
                String tData = "";
                tData = "환자 id";   //환자 id
                tData += "," + "나이";    // 나이
                tData += "," + "심박수";     //심박수
                tData += "," + "산소포화도";   // 산소포화도
                tData += "," + "체온";     //체온
                tData += "," + "고혈압여부";    //고혈압 여부

                fw.write(tData);
                fw.newLine();

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
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            return resultCount;
        }

    }

    /**
     * 스코어 output  데이터  테이블 import
     * @param vo
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public String scoreInsert(BatchVO vo) throws IOException, InterruptedException {

        String rtn = "0";

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






    @Transactional(rollbackFor = Exception.class)
    public void depress() throws FdlException {
    }

    /**
     * 파이썬 소스 파일 실행하기
     * @return
     */
    public Boolean pythonProcessbuilder() throws IOException, InterruptedException {
        System.out.println("pythonbuilder ");
        Boolean bool = true;
        String arg1;
        ProcessBuilder builder;
        BufferedReader br;

        arg1 = "./resources/inference/score/score.py";
        builder = new ProcessBuilder("python",arg1); //python3 error

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
            bool=false;
        }

        return bool;
    }


    /**
     * 파이썬 파일 실행
     */

    public void excuteFile() {

        List<String> cmd = new ArrayList<String>();
        cmd.add("/bin/bash");
        cmd.add("-c");
        cmd.add("/pythonCode/test.py");

        StringBuilder sb = new StringBuilder(1024);
        String s = null;
        ProcessBuilder prsbld = null;
        Process prs = null;

        try {
            prsbld = new ProcessBuilder(cmd);
            // prsbld.directory(new File("/pythonCode")); // 디렉토리 이동
            // System.out.println("command: " + prsbld.command()); 	// 커맨드 확인

            // 프로세스 수행시작
            prs = prsbld.start();

            BufferedReader stdError = new BufferedReader(new InputStreamReader(prs.getErrorStream()));
            while ((s = stdError.readLine()) != null)
            {
                sb.append(s);
            }
            prs.getErrorStream().close();
            prs.getInputStream().close();
            prs.getOutputStream().close();

            // 종료까지 대기
            prs.waitFor();

        }catch (Exception e1) {
        }
        finally
        {
            if(prs != null)
                try {
                    prs.destroy();
                } catch(Exception e2) {
                }

        }

    }



    public int vonageArchiveList() throws IOException, OpenTokException {
        LOGGER.info("vonageArchiveList   Start" );
        ArchiveVO avo = new ArchiveVO();

        List<ArchiveVO> rtnvoList = new ArrayList<>();
        rtnvoList = aiInferenceDao.archiveList(avo);

        if (rtnvoList != null) {

            for (ArchiveVO rvo : rtnvoList) {
                  //파일다운로드
                  String strRtn = fileDownload(rvo);
                  //파일 다운로드가 성공하면 다운로드성공 업데이트
                  if (strRtn.equals("0")){
                      aiInferenceDao.udpArchiveDown(rvo);
                  }
            }

        }
        return 0;
    }
    /**
     * 아카리브 파일 다운로드
     * @return
     */
    public String fileDownload(ArchiveVO vo) throws IOException, OpenTokException , MalformedURLException {

        LOGGER.info("파일 다운로드 시작");

        String rtn = "0";
        //String filePath = "/home/administrator/python/video/";
        String filePath = "E://python//video//";
        String fileName = vo.getName();
        String archiveId = vo.getArchiveId();
        String url = "";

        //디렉토리생성
        filePath = filePath + fileName;

        Path directoryPath = Paths.get(filePath);
        try {
            // 디렉토리 생성
            Files.createDirectory(directoryPath);
        } catch (FileAlreadyExistsException e) {
            //LOGGER.info("디렉토리가 이미 존재합니다");
        } catch (NoSuchFileException e) {
            LOGGER.info("디렉토리 경로가 존재하지 않습니다");
        }catch (IOException e) {
            e.printStackTrace();
        }


        Archive archive = null;
        try {
            LOGGER.info("파일 다운로드 시작");

            OpenTok openTok = new OpenTok(apikey, apiSecret);
            archive =openTok.getArchive(archiveId);

            url = archive.getUrl();
            filePath = filePath + "\\" + fileName + ".mp4";

            LOGGER.info("filePath & file name  ==  " + filePath);

            File f= new File((filePath));
            FileUtils.copyURLToFile(new URL(url), f);

            LOGGER.info("파일 다운로드 종료");

        } catch(IOException e){
            System.out.println(e.getMessage());
            rtn="1";
        }

        return rtn;

    }


}


