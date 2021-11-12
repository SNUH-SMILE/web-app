package kr.co.hconnect.controller;

import kr.co.hconnect.service.ComCdManagerService;
import kr.co.hconnect.vo.ComCdDetailVO;
import kr.co.hconnect.vo.ComCdVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 공통코드 및 공통 세부코드 관련 Controller
 */
@Controller
@RequestMapping(value = "/common")
public class CommonCodeController {

    /**
     * Slf4j Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonCodeController.class);

    /**
     * 공통코드 서비스
     */
    private final ComCdManagerService commonCodeService;

    /**
     * 생성자
     * @param commonCodeService - 공통코드 서비스
     */
    @Autowired
    public CommonCodeController(ComCdManagerService commonCodeService) {
        this.commonCodeService = commonCodeService;
    }


    /**
     * 공토코드 Home 뷰
     * @return 공통코드 뷰
     */
    @RequestMapping(value = "/commonCode.do")
    public String commonCodeHome() { return "sys/comCd"; }

    /**
     * 공통코드 목록 조회
     * @param comCd - 공통코드
     * @param comCdNm - 공통코드명
     * @param useYn - 코드 사용여부
     * @return List<ComCdVO> 공통코드 목록
     */
    @RequestMapping(value = "/commonCodeList.ajax")
    @ResponseBody
    public List<ComCdVO> selectComCdList(@RequestParam(value = "comCd", required = false) String comCd
            , @RequestParam(value = "comCdNm", required = false) String comCdNm
            , @RequestParam(value = "useYn", required = false) boolean useYn) {

        ComCdVO comCdVO = new ComCdVO();
        comCdVO.setComCd(comCd);
        comCdVO.setComCdNm(comCdNm);
        comCdVO.setUseYn(useYn ? "N" : null);

        return commonCodeService.selectComCdList(comCdVO);
    }


    /**
     * 신규 공통코드
     * @param comCdVO - 공통코드 VO
     * @return List<ComCdVO> - 공통코드VO 목록
     */
    @RequestMapping(value = "/commonCode.ajax", method = RequestMethod.POST)
    @ResponseBody
    public List<ComCdVO> insertCommonCode(@ModelAttribute ComCdVO comCdVO) {
        commonCodeService.insertCommonCode(comCdVO);
        //공통코드 목록 변수
        return commonCodeService.selectComCdList(new ComCdVO());
    }

