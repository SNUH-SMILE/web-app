package kr.co.hconnect.batch;

import com.opentok.exception.OpenTokException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import kr.co.hconnect.service.BatchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import kr.co.hconnect.vo.*;

import java.io.IOException;

@Component
@Controller
public class BatchController {

    /**
     * to- do  xml
     * dao
     *
     */

    private final BatchService batchService;


    @Autowired
    public BatchController(BatchService batchService) {
        this.batchService = batchService;

    }




    /**
     * 1. 스코어 배치 처리 하기
     * ./score 폴더
     */
    //@Scheduled(cron="0 0 7 * * *")
    @Scheduled(fixedDelay = 30 * 100000)
    public void scoreScheduler() throws IOException, InterruptedException {
        System.out.println("1. 스코어 배치 처리 하기 ");

         String filePath = "/home/administrator/python/score/score_file.csv";
         String outfilePath = "/home/administrator/python/score/score_result.csv";

        //String filePath = "E://python//score//score_file.csv";
        //String outfilePath = "E://python//score//score_result.csv";

        /**
         * 스코어 데이터 파일 생성
         */
        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);

        System.out.println("2. 스코어 배치 파일 만들기 ");
        batchService.scoreCreate(bvo);

        /**
         * 스코어 AI 추론엔진 실행 서비스
         */
        System.out.println("3. 스코어 배치 실행 ");
        //batchService.scoreCreate(bvo);


        /**
         * 스코어 파일 임포트
         */
        System.out.println("4. 스코어 배치 파일 임포트 ");
        batchService.scoreInsert(bvo);

        //파이썬 실행             scoreExcute

    }



    /**
     * 2. 체온 배치 처리 하기
     * ./temperature
     */
    @Scheduled(fixedDelay = 40 * 10000)
    public void bodyTemperatureScheduler() throws IOException, InterruptedException {
        //System.out.println("2. 체온 배치 처리 하기 ");

        String filePath = "/home/administrator/python/temper/temper_file.csv";
        String outfilePath = "/home/administrator/python/temper/temper_result.csv";

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
        System.out.println("3. 체온 배치 실행 ");
        //batchService.scoreCreate(bvo);


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
    @Scheduled(fixedDelay = 300000)
    public void depressedScheduler() throws IOException, InterruptedException {

        String filePath = "/home/administrator/python/depress/depress_file.csv";
        String outfilePath = "/home/administrator/python/depress/depress_result.csv";

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



    @Scheduled(fixedDelay = 30 * 1000)
    public void fileDownScheduler() throws IOException, OpenTokException {
        //System.out.println("3. 우울 배치 처리 하기 ");
        //파일 생성          depressedCreate
        //파이썬 실행
        //파일 테이블 insert depressedhist / depressedDelete / depressedinsert
        int rtn = batchService.vonageArchiveList();

    }



}
