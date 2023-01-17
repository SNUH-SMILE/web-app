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
    @Autowired
    public testController(TestService testService, BatchService batchService) {

        this.testService = testService;
        this.batchService = batchService;
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

        String bool = batchService.pythonProcessbuilder(executePath);

        String si = batchService.scoreInsert(bvo);

    }

    @RequestMapping(value = "/temperCreate", method = RequestMethod.POST)
    public void bodyTemperatureScheduler() throws IOException, InterruptedException, ParseException {

        String filePath = ai_path+ "temper/temper_file.csv";
        String outfilePath = ai_path + "temper/temper_result.csv";
        String executePath = ai_path + "temper/body_temp_rise.py";

        BatchVO bvo = new BatchVO();
        bvo.setFilePath(filePath);
        bvo.setOutFilePath(outfilePath);

        log.warn("2. 체온 배치 파일 만들기");
        String csvResult = batchService.temperCreate(bvo);

        String bool = batchService.pythonProcessbuilder(executePath);

        batchService.temperInsert(bvo);


    }


}
