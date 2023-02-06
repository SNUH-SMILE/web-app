package kr.co.hconnect.controller;
import com.opentok.exception.OpenTokException;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.hconnect.batch.BatchController;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.VoValidationGroups;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.exception.NotFoundUserInfoException;
import kr.co.hconnect.jwt.TokenDetailInfo;
import kr.co.hconnect.repository.TeleHealthDao;
import kr.co.hconnect.service.UserService;
import kr.co.hconnect.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import kr.co.hconnect.service.TestService;
import kr.co.hconnect.service.BatchService;
import kr.co.hconnect.service.TeleHealthService;



/**
 * 사용자 관리 Controller
 */
@RestController
@RequestMapping("/api/test")
public class testController {
    private static final Logger log = LoggerFactory.getLogger(testController.class);
/*
    @Value("${ai.path}")
    private String ai_path;

    @Value("${ai.video.path}")
    private String ai_video_path;
*/

    private String ai_path="/usr/local/apache-tomcat-8.5.79/python/";

    private String ai_video_path ="/usr/local/apache-tomcat-8.5.79/python/video/";




    private final TestService testService;

    private final BatchService batchService;

    private final TeleHealthService teleHealthService;



    @Autowired
    public testController(TestService testService, BatchService batchService, TeleHealthService teleHealthService) {

        this.testService = testService;
        this.batchService = batchService;
        this.teleHealthService = teleHealthService;
    }



    @RequestMapping(value = "/fileDown", method = RequestMethod.POST)
    public ResponseBaseVO<testVO> insertDrugDose(@Validated(VoValidationGroups.add.class) @RequestBody testVO vo
        , BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {

        ResponseBaseVO<testVO> responseVO = new ResponseBaseVO<>();

        try {



            String rtn = testService.fileDownload4(vo);

            vo.setArchiveUrl(rtn);

            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("fileDown ok");
            responseVO.setResult(vo);

        } catch (NotFoundUserInfoException e) {
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (OpenTokException e) {
            throw new RuntimeException(e);
        }

        return responseVO;
    }




    @RequestMapping(value = "/archiveFileDown", method = RequestMethod.POST)
    public ResponseBaseVO<testVO> archiveFileDown(@Validated(VoValidationGroups.add.class) @RequestBody testVO vo
        , BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {

        ResponseBaseVO<testVO> responseVO = new ResponseBaseVO<>();
        try {
            String rtn = testService.ArchiveList(vo);
            vo.setArchiveUrl(rtn);
            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("fileDown ok");
            responseVO.setResult(vo);

        } catch (NotFoundUserInfoException e) {
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (OpenTokException e) {
            throw new RuntimeException(e);
        }

        return responseVO;
    }




    /**
     * CSV FILE 만들기
     * @param vo
     * @param bindingResult
     * @param tokenDetailInfo
     * @return
     */
    @RequestMapping(value = "/csvFile", method = RequestMethod.POST)
    public ResponseBaseVO<testVO> createCsv(@Validated(VoValidationGroups.add.class) @RequestBody testVO vo
        , BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {

        ResponseBaseVO<testVO> responseVO = new ResponseBaseVO<>();

        try {

            int rtn = testService.csvCreate(vo);

            vo.setCount(rtn);

            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("csv File ok");
            responseVO.setResult(vo);

        } catch (NotFoundUserInfoException e) {
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }

        return responseVO;
    }

    @RequestMapping(value = "/valueCheck", method = RequestMethod.POST)
    public ResponseBaseVO<testVO> valueCheck(@Validated(VoValidationGroups.add.class) @RequestBody testVO vo
        , BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {

        ResponseBaseVO<testVO> responseVO = new ResponseBaseVO<>();
        try {

            String bioMetaDataFormat = "프로퍼티 ai_path = %s  ai_video_path = %s";
            String msg;

            msg= String.format(bioMetaDataFormat
                , ai_path
                , ai_video_path
            );
            //message

            System.out.println("프로퍼티 ai_path = %s  ai_video_path = %s");

            System.out.println(ai_path);

            System.out.println(ai_video_path);

            vo.setMessage(msg);

            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("valueCheck");
            responseVO.setResult(vo);

        } catch (NotFoundUserInfoException e) {
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }

        return responseVO;
    }


    @RequestMapping(value = "/CreateScore", method = RequestMethod.POST)
    public void scoreScheduler() throws IOException, InterruptedException {

        String filePath = ai_path+ "score/score_file.csv";
        String outfilePath = ai_path + "score/score_result.csv";
        String executePath = ai_path + "score/scoring.py";

        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);

        String cre = testService.scoreCreate(bvo);

    }

    @RequestMapping(value = "/ScoreExe", method = RequestMethod.POST)
    public void ScoreExe() throws IOException, InterruptedException {

        String filePath = ai_path+ "score/score_file.csv";
        String outfilePath = ai_path + "score/score_result.csv";
        String executePath = ai_path + "score/scoring.py";

        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);


        String exeResult = batchService.pythonProcessbuilder(executePath);
        log.info("score Exe >>>" + exeResult);

        String insResult = batchService.scoreInsert(bvo);
        log.info("score insert >>>" + insResult);

    }
    @RequestMapping(value = "/ScoreIns", method = RequestMethod.POST)
    public void Scoreins() throws IOException, InterruptedException {

        String filePath = ai_path+ "score/score_file.csv";
        String outfilePath = ai_path + "score/score_result.csv";
        String executePath = ai_path + "score/scoring.py";

        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);

        String insResult = batchService.scoreInsert(bvo);
        log.info("score insert >>>" + insResult);

    }


    @RequestMapping(value = "/scoreCsv", method = RequestMethod.POST)
    public void scoreCsv() throws IOException, InterruptedException {

        String filePath = ai_path+ "score/score_file.csv";
        String outfilePath = ai_path + "score/score_result.csv";
        String executePath = ai_path + "score/scoring.py";

        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);

        batchService.scoreCreate(bvo);
    }

    @RequestMapping(value = "/scoreExe", method = RequestMethod.POST)
    public void scoreExe() throws IOException, InterruptedException {

        String filePath = ai_path+ "score/score_file.csv";
        String outfilePath = ai_path + "score/score_result.csv";
        String executePath = ai_path + "score/scoring.py";

        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);

        String exeResult = batchService.pythonProcessbuilder(executePath);
        log.info("scoreExe >>>" + exeResult);

        // String insResult = batchService.temperInsert(bvo);
        // log.info("temper insert >>>" + insResult);
    }
    @RequestMapping(value = "/scoreinsrt", method = RequestMethod.POST)
    public void scoreinsrt() throws IOException, InterruptedException {

        String filePath = ai_path+ "score/score_file.csv";
        String outfilePath = ai_path + "score/score_result.csv";
        String executePath = ai_path + "score/scoring.py";

        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);

       batchService.scoreInsert(bvo);

    }


