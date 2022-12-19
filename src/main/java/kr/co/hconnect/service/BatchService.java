package kr.co.hconnect.service;

import com.opentok.Archive;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.hconnect.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.hconnect.repository.*;
import org.springframework.util.ResourceUtils;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.opentok.*;
import com.opentok.exception.OpenTokException;

@Service
public class BatchService extends EgovAbstractServiceImpl{

    private  final  AiInferenceDao aiInferenceDao;

    private int apikey = 47595911;
    private String apiSecret = "2ddde1eb92a2528bd22be0c465174636daca363d";

    private String orgPath ="E:\\projects\\snuh_smile\\web-app\\src\\main\\resources\\inference\\score\\";

    @Autowired
    public BatchService(AiInferenceDao dao) {
        this.aiInferenceDao = dao;
    }


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
                tData += "," + "혈압";       //혈압
                tData += "," + "체온";     //체온
                tData += "," + "고혈압여부";    //고혈압 여부

                fw.write(tData);
                fw.newLine();

                for (ScoreVO dt : dataList) {
                    String aData = "";
                    aData = dt.getAdmissionId().toString();   //환자 id
                    aData += "," + dt.getAge();    // 나이
                    aData += "," + dt.getPr();     //심박수
                    aData += "," + dt.getSpo2();   // 산소포화도
                    aData += "," + dt.getSbp();    //수축기 혈압
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

    public String scoreInsert(BatchVO vo) throws IOException, InterruptedException {

        String rtn = "0";
        //csv file open
        //     /home/administrator/pythonCode
        File csv = new File(vo.getOutFilePath());

        BufferedReader br = null;
        String line = "";

        try{
            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null){
                String[] lineArr = line.split(",");

                AiInferenceVO entityVO = new AiInferenceVO();
                entityVO.setAdmissionId(lineArr[0]);
                entityVO.setInfDiv("10");
                entityVO.setInfValue(Float.parseFloat(lineArr[1].toString()));

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
    public void temperature() throws FdlException {
        //create
        String filePath = "./resources/inference/temperature/temp.csv";
        int rtnScore = csvCreate(filePath);
        if (rtnScore != 0) {

        }
        //excute
        //scorehist
        //scoreDelete
        //scoreinsert
    }
    @Transactional(rollbackFor = Exception.class)
    public void depress() throws FdlException {
        //create
        String filePath = "./resources/inference/depressed/depressed.csv";      //우울
        int rtnScore = csvCreate(filePath);
        if (rtnScore != 0) {

        }
        //excute
        //scorehist
        //scoreDelete
        //scoreinsert
    }

    public int csvCreate( String filePath) {
        int resultCount = 0;

        List list= null;
        //데이터를 받아오고 파일로 쓰기
        try {
            //csv 파일의 기존 값에 이어쓰려면 위처럼 tru를 지정하고 기존갑을 덮어 쓰려면 true를 삭제한다
            BufferedWriter fw = new BufferedWriter(new FileWriter(filePath));

            //쿼리 를 한다.
            //
            List<ScoreVO> dataList = aiInferenceDao.scoreList();

            if (dataList.size() > 0 ) {
                for (ScoreVO dt : dataList) {
                    String aData = "";
                    aData = dt.getAdmissionId();   //환자 id
                    aData += "," + dt.getAge();    // 나이
                    aData += "," + dt.getPr();     //심박수
                    aData += "," + dt.getSpo2();   // 산소포화도
                    aData += "," + dt.getSbp();    //수축기 혈압
                    aData += "," + dt.getDbp();    //이완기 혈압
                    aData += "," + dt.getBt();     //체온
                    aData += "," + dt.getAche();   //통증

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


