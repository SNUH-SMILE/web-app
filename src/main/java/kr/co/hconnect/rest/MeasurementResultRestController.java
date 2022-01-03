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
            existResult.setMessage(messageSource.getMessage("message.found.notice", null, Locale.getDefault()));
            existResult.setExistYn("Y");
        }
        //알림 없을때
        else if (result == 0) {
            existResult.setMessage(messageSource.getMessage("message.notfound.notice", null, Locale.getDefault()));
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
     * 전체 측정 결과 저장
     *
     * @param saveTotalResultInfo 전체 측정결과 저장 정보
     * @return BaseResponse
     */
    @RequestMapping(value = "/results/total", method = RequestMethod.POST)
    public BaseResponse saveTotalResult(@Valid @RequestBody SaveTotalResultInfo saveTotalResultInfo
        , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        // 반환정보
        BaseResponse baseResponse = new BaseResponse();

        // 전체 측정결과 저장정보 생성
        ResultTotalSavedInformationData resultTotalSavedInformationData
            = getTotalResultSavedInfo(saveTotalResultInfo);

        // 저장 데이터 확인
        if (resultTotalSavedInformationData.isEmpty()) {
            baseResponse.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            baseResponse.setMessage(messageSource.getMessage("validation.null.result"
                , null, Locale.getDefault()));
            return baseResponse;
        }

        // 측정결과 저장
        resultService.saveTotalResult(resultTotalSavedInformationData);

        baseResponse.setCode(ApiResponseCode.SUCCESS.getCode());
        baseResponse.setMessage(messageSource.getMessage("message.success.saveResult"
            , null, Locale.getDefault()));

        return baseResponse;
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

        // 측정결과 저장정보 생성
        List<ResultSavedInformationData> resultSavedInformationDataList
            = convertResultSavedInfo(ItemId.BODY_TEMPERATURE, resultInfo);

        // 측정결과 저장
        resultService.saveResult(resultSavedInformationDataList);

        // 반환정보
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ApiResponseCode.SUCCESS.getCode());
        baseResponse.setMessage(messageSource.getMessage("message.success.saveResult"
            , null, Locale.getDefault()));

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

        // 측정결과 저장정보 생성
        List<ResultSavedInformationData> resultSavedInformationDataList
            = convertResultSavedInfo(ItemId.BLOOD_PRESSURE, resultInfo);

        // 측정결과 저장
        resultService.saveResult(resultSavedInformationDataList);

        // 반환정보
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ApiResponseCode.SUCCESS.getCode());
        baseResponse.setMessage(messageSource.getMessage("message.success.saveResult"
            , null, Locale.getDefault()));

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

        // 측정결과 저장정보 생성
        List<ResultSavedInformationData> resultSavedInformationDataList
            = convertResultSavedInfo(ItemId.HEART_RATE, resultInfo);

        // 측정결과 저장
        resultService.saveResult(resultSavedInformationDataList);

        // 반환정보
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ApiResponseCode.SUCCESS.getCode());
        baseResponse.setMessage(messageSource.getMessage("message.success.saveResult"
            , null, Locale.getDefault()));

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

        // 측정결과 저장정보 생성
        List<ResultSavedInformationData> resultSavedInformationDataList
            = convertResultSavedInfo(ItemId.OXYGEN_SATURATION, resultInfo);

        // 측정결과 저장
        resultService.saveResult(resultSavedInformationDataList);

        // 반환정보
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ApiResponseCode.SUCCESS.getCode());
        baseResponse.setMessage(messageSource.getMessage("message.success.saveResult"
            , null, Locale.getDefault()));

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

        // 측정결과 저장정보 생성
        List<ResultSavedInformationData> resultSavedInformationDataList
            = convertResultSavedInfo(ItemId.STEP_COUNT, resultInfo);

        // 측정결과 저장
        resultService.saveResult(resultSavedInformationDataList);

        // 반환정보
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ApiResponseCode.SUCCESS.getCode());
        baseResponse.setMessage(messageSource.getMessage("message.success.saveResult"
            , null, Locale.getDefault()));

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
        baseResponse.setMessage(messageSource.getMessage("message.success.saveResult"
            , null, Locale.getDefault()));

        return baseResponse;
    }

    /**
     * 전체 측정결과 저장 데이터 생성
     * @param saveTotalResultInfo 전체 측정결과 저장 정보
     * @return 전체 측정결과 저장 정보 데이터
     */
    private ResultTotalSavedInformationData getTotalResultSavedInfo(SaveTotalResultInfo saveTotalResultInfo) {
        ResultTotalSavedInformationData resultTotalSavedInformationData = new ResultTotalSavedInformationData();

        List<ResultSavedInformationData> savedInformationData = new ArrayList<>();

        String loginId = saveTotalResultInfo.getLoginId();

        // 체온
        if (saveTotalResultInfo.getBtResults() != null && !saveTotalResultInfo.getBtResults().isEmpty()) {
            SaveBtResultInfo saveBtResultInfo = new SaveBtResultInfo();
            saveBtResultInfo.setLoginId(loginId);
            saveBtResultInfo.setResults(saveTotalResultInfo.getBtResults());
            savedInformationData.addAll(convertResultSavedInfo(ItemId.BODY_TEMPERATURE, saveBtResultInfo));
        }
        // 혈압
        if (saveTotalResultInfo.getBpResults() != null && !saveTotalResultInfo.getBpResults().isEmpty()) {
            SaveBpResultInfo saveBpResultInfo = new SaveBpResultInfo();
            saveBpResultInfo.setLoginId(loginId);
            saveBpResultInfo.setResults(saveTotalResultInfo.getBpResults());
            savedInformationData.addAll(convertResultSavedInfo(ItemId.BLOOD_PRESSURE, saveBpResultInfo));
        }
        // 심박수
        if (saveTotalResultInfo.getHrResults() != null && !saveTotalResultInfo.getHrResults().isEmpty()) {
            SaveHrResultInfo saveHrResultInfo = new SaveHrResultInfo();
            saveHrResultInfo.setLoginId(loginId);
            saveHrResultInfo.setResults(saveTotalResultInfo.getHrResults());
            savedInformationData.addAll(convertResultSavedInfo(ItemId.HEART_RATE, saveHrResultInfo));
        }
        // 산소포화도
        if (saveTotalResultInfo.getSpO2Results() != null && !saveTotalResultInfo.getSpO2Results().isEmpty()) {
            SaveSpO2ResultInfo saveSpO2ResultInfo = new SaveSpO2ResultInfo();
            saveSpO2ResultInfo.setLoginId(loginId);
            saveSpO2ResultInfo.setResults(saveTotalResultInfo.getSpO2Results());
            savedInformationData.addAll(convertResultSavedInfo(ItemId.OXYGEN_SATURATION, saveSpO2ResultInfo));
        }
        // 걸음수
        if (saveTotalResultInfo.getStepCountResults() != null && !saveTotalResultInfo.getStepCountResults().isEmpty()) {
            SaveStepCountResultInfo saveStepCountResultInfo = new SaveStepCountResultInfo();
            saveStepCountResultInfo.setLoginId(loginId);
            saveStepCountResultInfo.setResults(saveTotalResultInfo.getStepCountResults());
            savedInformationData.addAll(convertResultSavedInfo(ItemId.STEP_COUNT, saveStepCountResultInfo));
        }

        // 1. VitalSign 측정결과 전체 저장 정보
        if (!savedInformationData.isEmpty()) {
            resultTotalSavedInformationData.setResultSavedInformationDataList(savedInformationData);
        }
        // 2. 수면 정보 측정결과 저장 정보
        if (saveTotalResultInfo.getSleepTimeResults() != null && !saveTotalResultInfo.getSleepTimeResults().isEmpty()) {
            SaveSleepResultInfo saveSleepResultInfo = new SaveSleepResultInfo();
            saveSleepResultInfo.setLoginId(loginId);
            saveSleepResultInfo.setResults(saveTotalResultInfo.getSleepTimeResults());
            resultTotalSavedInformationData.setSaveSleepResultInfo(saveSleepResultInfo);
        }

        return resultTotalSavedInformationData;
    }

    /**
     * Result 저장 데이터 정보 생성
     * @return List&lt;ResultSavedInformationData&gt;
     */
    private List<ResultSavedInformationData> convertResultSavedInfo(ItemId itemId, SaveResult saveResult) {
        // 측정결과 저장 데이터
        List<ResultSavedInformationData> resultSavedInformationDataList = new ArrayList<>();

        // 로그인ID
        String loginId = saveResult.getLoginId();

        for (ResultValue oriResult : saveResult.getResultList()) {
            // 측정결과 저장 정보 데이터
            ResultSavedInformationData data = new ResultSavedInformationData();

            // 01. 측정결과 내역
            Result result = new Result();
            BeanUtils.copyProperties(oriResult, result, "resultSeq", "admissionId", "itemId");
            result.setItemId(itemId.getItemId());

            // 02. 측정결과 상세 내역
            List<ResultDetail> resultDetailList = new ArrayList<>();
            switch (itemId) {
                case BODY_TEMPERATURE:
                case HEART_RATE:
                case OXYGEN_SATURATION:
                    ResultDetail resultDetail = new ResultDetail();
                    resultDetail.setResultType(ResultType.SINGLE_RESULT.getResultType());
                    resultDetail.setResult(oriResult.getResult());
                    resultDetailList.add(resultDetail);

                    break;
                case BLOOD_PRESSURE:
                    // 최고혈압
                    ResultDetail resultDetailSbp = new ResultDetail();
                    resultDetailSbp.setResultType(ResultType.MAXIMUM_BLOOD_PRESSURE.getResultType());
                    resultDetailSbp.setResult(oriResult.getResult());

                    // 최저혈압
                    ResultDetail resultDetailDbp = new ResultDetail();
                    resultDetailDbp.setResultType(ResultType.MINIMUM_BLOOD_PRESSURE.getResultType());
                    resultDetailDbp.setResult(oriResult.getResult2());

                    resultDetailList.add(resultDetailSbp);
                    resultDetailList.add(resultDetailDbp);

                    break;
                case STEP_COUNT:
                    // 걸음수
                    ResultDetail resultDetailStepCount = new ResultDetail();
                    resultDetailStepCount.setResultType(ResultType.STEP_COUNT.getResultType());
                    resultDetailStepCount.setResult(oriResult.getResult());

                    // 거리
                    ResultDetail resultDetailDistance = new ResultDetail();
                    resultDetailDistance.setResultType(ResultType.DISTANCE.getResultType());
                    resultDetailDistance.setResult(oriResult.getResult2());

                    resultDetailList.add(resultDetailStepCount);
                    resultDetailList.add(resultDetailDistance);

                    break;

            }

            // 03. 측정결과 저장정보 구성
            data.setLoginId(loginId);
            data.setResult(result);
            data.setResultDetails(resultDetailList);

            // 측정결과 저장정보 추가
            resultSavedInformationDataList.add(data);
        }

        return resultSavedInformationDataList;
    }
}
