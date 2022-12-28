package kr.co.hconnect.controller;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.VoValidationGroups;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.jwt.TokenDetailInfo;
import kr.co.hconnect.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import kr.co.hconnect.service.DrugService;
import kr.co.hconnect.service.AdmissionService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/drug")
public class DrugController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DrugController.class);

    private final MessageSource messageSource;

    private final DrugService drugService;
    /**
     * 격리/입소내역 관리 Service
     */
    private final AdmissionService admissionService;

    @Autowired
    public DrugController(MessageSource messageSource
                          ,AdmissionService admissionService
                          ,DrugService drugService) {
        this.messageSource = messageSource;
        this.admissionService = admissionService;
        this.drugService = drugService;
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
     * 복약알림저장
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/setNotice", method = RequestMethod.POST)
    public ResponseBaseVO<DrugAlarmSaveVO> insertDrugAlarm(@Validated(VoValidationGroups.add.class) @RequestBody DrugAlarmSaveVO vo
        , BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        ResponseBaseVO<DrugAlarmSaveVO> responseVO = new ResponseBaseVO<>();

        String admisstionId = getAdmissionId(vo.getLoginId());

        System.out.println("admisstionId " + admisstionId);
        String loginId = vo.getLoginId();

        // 저장정보 구성
        DrugAlarmSaveVO drugAlarmSaveVO = new DrugAlarmSaveVO();
        BeanUtils.copyProperties(vo, drugAlarmSaveVO);
        drugAlarmSaveVO.setRegId(loginId);

        try{
            vo.setAdmissionId(admisstionId);
            String drugSeq = drugService.insertDrugAlarm(vo);
            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("저장 완료");

        }catch (Exception  e){
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }
        return responseVO;
    }

    /**
     * 복약결과 알림저장 저장
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/setTakeResult", method = RequestMethod.POST)
    public ResponseBaseVO<DrugAlarmSaveVO> insertDrugDose(@Validated(VoValidationGroups.add.class) @RequestBody DrugDoseSaveVO vo
        , BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        ResponseBaseVO<DrugAlarmSaveVO> responseVO = new ResponseBaseVO<>();

        String admisstionId = getAdmissionId(vo.getLoginId());
        String loginId = vo.getLoginId();

        // 저장정보 구성
        DrugDoseSaveVO drugDoseVO = new DrugDoseSaveVO();
        BeanUtils.copyProperties(vo, drugDoseVO);

        try{
            vo.setAdmissionId(admisstionId);
            String drugSeq = drugService.insertDrugDose(vo);
            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("저장 완료");

        }catch (Exception  e){
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }
        return responseVO;
    }

    /**
     * 복약결과 알림 없이  저장
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/setEtcResult", method = RequestMethod.POST)
    public ResponseBaseVO<DrugDoseVO> insertNoAlarmDrugDose(@Validated(VoValidationGroups.add.class) @RequestBody DrugDoseSaveVO vo
        , BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        ResponseBaseVO<DrugDoseVO> responseVO = new ResponseBaseVO<>();

        String admisstionId = getAdmissionId(vo.getLoginId());
        String loginId = vo.getLoginId();

        // 저장정보 구성
        DrugDoseSaveVO drugDoseVO = new DrugDoseSaveVO();
        BeanUtils.copyProperties(vo, drugDoseVO);

        try{
            vo.setAdmissionId(admisstionId);
            vo.setTakeResult("1");                   //복약여부

            String drugSeq = drugService.insertNoAlarmDrugDose(vo);
            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("저장 완료");

        }catch (Exception  e){
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }
        return responseVO;
    }

    /**
     * 복약정보 타임리스트
     * @param vo 타임라인 조회조회조건 VO
     * @return
     */
    @RequestMapping(value = "/timeList", method = RequestMethod.POST)
    public ResponseBaseVO<DrugTimeListVO> selectTimeList(@Valid @RequestBody DrugSearchVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }
        ResponseBaseVO<DrugTimeListVO> responseVO = new ResponseBaseVO<>();
        String admisstionId = getAdmissionId(vo.getLoginId());
        String loginId = vo.getLoginId();
        vo.setAdmissionId(admisstionId);
        try{
            DrugTimeListVO dt = drugService.selectDrugTimeList(vo);
            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("조회성공");
            responseVO.setResult(dt);
        } catch(RuntimeException e){
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }

        return responseVO;
    }
    /**
     * 복약정보 알림리스트
     * @param vo
     * @return
     */
    @RequestMapping(value = "/noticeList", method = RequestMethod.POST)
    public ResponseBaseVO<DrugNoticeVO> selectNoticeList(@Valid @RequestBody DrugSearchVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        ResponseBaseVO<DrugNoticeVO> responseVO = new ResponseBaseVO<>();

        String admisstionId = getAdmissionId(vo.getLoginId());
        String loginId = vo.getLoginId();
        vo.setAdmissionId(admisstionId);

        try{
            DrugNoticeVO dt = drugService.selectAlarmList(vo);

            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("조회성공");
            responseVO.setResult(dt);
        } catch(RuntimeException e){
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }

        return responseVO;
    }
    /**
     * 환자상세의 투약내역 조회
     * @param admissionId
     * @return
     */
    @RequestMapping(value = "/listForDetail", method = RequestMethod.GET)
    public ResponseBaseVO<List<DrugDetailVO>> selectDrugListForDetail(@RequestParam String admissionId) {
        ResponseBaseVO<List<DrugDetailVO>> responseVO = new ResponseBaseVO<>();

        List<DrugDetailVO> drugDetailVOS =  drugService.selectDrugListForDetail(admissionId);
        responseVO.setResult(drugDetailVOS);
        responseVO.setMessage("입소내역 조회 성공");

        return responseVO;

    }
}