    @RequestMapping(value = "/temperCsv", method = RequestMethod.POST)
    public void temperCsv() throws IOException, InterruptedException {

        String filePath = ai_path+ "temper/temper_file.csv";
        String outfilePath = ai_path + "temper/temper_result.csv";
        String executePath = ai_path + "temper/body_temp_rise.py";

        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);
        bvo.setTestFlag("1");

        String csvResult = batchService.temperCreate(bvo);

    }
    @RequestMapping(value = "/temperExe", method = RequestMethod.POST)
    public void temperExe() throws IOException, InterruptedException {

        String filePath = ai_path+ "temper/temper_file.csv";
        String outfilePath = ai_path + "temper/temper_result.csv";
        String executePath = ai_path + "temper/body_temp_rise.py";

        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);

        String exeResult = batchService.pythonProcessbuilder(executePath);
        log.info("temperExe >>>" + exeResult);

        // String insResult = batchService.temperInsert(bvo);
        // log.info("temper insert >>>" + insResult);
    }

    @RequestMapping(value = "/temperInset", method = RequestMethod.POST)
    public void temperinsert() throws IOException, InterruptedException {

        String filePath = ai_path+ "temper/temper_file.csv";
        String outfilePath = ai_path + "temper/temper_result.csv";
        String executePath = ai_path + "temper/body_temp_rise.py";

        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);

        String csvResult = batchService.temperInsert(bvo);

    }

    @RequestMapping(value = "/deprecsv", method = RequestMethod.POST)
    public void deprecsv() throws IOException, InterruptedException, ParseException {

        String filePath = ai_path+ "depressed/annotation.csv";
        String outfilePath = ai_path + "depressed/result.csv";
        String executePath = ai_path + "depressed/final_exe.py";

        /**
         *
         */
        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);

        System.out.println("3. 우울 배치 파일 만들기 ");
        batchService.depressCreate(bvo);

    }

    @RequestMapping(value = "/depreExe", method = RequestMethod.POST)
    public void depreExe() throws IOException, InterruptedException, ParseException {

        String filePath = ai_path+ "depressed/annotation.csv";
        String outfilePath = ai_path + "depressed/result.csv";
        String executePath = ai_path + "depressed/final_exe.py";

        /**
         *
         */
        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);


        String exeResult = batchService.pythonProcessbuilder(executePath);
        log.info("depressed Exe >>>" + exeResult);

        String insResult = batchService.depressInsert(bvo);
        log.info("depressed insert >>>" + insResult);


    }

    @RequestMapping(value = "/depreinsert", method = RequestMethod.POST)
    public void depreinsert() throws IOException, InterruptedException, ParseException {

        String filePath = ai_path+ "depressed/annotation.csv";
        String outfilePath = ai_path + "depressed/result.csv";
        String executePath = ai_path + "depressed/final_exe.py";

        /**
         * 체온 데이터 파일 생성
         */
        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);

        System.out.println("3. 우울 배치 파일 만들기 ");
        batchService.depressInsert(bvo);
    }

    @RequestMapping(value = "/interviewAlarm", method = RequestMethod.POST)
    public void interviewAlarm()  {
        log.info(" 문진 알람 리스트 및 알람 발송 시작");
        batchService.interviewAlarm();
        log.info(" 문진 알람 리스트 및 알람 발송 종료");
    }




}
