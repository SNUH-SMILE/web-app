package kr.co.hconnect.batch;

import com.opentok.exception.OpenTokException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import kr.co.hconnect.service.BatchService;
import org.springframework.stereotype.Controller;

import kr.co.hconnect.vo.*;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
@Controller
public class BatchController {



    private String ai_path = "/usr/local/apache-tomcat-8.5.79/python/";

    private final BatchService batchService;


    @Autowired
    public BatchController(BatchService batchService) {
        this.batchService = batchService;

    }


    /**
     * 1. 스코어 배치 처리 하기
     * ./score 폴더
     */
    @Scheduled(cron="0 0 0/1 * * *")
    public void scoreScheduler() throws IOException, InterruptedException {

        String filePath = ai_path+ "score/score_file.csv";
        String outfilePath = ai_path + "score/score_result.csv";
        String executePath = ai_path + "score/scoring.py";

        //String filePath = "E://python//score//score_file.csv";
        //String outfilePath = "E://python//score//score_result.csv";

        /**
         * 스코어 데이터 파일 생성
         */
        System.out.println("1. 스코어 배치 처리 하기 ");

        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);

        System.out.println("2. 스코어 배치 파일 만들기 ");
        String cre = batchService.scoreCreate(bvo);
        if (!cre.equals("")){

        }

        System.out.println("3. 스코어 파이썬  실행 ");
        String bool = batchService.pythonProcessbuilder(executePath);
        if (!bool.equals("")){

        }
        System.out.println("4. 스코어 배치 파일 임포트 ");
        String si = batchService.scoreInsert(bvo);
        if (!si.equals("")){

        }

    }

    /**
     * 2. 체온 배치 처리 하기
     * ./temperature
     */
    @Scheduled(cron="0 0 0/2 * * *")
    public void bodyTemperatureScheduler() throws IOException, InterruptedException {
        //System.out.println("2. 체온 배치 처리 하기 ");

        String filePath = ai_path+ "temper/temper_file.csv";
        String outfilePath = ai_path + "temper/temper_result.csv";
        String executePath = ai_path + "temper/body_temp_rise.py";

        //String filePath = "E://python//temper//temper_file.csv";
        //String outfilePath = "E://python//temper//temper_result.csv";

        /**
         * 체온 데이터 파일 생성
         */
        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);

        System.out.println("2. 체온 배치 파일 만들기 ");
        batchService.temperCreate(bvo);

        /**
         * 스코어 AI 추론엔진 실행 서비스
         */
        System.out.println("3. 체온 파이썬 엔진 실행 ");
        String bool = batchService.pythonProcessbuilder(executePath);
        if (!bool.equals("")){

        }


        /**
         * 스코어 파일 임포트
         */
        System.out.println("4. 스코어 배치 파일 임포트 ");
        batchService.temperInsert(bvo);

    }

    /**
     * 3. 우울 배치 처리 하기
     * 파이썬 exe 파일
     * ./depressed 폴더
     */
    @Scheduled(cron="0 0 23 * * *")
    public void depressedScheduler() throws IOException, InterruptedException {

        String filePath = ai_path+ "depress/annotation.csv";
        String outfilePath = ai_path + "depress/result.csv";
        String executePath = ai_path + "ise.py";

        //String filePath = "E://python//depress//depress_file.csv";
        //String outfilePath = "E://python//depress//depress_result.csv";

        /**
         * 체온 데이터 파일 생성
         */
        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);

        System.out.println("3. 우울 배치 파일 만들기 ");
        batchService.depressCreate(bvo);

        /**
         * 스코어 AI 추론엔진 실행 서비스
         */
        System.out.println("3. 우울 배치 실행 ");
        //batchService.scoreCreate(bvo);

        /**
         * 스코어 파일 임포트
         */
        System.out.println("4. 우울 배치 파일 임포트 ");
        batchService.depressInsert(bvo);
    }


    /**
     * 파일 다운로드
     */



    //@Scheduled(fixedDelay = 30 * 1000)
    @Scheduled(cron="0 0 20 * * *")
    public void fileDownScheduler() throws IOException, OpenTokException {
        //System.out.println("3. 우울 배치 처리 하기 ");
        //파일 생성          depressedCreate
        //파이썬 실행
        //파일 테이블 insert depressedhist / depressedDelete / depressedinsert


        String rtn = batchService.vonageArchiveList();


        if (!StringUtils.isEmpty(rtn)){

        }

    }



}