    /**
     * 공통코드 수정
     * @param comCdVO - 공통코드 VO
     * @return ResponseEntity<?> - Spring Framework에서 제공하는 HttpEntity 구현 클래스.
     *                             body에 List<ComCdDetailListVO> 담고 응답.
     *                             1. 400 - ERROR 시  message(String) 반환
     *                             2. 200 - List<ComCdVO> 목록 반환
     */
    @RequestMapping(value = "/commonCode.ajax", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> saveCommonCode(@RequestBody ComCdVO comCdVO) {
        try {
            commonCodeService.updateCommonCode(comCdVO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(e.getMessage());
        }

        List<ComCdVO> comCdVOList = commonCodeService.selectComCdList(new ComCdVO());

        LOGGER.info("comCdVO Values {} ", comCdVOList);

        return ResponseEntity
                .status(200)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(comCdVOList);

    }

    /**
     * 공통코드 비활성화 (useYn -> N)
     * @param comCd - 공통코드
     * @return ResponseEntity<?> - Spring Framework에서 제공하는 HttpEntity 구현 클래스.
     *                             body에 List<ComCdDetailListVO> 담고 응답.
     *                             1. 400 - ERROR 시  message(String) 반환
     *                             2. 200 - List<ComCdVO> 목록 반환
     */
    @RequestMapping(value = "/commonCode.ajax", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCommonCode(@RequestParam(value = "comCd") String comCd){

        ComCdVO comCdVO = new ComCdVO();

        if(StringUtils.isEmpty(comCd)){
            return ResponseEntity.status(400)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body("공통코드는 필수값 입니다. 다시 시도해주세요.");
        }

        comCdVO.setComCd(comCd);

        //임시 테스트용 데이터 -- TODO 나중에 삭제 (테스트용  regId, updId)
        comCdVO.setRegId("dev");
        comCdVO.setUpdId("dev");

        try {
            commonCodeService.deleteCommonCode(comCdVO);
        }catch (RuntimeException e){
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(e.getMessage());
        }

        List<ComCdVO> comCdVOList = commonCodeService.selectComCdList(new ComCdVO());

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(comCdVOList);
    }

    /**
     * 세부코드 목록 조회
     * @param comCd - 공통코드
     * @return List<ComCdDetailVO> - 세부코드 목록
     */
    @RequestMapping(value = "/commonDetailList.ajax", method = RequestMethod.GET)
    @ResponseBody
    public List<ComCdDetailVO> selectComCdDetailList(@RequestParam(value = "comCd") String comCd
            ,@RequestParam(value = "detailCd", required = false) String detailCd
            ,@RequestParam(value = "detailCdNm", required = false) String detailCdNm
            ,@RequestParam(value = "useYn", required = false) boolean useYn) {
        ComCdDetailVO comCdDetailVO = new ComCdDetailVO();
        comCdDetailVO.setComCd(comCd);
        comCdDetailVO.setDetailCdNm(detailCdNm);
        comCdDetailVO.setUseYn(useYn ?"N":null);

        if(StringUtils.isNotEmpty(detailCd)){ comCdDetailVO.setDetailCd(detailCd); }

        return commonCodeService.selectComDetailList(comCdDetailVO);
    }

    /**
     * 세부코드 조회 - 중복 세부코드 확인용
     * @param comCd - 공통코드
     * @param detailCd - 세부코드
     * @return ComCdDetailVO - 세부코드 VO
     */
    @RequestMapping(value = "/duplicateDetailCodeCheck.ajax", method = RequestMethod.GET)
    @ResponseBody
    public ComCdDetailVO selectDuplicateDetailCodeCheck(@RequestParam(value = "comCd") String comCd
            ,@RequestParam(value = "detailCd") String detailCd) {
        ComCdDetailVO comCdDetailVO = new ComCdDetailVO();
        comCdDetailVO.setComCd(comCd);
        comCdDetailVO.setDetailCd(detailCd);
        return commonCodeService.selectComDetailCdByCode(comCdDetailVO);
    }


    /**
     * 세부코드 신규 등록
     * @param comCdDetailVO - 세부코드 VO
     * @return ResponseEntity<List<ComCdDetailVO>> - Spring Framework에서 제공하는 HttpEntity 구현 클래스.
     *                                               body에 List<ComCdDetailListVO> 담고 응답.
     */
    @RequestMapping(value = "/commonDetail.ajax", method = RequestMethod.POST)
    public ResponseEntity<List<ComCdDetailVO>> insertComCdDetail(@ModelAttribute ComCdDetailVO comCdDetailVO) {
        commonCodeService.insertComCdDetail(comCdDetailVO);

        ComCdDetailVO selectComCdDetailVO = new ComCdDetailVO();
        selectComCdDetailVO.setComCd(comCdDetailVO.getComCd());
        List<ComCdDetailVO> comCdDetailListVOList = commonCodeService.selectComDetailList(selectComCdDetailVO);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(comCdDetailListVOList);
    }

    /**
     * 세부코드 수정
     * @param comCdDetailVO - 세부코드 VO
     * @return ResponseEntity<?> - Spring Framework에서 제공하는 HttpEntity 구현 클래스.
     *                             body에 List<ComCdDetailListVO> 담고 응답.
     *                             1. 400 - ERROR 시  message(String) 반환
     *                             2. 200 - List<ComCdVO> 목록 반환
     */
    @RequestMapping(value = "/commonDetail.ajax", method = RequestMethod.PUT)
    public ResponseEntity<?> saveComCdDetail(@RequestBody ComCdDetailVO comCdDetailVO) {
        try{
            commonCodeService.updateComCdDetail(comCdDetailVO);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(e.getMessage());
        }

        ComCdDetailVO selectComCdDetailVO = new ComCdDetailVO();
        selectComCdDetailVO.setComCd(comCdDetailVO.getComCd());
        List<ComCdDetailVO> comCdDetailListVOList = commonCodeService.selectComDetailList(selectComCdDetailVO);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(comCdDetailListVOList);
    }


    /**
     * 세부코드 비활성화 (종료)
     * @param comCd - 공통코드
     * @param detailCd - 세부코드
     * @return ResponseEntity<?> - Spring Framework에서 제공하는 HttpEntity 구현 클래스.
     *                             body에 List<ComCdDetailListVO> 담고 응답.
     *                             1. 400 - ERROR 시  message(String) 반환
     *                             2. 200 - List<ComCdDetailVO> 목록 반환
     */
    @RequestMapping(value = "/commonDetail.ajax", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCommonDetail(@RequestParam(value = "comCd") String comCd
            ,@RequestParam(value = "detailCd") String detailCd){

        ComCdDetailVO comCdDetailVO = new ComCdDetailVO();


        if(StringUtils.isEmpty(comCd) || StringUtils.isEmpty(detailCd)){
            return ResponseEntity.status(400)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body("공통코드는 필수값 입니다. 다시 시도해주세요.");
        }

        comCdDetailVO.setComCd(comCd);
        comCdDetailVO.setDetailCd(detailCd);

        if(commonCodeService.selectComDetailCdByCode(comCdDetailVO) == null){
            return ResponseEntity.status(400)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body("존재하지 않는 세부코드 입니다. 다시 시도해주세요.");
        }


        //임시 테스트용 데이터 -- TODO 나중에 삭제 (테스트용  regId, updId)
        comCdDetailVO.setRegId("dev");
        comCdDetailVO.setUpdId("dev");

        try{
            commonCodeService.deleteCommonDetail(comCdDetailVO);
        }catch (RuntimeException e){
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(comCdDetailVO.getComCd() + " :  " + e.getMessage());
        }

        ComCdDetailVO selectComCdDetailVO = new ComCdDetailVO();
        selectComCdDetailVO.setComCd(comCd);
        List<ComCdDetailVO> comCdDetailList = commonCodeService.selectComDetailList(selectComCdDetailVO);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(comCdDetailList);
    }

}
