package kr.co.hconnect.rest;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.ItemId;
import kr.co.hconnect.common.ResultType;
import kr.co.hconnect.domain.*;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.service.AdmissionService;
import kr.co.hconnect.service.MeasurementResultService;
import kr.co.hconnect.service.ResultService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    /**
     * 격리/입소내역 관리 Service
     */
    private final AdmissionService admissionService;

    /**
     * 측정결과 Service
     */
    private final ResultService resultService;


    private final MessageSource messageSource;

    /**
     * 생성자
     *
     * @param measurementResultService 메인화면 Service
     * @param admissionService         입소내역 Service
     * @param resultService            측정결과 Service
     */
    @Autowired
    public MeasurementResultRestController(MeasurementResultService measurementResultService, AdmissionService admissionService, ResultService resultService, MessageSource messageSource) {
        this.measurementResultService = measurementResultService;
        this.admissionService = admissionService;
        this.resultService = resultService;
        this.messageSource = messageSource;
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
     * 메인컨텐츠 조회
     *
     * @param loginId 로그인Id
     * @return MainContentDetail
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public MainContentDetail mainContent(@Valid @RequestBody LoginId loginId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        MainContentDetail mainContentDetail = measurementResultService.mainService(loginId.getLoginId());
        int resultListsSize = 0;
        resultListsSize += mainContentDetail.getTodayBpList().size();
        resultListsSize += mainContentDetail.getTodayBtList().size();
        resultListsSize += mainContentDetail.getTodayHrList().size();
        resultListsSize += mainContentDetail.getTodaySpO2List().size();
        resultListsSize += mainContentDetail.getTodaySleepTimeList().size();
        resultListsSize += mainContentDetail.getTodayStepCountList().size();
        if(resultListsSize>0){
            mainContentDetail.setCode(ApiResponseCode.SUCCESS.getCode());
            mainContentDetail.setMessage(messageSource.getMessage("message.success.searchResultList", null, Locale.getDefault()));
        }
        else if(resultListsSize==0){
            mainContentDetail.setCode(ApiResponseCode.SUCCESS.getCode());
            mainContentDetail.setMessage(messageSource.getMessage("message.notfound.searchResultList", null, Locale.getDefault()));
        }
        return mainContentDetail;
    }


    /**
     * 신규 알림 여부조회
     *
     * @param loginId 로그인 Id
     * @return ExistResult
     */
    @RequestMapping(value = "/main/notice", method = RequestMethod.GET)
    public ExistResult unReadNotice(@Valid @RequestBody LoginId loginId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        int result = measurementResultService.unReadNotice(getAdmissionId(loginId.getLoginId()));
        ExistResult existResult = new ExistResult();
        existResult.setCode(ApiResponseCode.SUCCESS.getCode());
        // 알림 있을때
        if (result > 0) {
            existResult.setMessage(messageSource.getMessage("message.found.notice", null, Locale.getDefault()));;
            existResult.setExistYn("Y");
        }
        //알림 없을때
        else if (result == 0) {
            existResult.setMessage(messageSource.getMessage("message.notfound.notice", null, Locale.getDefault()));;
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
    @RequestMapping(value = "/results/bt", method = RequestMethod.GET)
    public BtResultDetail selectBtList(@Valid @RequestBody SearchResultInfo searchResultInfo, BindingResult bindingResult) {
        //유효성 검사
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        searchResultInfo.setAdmissionId(getAdmissionId(searchResultInfo.getLoginId()));
        BtResultDetail btResultDetail = new BtResultDetail();
        btResultDetail.setBtList(measurementResultService.selectBtLIst(searchResultInfo));
        if(btResultDetail.getBtList().size() > 0){
            btResultDetail.setCode(ApiResponseCode.SUCCESS.getCode());
            btResultDetail.setMessage(messageSource.getMessage("message.success.searchResultList", null, Locale.getDefault()));

        }
        else if(btResultDetail.getBtList().size() == 0){
            btResultDetail.setCode(ApiResponseCode.SUCCESS.getCode());
            btResultDetail.setMessage(messageSource.getMessage("message.notfound.searchResultList", null, Locale.getDefault()));
        }
        return btResultDetail;
    }

    /**
     * 심박수 상세목록 조회
     *
     * @param searchResultInfo 측정결과 검색 조건
     * @return HrResultDetail hrResultDetail
     */
    @RequestMapping(value = "/results/hr", method = RequestMethod.GET)
    public HrResultDetail selectHrList(@Valid @RequestBody SearchResultInfo searchResultInfo, BindingResult bindingResult) {
        //유효성 검사
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        searchResultInfo.setAdmissionId(getAdmissionId(searchResultInfo.getLoginId()));
        HrResultDetail hrResultDetail = new HrResultDetail();
        hrResultDetail.setHrList(measurementResultService.selectHrList(searchResultInfo));
        if(hrResultDetail.getHrList().size() > 0){
            hrResultDetail.setCode(ApiResponseCode.SUCCESS.getCode());
            hrResultDetail.setMessage(messageSource.getMessage("message.success.searchResultList", null, Locale.getDefault()));
        }
        else if(hrResultDetail.getHrList().size() == 0){
            hrResultDetail.setCode(ApiResponseCode.SUCCESS.getCode());
            hrResultDetail.setMessage(messageSource.getMessage("message.notfound.searchResultList", null, Locale.getDefault()));
        }
        return hrResultDetail;
    }

    /**
     * 산소포화도 상세목록 조회
     *
     * @param searchResultInfo 측정결과 검색 조건
     * @return SpO2ResultDetail spO2ResultDetail
     */
    @RequestMapping(value = "/results/spO2", method = RequestMethod.GET)
    public SpO2ResultDetail selectSpO2List(@Valid @RequestBody SearchResultInfo searchResultInfo, BindingResult bindingResult) {
        //유효성 검사
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        searchResultInfo.setAdmissionId(getAdmissionId(searchResultInfo.getLoginId()));

        SpO2ResultDetail spO2ResultDetail = new SpO2ResultDetail();
        spO2ResultDetail.setSpO2List(measurementResultService.selectSpO2List(searchResultInfo));
        if(spO2ResultDetail.getSpO2List().size() > 0){
            spO2ResultDetail.setCode(ApiResponseCode.SUCCESS.getCode());
            spO2ResultDetail.setMessage(messageSource.getMessage("message.success.searchResultList", null, Locale.getDefault()));
        }
        else if(spO2ResultDetail.getSpO2List().size() == 0){
            spO2ResultDetail.setCode(ApiResponseCode.SUCCESS.getCode());
            spO2ResultDetail.setMessage(messageSource.getMessage("message.notfound.searchResultList", null, Locale.getDefault()));
        }
        return spO2ResultDetail;
    }

    /**
     * 걸음 상세목록 조회
     *
     * @param searchResultInfos 측정결과 검색 조건
     * @return StepCountResultDetail bpResultDetail
     */
    @RequestMapping(value = "/results/stepCount", method = RequestMethod.GET)
    public StepCountResultDetail selectStepList(@Valid @RequestBody SearchResultInfos searchResultInfos, BindingResult bindingResult) {
        //유효성 검사
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        searchResultInfos.setAdmissionId(getAdmissionId(searchResultInfos.getLoginId()));

        StepCountResultDetail stepCountResultDetail = new StepCountResultDetail();
        stepCountResultDetail.setStepCountList(measurementResultService.selectStepList(searchResultInfos));
        if(stepCountResultDetail.getStepCountList().size() > 0){
            stepCountResultDetail.setCode(ApiResponseCode.SUCCESS.getCode());
            stepCountResultDetail.setMessage(messageSource.getMessage("message.success.searchResultList", null, Locale.getDefault()));
        }
        else if(stepCountResultDetail.getStepCountList().size() == 0){
            stepCountResultDetail.setCode(ApiResponseCode.SUCCESS.getCode());
            stepCountResultDetail.setMessage(messageSource.getMessage("message.notfound.searchResultList", null, Locale.getDefault()));
        }
        return stepCountResultDetail;
    }

    /**
     * 혈압 상세목록 조회
     *
     * @param searchResultInfos 결과 타입이 2개인 측정결과 검색 조건
     * @return BpResultDetail bpResultDetail
     */
    @RequestMapping(value = "/results/bp", method = RequestMethod.GET)
    public BpResultDetail selectBpList(@Valid @RequestBody SearchResultInfos searchResultInfos, BindingResult bindingResult) {
        //유효성 검사
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        searchResultInfos.setAdmissionId(getAdmissionId(searchResultInfos.getLoginId()));

        BpResultDetail bpResultDetail = new BpResultDetail();
        bpResultDetail.setBpList(measurementResultService.selectBpList(searchResultInfos));
        if(bpResultDetail.getBpList().size() > 0){
            bpResultDetail.setCode(ApiResponseCode.SUCCESS.getCode());
            bpResultDetail.setMessage(messageSource.getMessage("message.success.searchResultList", null, Locale.getDefault()));
        }
        else if(bpResultDetail.getBpList().size() == 0){
            bpResultDetail.setCode(ApiResponseCode.SUCCESS.getCode());
            bpResultDetail.setMessage(messageSource.getMessage("message.notfound.searchResultList", null, Locale.getDefault()));
        }
        return bpResultDetail;
    }


    /**
     * 수면시간 상세목록 조회
     *
     * @param searchSleepResultInfo 수면 측정결과 검색 조건
     * @return SleepTimeResultDetail sleepTimeResultDetail
     */
    @RequestMapping(value = "/results/sleepTime", method = RequestMethod.GET)
    public SleepTimeResultDetail selectSleepList(@Valid @RequestBody SearchSleepResultInfo searchSleepResultInfo
            , BindingResult bindingResult) {
        //유효성 검사
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        searchSleepResultInfo.setAdmissionId(getAdmissionId(searchSleepResultInfo.getLoginId()));


        //SleepTimeResultDetail 세팅
        SleepTimeResultDetail sleepTimeResultDetail = new SleepTimeResultDetail();
        sleepTimeResultDetail.setResultStartDateTime(searchSleepResultInfo.getResultStartDateTime());
        sleepTimeResultDetail.setResultEndDateTime(searchSleepResultInfo.getResultEndDateTime());

        sleepTimeResultDetail.setSleepTimeList(measurementResultService.selectSleepTimeList(searchSleepResultInfo));
        if(sleepTimeResultDetail.getSleepTimeList().size() > 0){
            sleepTimeResultDetail.setCode(ApiResponseCode.SUCCESS.getCode());
            sleepTimeResultDetail.setMessage(messageSource.getMessage("message.success.searchResultList", null, Locale.getDefault()));
            //총 수면시간
            List<SleepTimeResult> sleepTimeResultList = sleepTimeResultDetail.getSleepTimeList();
            int tempTotalSleep = measurementResultService.getTempTotalSleep(sleepTimeResultList);
            sleepTimeResultDetail.setTotalSleepTime(Integer.toString(tempTotalSleep / 60));
        }
        else if(sleepTimeResultDetail.getSleepTimeList().size() == 0){
            sleepTimeResultDetail.setCode(ApiResponseCode.SUCCESS.getCode());
            sleepTimeResultDetail.setMessage(messageSource.getMessage("message.notfound.searchResultList", null, Locale.getDefault()));
            sleepTimeResultDetail.setTotalSleepTime("0");
        }


        return sleepTimeResultDetail;
    }


    /**
     * 체온 측정 정보 저장
     *
     * @param resultInfo 체온 측정 결과 정보
     * @return BaseResponse
     */
    @RequestMapping(value = "/results/bt", method = RequestMethod.POST)
    public BaseResponse saveBtResult(@Valid @RequestBody SaveBtResultInfo resultInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        String loginId = resultInfo.getLoginId();

        // 측정결과 저장정보 생성
        List<ResultSavedInformationData> resultSavedInformationDataList = new ArrayList<>();
        for (BtResult oriResult : resultInfo.getResults()) {
            // 01. 측정결과 내역
            Result result = new Result();
            BeanUtils.copyProperties(oriResult, result, "resultSeq", "admissionId", "itemId");
            result.setItemId(ItemId.BODY_TEMPERATURE.getItemId());

            // 02. 측정결과 상세 내역
            ResultDetail resultDetail = new ResultDetail();
            resultDetail.setResultType(ResultType.SINGLE_RESULT.getResultType());
            resultDetail.setResult(oriResult.getResult());

            List<ResultDetail> resultDetailList = new ArrayList<>();
            resultDetailList.add(resultDetail);

            // 03. 측정결과 저장정보 구성
            ResultSavedInformationData data = new ResultSavedInformationData();
            data.setLoginId(loginId);
            data.setResult(result);
            data.setResultDetails(resultDetailList);

            // 측정결과 저장정보 추가
            resultSavedInformationDataList.add(data);
        }

        // 측정결과 저장
        resultService.saveResult(resultSavedInformationDataList);

        // 반환정보
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ApiResponseCode.SUCCESS.getCode());
        baseResponse.setMessage(messageSource.getMessage("message.success.saveResult", null, Locale.getDefault()));

        return baseResponse;
    }

    /**
     * 혈압 측정 정보 저장
     *
     * @param resultInfo 혈압 측정 결과 정보
     * @return BaseResponse
     */
    @RequestMapping(value = "/results/bp", method = RequestMethod.POST)
    public BaseResponse saveBpResult(@Valid @RequestBody SaveBpResultInfo resultInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        String loginId = resultInfo.getLoginId();

        // 측정결과 저장정보 생성
        List<ResultSavedInformationData> resultSavedInformationDataList = new ArrayList<>();
        for (BpResult oriResult : resultInfo.getResults()) {
            // 01. 측정결과 내역
            Result result = new Result();
            BeanUtils.copyProperties(oriResult, result, "resultSeq", "admissionId", "itemId");
            result.setItemId(ItemId.BLOOD_PRESSURE.getItemId());

            // 02. 측정결과 상세 내역
            // 최저혈압
            ResultDetail resultDetailSbp = new ResultDetail();
            resultDetailSbp.setResultType(ResultType.MINIMUM_BLOOD_PRESSURE.getResultType());
            resultDetailSbp.setResult(oriResult.getResultSbp());

            // 최고혈압
            ResultDetail resultDetailDbp = new ResultDetail();
            resultDetailDbp.setResultType(ResultType.MAXIMUM_BLOOD_PRESSURE.getResultType());
            resultDetailDbp.setResult(oriResult.getResultDbp());

            List<ResultDetail> resultDetailList = new ArrayList<>();
            resultDetailList.add(resultDetailSbp);
            resultDetailList.add(resultDetailDbp);

            // 03. 측정결과 저장정보 구성
            ResultSavedInformationData data = new ResultSavedInformationData();
            data.setLoginId(loginId);
            data.setResult(result);
            data.setResultDetails(resultDetailList);

            // 측정결과 저장정보 추가
            resultSavedInformationDataList.add(data);
        }

        // 측정결과 저장
        resultService.saveResult(resultSavedInformationDataList);

        // 반환정보
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ApiResponseCode.SUCCESS.getCode());
        baseResponse.setMessage(messageSource.getMessage("message.success.saveResult", null, Locale.getDefault()));

        return baseResponse;
    }

    /**
     * 심박수 측정 정보 저장
     *
     * @param resultInfo 심박수 측정 결과 정보
     * @return BaseResponse
     */
    @RequestMapping(value = "/results/hr", method = RequestMethod.POST)
    public BaseResponse saveHrResult(@Valid @RequestBody SaveHrResultInfo resultInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        String loginId = resultInfo.getLoginId();

        // 측정결과 저장정보 생성
        List<ResultSavedInformationData> resultSavedInformationDataList = new ArrayList<>();
        for (HrResult oriResult : resultInfo.getResults()) {
            // 01. 측정결과 내역
            Result result = new Result();
            BeanUtils.copyProperties(oriResult, result, "resultSeq", "admissionId", "itemId");
            result.setItemId(ItemId.HEART_RATE.getItemId());

            // 02. 측정결과 상세 내역
            ResultDetail resultDetail = new ResultDetail();
            resultDetail.setResultType(ResultType.SINGLE_RESULT.getResultType());
            resultDetail.setResult(oriResult.getResult());

            List<ResultDetail> resultDetailList = new ArrayList<>();
            resultDetailList.add(resultDetail);

            // 03. 측정결과 저장정보 구성
            ResultSavedInformationData data = new ResultSavedInformationData();
            data.setLoginId(loginId);
            data.setResult(result);
            data.setResultDetails(resultDetailList);

            // 측정결과 저장정보 추가
            resultSavedInformationDataList.add(data);
        }

        // 측정결과 저장
        resultService.saveResult(resultSavedInformationDataList);

        // 반환정보
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ApiResponseCode.SUCCESS.getCode());
        baseResponse.setMessage(messageSource.getMessage("message.success.saveResult", null, Locale.getDefault()));

        return baseResponse;
    }

    /**
     * 산소포화도 측정 정보 저장
     *
     * @param resultInfo 산소포화도 측정 결과 정보
     * @return BaseResponse
     */
    @RequestMapping(value = "/results/spO2", method = RequestMethod.POST)
    public BaseResponse saveSpO2Result(@Valid @RequestBody SaveSpO2ResultInfo resultInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        String loginId = resultInfo.getLoginId();

        // 측정결과 저장정보 생성
        List<ResultSavedInformationData> resultSavedInformationDataList = new ArrayList<>();
        for (SpO2Result oriResult : resultInfo.getResults()) {
            // 01. 측정결과 내역
            Result result = new Result();
            BeanUtils.copyProperties(oriResult, result, "resultSeq", "admissionId", "itemId");
            result.setItemId(ItemId.OXYGEN_SATURATION.getItemId());

            // 02. 측정결과 상세 내역
            ResultDetail resultDetail = new ResultDetail();
            resultDetail.setResultType(ResultType.SINGLE_RESULT.getResultType());
            resultDetail.setResult(oriResult.getResult());

            List<ResultDetail> resultDetailList = new ArrayList<>();
            resultDetailList.add(resultDetail);

            // 03. 측정결과 저장정보 구성
            ResultSavedInformationData data = new ResultSavedInformationData();
            data.setLoginId(loginId);
            data.setResult(result);
            data.setResultDetails(resultDetailList);

            // 측정결과 저장정보 추가
            resultSavedInformationDataList.add(data);
        }

        // 측정결과 저장
        resultService.saveResult(resultSavedInformationDataList);

        // 반환정보
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ApiResponseCode.SUCCESS.getCode());
        baseResponse.setMessage(messageSource.getMessage("message.success.saveResult", null, Locale.getDefault()));

        return baseResponse;
    }

    /**
     * 걸음수 측정 정보 저장
     *
     * @param resultInfo 걸음수 측정 결과 정보
     * @return BaseResponse
     */
    @RequestMapping(value = "/results/stepCount", method = RequestMethod.POST)
    public BaseResponse saveStepCountResult(@Valid @RequestBody SaveStepCountResultInfo resultInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        String loginId = resultInfo.getLoginId();

        // 측정결과 저장정보 생성
        List<ResultSavedInformationData> resultSavedInformationDataList = new ArrayList<>();
        for (StepCountResult oriResult : resultInfo.getResults()) {
            // 01. 측정결과 내역
            Result result = new Result();
            BeanUtils.copyProperties(oriResult, result, "resultSeq", "admissionId", "itemId");
            result.setItemId(ItemId.STEP_COUNT.getItemId());

            // 02. 측정결과 상세 내역
            // 걸음수
            ResultDetail resultDetailStepCount = new ResultDetail();
            resultDetailStepCount.setResultType(ResultType.STEP_COUNT.getResultType());
            resultDetailStepCount.setResult(oriResult.getResultStepCount());

            // 거리
            ResultDetail resultDetailDistance = new ResultDetail();
            resultDetailDistance.setResultType(ResultType.DISTANCE.getResultType());
            resultDetailDistance.setResult(oriResult.getResultDistance());

            List<ResultDetail> resultDetailList = new ArrayList<>();
            resultDetailList.add(resultDetailStepCount);
            resultDetailList.add(resultDetailDistance);

            // 03. 측정결과 저장정보 구성
            ResultSavedInformationData data = new ResultSavedInformationData();
            data.setLoginId(loginId);
            data.setResult(result);
            data.setResultDetails(resultDetailList);

            // 측정결과 저장정보 추가
            resultSavedInformationDataList.add(data);
        }

        // 측정결과 저장
        resultService.saveResult(resultSavedInformationDataList);

        // 반환정보
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ApiResponseCode.SUCCESS.getCode());
        baseResponse.setMessage(messageSource.getMessage("message.success.saveResult", null, Locale.getDefault()));

        return baseResponse;
    }

    /**
     * 수면 정보 측정결과 저장
     *
     * @param resultInfo 수면 측정결과 저장 정보
     * @return BaseResponse
     */
    @RequestMapping(value = "/results/sleepTime", method = RequestMethod.POST)
    public BaseResponse saveSleepTimeResult(@Valid @RequestBody SaveSleepResultInfo resultInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        // 수면 정보 측정결과 저장
        resultService.saveResultSleep(resultInfo);

        // 반환정보
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ApiResponseCode.SUCCESS.getCode());
        baseResponse.setMessage(messageSource.getMessage("message.success.saveResult", null, Locale.getDefault()));

        return baseResponse;
    }
}
