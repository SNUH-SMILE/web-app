package kr.co.hconnect.rest;

import kr.co.hconnect.domain.*;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.service.AdmissionService;
import kr.co.hconnect.service.MeasurementResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 메인화면 컨트롤러
 */
@RestController()
@RequestMapping("/api")
public class MeasurementResultRestController {

    /**
     * 메인화면 Service
     */
    private final MeasurementResultService measurementResultService;
    private final AdmissionService admissionService;

    /**
     * 생성자
     *
     * @param measurementResultService 메인화면 Service
     * @param admissionService         입소내역 Service
     */
    @Autowired
    public MeasurementResultRestController(MeasurementResultService measurementResultService, AdmissionService admissionService) {
        this.measurementResultService = measurementResultService;
        this.admissionService = admissionService;
    }

    /**
     * AdmissionId 찾기
     *
     * @param loginId 로그인Id
     * @return AdmissionId
     */
    public String getAdmissionId(String loginId) {
        return admissionService.selectActiveAdmissionByLoginId(loginId).getAdmissionId();
    }


    /**
     * 신규 알림 여부조회
     *
     * @param loginId 로그인 Id
     * @return ExistResult
     */
    //public ExistResult unReadNotice(@RequestParam(value = "loginId") String loginId){
    @RequestMapping(value = "/main/notice", method = RequestMethod.GET)
    public ExistResult unReadNotice(@Valid @RequestBody LoginId loginId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        int result = measurementResultService.unReadNotice(getAdmissionId(loginId.getLoginId()));
        ExistResult existResult = new ExistResult();
        existResult.setCode("00");
        // 알림 있을때
        if (result > 0) {
            existResult.setMessage("신규 알림이 있습니다.");
            existResult.setExistYn("Y");
        }
        //알림 없을때
        else if (result == 0) {
            existResult.setMessage("신규 알림이 없습니다.");
            existResult.setExistYn("N");
        }
        return existResult;
    }

    /**
     * 체온 상세목록 조회
     *
     * @param searchResultInfo 측정결과 검색 조건
     * @return BtResultDetail btResultDetail
     */
    @RequestMapping(value = "/result/bt", method = RequestMethod.GET)
    public BtResultDetail selectBtList(@Valid @RequestBody SearchResultInfo searchResultInfo, BindingResult bindingResult) {
        //유효성 검사
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        searchResultInfo.setAdmissionId(getAdmissionId(searchResultInfo.getLoginId()));
        searchResultInfo.setItemId("I0001");
        BtResultDetail btResultDetail = new BtResultDetail();
        btResultDetail.setCode("00");
        btResultDetail.setMessage("체온 상세목록 조회 성공");
        btResultDetail.setBtList(measurementResultService.selectBpLIst(searchResultInfo));
        return btResultDetail;
    }

    /**
     * 심박수 상세목록 조회
     *
     * @param searchResultInfo 측정결과 검색 조건
     * @return HrResultDetail hrResultDetail
     */
    @RequestMapping(value = "/result/hr", method = RequestMethod.GET)
    public HrResultDetail selectHrList(@Valid @RequestBody SearchResultInfo searchResultInfo, BindingResult bindingResult) {
        //유효성 검사
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        searchResultInfo.setAdmissionId(getAdmissionId(searchResultInfo.getLoginId()));
        searchResultInfo.setItemId("I0002");
        HrResultDetail hrResultDetail = new HrResultDetail();
        hrResultDetail.setCode("00");
        hrResultDetail.setMessage("심박수 상세목록 조회 성공");
        hrResultDetail.setHrList(measurementResultService.selectHrList(searchResultInfo));
        return hrResultDetail;
    }

    /**
     * 산소포화도 상세목록 조회
     *
     * @param searchResultInfo 측정결과 검색 조건
     * @return SpO2ResultDetail spO2ResultDetail
     */
    @RequestMapping(value = "/result/spO2", method = RequestMethod.GET)
    public SpO2ResultDetail selectSpO2List(@Valid @RequestBody SearchResultInfo searchResultInfo, BindingResult bindingResult) {
        //유효성 검사
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        searchResultInfo.setAdmissionId(getAdmissionId(searchResultInfo.getLoginId()));
        searchResultInfo.setItemId("I0003");
        SpO2ResultDetail spO2ResultDetail = new SpO2ResultDetail();
        spO2ResultDetail.setCode("00");
        spO2ResultDetail.setMessage("산소포화도 상세목록 조회 성공");
        spO2ResultDetail.setSpO2List(measurementResultService.selectSpO2List(searchResultInfo));
        return spO2ResultDetail;
    }

    /**
     * 걸음 상세목록 조회
     *
     * @param searchResultInfos 측정결과 검색 조건
     * @return StepCountResultDetail bpResultDetail
     */
    @RequestMapping(value = "/result/step", method = RequestMethod.GET)
    public StepCountResultDetail selectStepList(@Valid @RequestBody SearchResultInfos searchResultInfos, BindingResult bindingResult) {
        //유효성 검사
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        searchResultInfos.setAdmissionId(getAdmissionId(searchResultInfos.getLoginId()));
        searchResultInfos.setItemId("I0004");
        searchResultInfos.setFirstResultType("04");
        searchResultInfos.setSecondResultType("05");

        StepCountResultDetail stepCountResultDetail = new StepCountResultDetail();
        stepCountResultDetail.setCode("00");
        stepCountResultDetail.setMessage("걸음 상세목록 조회 성공");
        stepCountResultDetail.setStepCountList(measurementResultService.selectStepList(searchResultInfos));
        return stepCountResultDetail;
    }

    /**
     * 혈압 상세목록 조회
     *
     * @param searchResultInfos 결과 타입이 2개인 측정결과 검색 조건
     * @return BpResultDetail bpResultDetail
     */
    @RequestMapping(value = "/result/bp", method = RequestMethod.GET)
    public BpResultDetail selectBpList(@Valid @RequestBody SearchResultInfos searchResultInfos, BindingResult bindingResult) {
        //유효성 검사
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        searchResultInfos.setAdmissionId(getAdmissionId(searchResultInfos.getLoginId()));
        searchResultInfos.setItemId("I0005");
        searchResultInfos.setFirstResultType("02");
        searchResultInfos.setSecondResultType("03");

        BpResultDetail bpResultDetail = new BpResultDetail();
        bpResultDetail.setCode("00");
        bpResultDetail.setMessage("혈압 상세목록 조회 성공");
        bpResultDetail.setBpList(measurementResultService.selectBpList(searchResultInfos));
        return bpResultDetail;
    }
}
