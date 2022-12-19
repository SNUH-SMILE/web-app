package kr.co.hconnect.batch;

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
    @Scheduled(fixedDelay = 10000)
    public void scoreScheduler() throws IOException, InterruptedException {
        // System.out.println("1. 스코어 배치 처리 하기 ");

        String filePath = "/home/administrator/python/score/score_file.csv";
        String outfilePath = "/home/administrator/python/score/score_result.csv";

        //BatchVO bvo = new BatchVO();
        //bvo.setFilePath(filePath);
        //bvo.setOutFilePath(outfilePath);
        //batchService.scoreCreate(bvo);




        //파일 생성               scoreCreate
        //파이썬 실행             scoreExcute
        //파일 테이블 insert      scorehist / scoreDelete / scoreInsert
    }



    /**
     * 2. 체온 배치 처리 하기
     * ./temperature
     */
    @Scheduled(fixedDelay = 20000)
    public void bodyTemperatureScheduler(){
        //System.out.println("2. 체온 배치 처리 하기 ");
        //파일 생성               temperatureCreate
        //파이썬 실행             temperatureExcute
        //파일 테이블 insert      temperaturehist / temperatureDelete / temperatureInsert
    }

    /**
     * 3. 우울 배치 처리 하기
     * 파이썬 exe 파일
     * ./depressed 폴더
     */
    @Scheduled(fixedDelay = 30000)
    public void depressedScheduler(){
        //System.out.println("3. 우울 배치 처리 하기 ");
        //파일 생성          depressedCreate
        //파이썬 실행
        //파일 테이블 insert depressedhist / depressedDelete / depressedinsert
    }


    /**
     * 파일 다운로드
     */



    @Scheduled(fixedDelay = 30000)
    public void fileDownScheduler(){
        //System.out.println("3. 우울 배치 처리 하기 ");
        //파일 생성          depressedCreate
        //파이썬 실행
        //파일 테이블 insert depressedhist / depressedDelete / depressedinsert
    }



}
