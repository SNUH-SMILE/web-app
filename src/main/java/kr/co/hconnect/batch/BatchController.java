package kr.co.hconnect.batch;

import com.opentok.exception.OpenTokException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import kr.co.hconnect.service.BatchService;
import org.springframework.stereotype.Controller;

import kr.co.hconnect.vo.*;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.ParseException;

@Component

@Controller
public class BatchController {

    private static final Logger log = LoggerFactory.getLogger(BatchController.class);

    //경로파일 수정
    private String ai_path="/usr/local/apache-tomcat-8.5.79/python/";

    private String ai_video_path = "/usr/local/apache-tomcat-8.5.79/python/video/";


    private final BatchService batchService;


    @Autowired
    public BatchController(BatchService batchService) {
        this.batchService = batchService;

    }


    /**
     * 1. 스코어 배치 처리 하기
     * ./score 폴더
     * @Scheduled(cron="0 0 0/1 * * *")
     * 매일 1번 22시 (오후10시)
     */
    @Scheduled(cron="0 0 22 * * *")
    public void scoreScheduler() throws IOException, InterruptedException {

        String filePath = ai_path+ "score/score_file.csv";
        String outfilePath = ai_path + "score/score_result.csv";
        String executePath = ai_path + "score/scoring.py";

        log.info("1.상급 병원 전원 예측 배치 처리 하기 ");

        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);

        String cre = batchService.scoreCreate(bvo);
        if (cre.equals("")){
            String bool = batchService.pythonProcessbuilder(executePath);
            if (bool.equals("")){
                String si = batchService.scoreInsert(bvo);
            } else {
                log.warn("상급 병원 전원 예측 배치 실행에서 오류가 발생하였습니다.");
                log.warn("상급 병원 전원 예측 bool >> " + bool);
            }
            if (bool.equals("")){
            }

        } else {
            log.warn("상급 병원 전원 예측 배치 파일 만들기 에서 오류가 발생하였습니다.");
        }

    }

    /**
     * 2. 체온 배치 처리 하기
     * ./temperature
     * @Scheduled(fixedDelay=20000)
     * @Scheduled(cron="0 0 0/2 * * *")
     * 매일 2시간 마다
     */
    @Scheduled(cron="0 0 0/2 * * *")
    public void bodyTemperatureScheduler() throws IOException, InterruptedException {

        String filePath = ai_path+ "temper/temper_file.csv";
        String outfilePath = ai_path + "temper/temper_result.csv";
        String executePath = ai_path + "temper/body_temp_rise.py";

        /**
         * 체온 데이터 파일 생성
         */
        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);

        log.info("2. 체온 배치 파일 만들기");
        String csvResult = batchService.temperCreate(bvo);
        if (csvResult.equals("")) {

            String bool = batchService.pythonProcessbuilder(executePath);
            if (bool.equals("")){
                batchService.temperInsert(bvo);
            } else {
                log.warn("체온상승 배치 실행에서 오류가 발생하였습니다.");
                log.warn("체온상승 bool >> " + bool);
            }
        } else {
            log.warn("체온상승 배치 파일 만들기 에서 오류가 발생하였습니다.");
        }

    }

    /**
     * 3. 우울 배치 처리 하기
     * 영상녹화 파일 다운로드 를 하고 나서 시작한다
     * ./depressed 폴더
     * 매일 1번 23시 (오후11시)
     */
    @Scheduled(cron="0 0 23 * * *")
    public void depressedScheduler() throws IOException, InterruptedException, ParseException {

        String filePath = ai_path+ "depressed/annotation.csv";
        String outfilePath = ai_path + "depressed/result.csv";
        String executePath = ai_path + "depressed/final_exe.py";

        /**
         * 체온 데이터 파일 생성
         */
        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);

        log.info("3. 우울 배치 파일 만들기 ");
        batchService.depressCreate(bvo);

        /**
         * 스코어 AI 추론엔진 실행 서비스
         */
        log.info("3. 우울 배치 실행 ");
        batchService.pythonProcessbuilder(executePath);

        /**
         * 스코어 파일 임포트
         */
        log.info("4. 우울 배치 파일 임포트 ");
        batchService.depressInsert(bvo);

    }

    /**
     * 4. 영상 녹화 파일 다운로드
     * 매일 1번 20시 (오후 08시)
     */
    @Scheduled(cron="0 0 21 * * *")
    public void fileDownScheduler() throws IOException, OpenTokException {
        log.info("5. 화상상담 파일 다운로드 ");
        String rtn = batchService.vonageArchiveList();

    }

    /**
     */
    @Scheduled(fixedDelay=20000)
    public void interviewAlarm10() throws IOException, OpenTokException {
        log.info(" 문진 알람 10시 리스트 및 알람 발송 ");
        batchService.interviewAlarm();

    }

    @Scheduled(cron="0 0 16 * * *")
    public void interviewAlarm14() throws IOException, OpenTokException {
        log.info(" 문진 알람 16시 리스트 및 알람 발송");
        batchService.interviewAlarm();

    }

}
