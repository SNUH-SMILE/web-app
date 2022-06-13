package kr.co.hconnect.controller;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.common.VoValidationGroups;
import kr.co.hconnect.exception.InvalidRequestArgumentException;
import kr.co.hconnect.exception.NotFoundUserInfoException;
import kr.co.hconnect.jwt.TokenDetailInfo;
import kr.co.hconnect.service.UserService;
import kr.co.hconnect.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사용자 관리 Controller
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    /**
     * 사용자 Service
     */
    private final UserService userService;
    /**
     * MessageSource
     */
    private final MessageSource messageSource;

    /**
     * 생성자
     * @param userService 사용자 Service
     * @param messageSource MessageSource
     */
    @Autowired
    public UserController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    /**
     * 사용자 정보 조회-token 값 이용
     * @return ResponseVO&lt;UserVO&gt; 사용자 정보
     */
    @RequestMapping(value = "/info/token", method = RequestMethod.GET)
    public ResponseVO<UserVO> selectUserInfoByToken(@RequestAttribute TokenDetailInfo tokenDetailInfo) {
        return selectUserInfo(tokenDetailInfo.getId());
    }

    /**
     * 사용자 정보 조회
     * @param userId 사용자ID
     * @return ResponseVO&lt;UserVO&gt; 사용자 정보
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseVO<UserVO> selectUserInfo(@RequestParam String userId) {
        ResponseVO<UserVO> responseVO = new ResponseVO<>();

        try {
            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("사용자 정보 조회 완료");
            responseVO.setResult(userService.selectUserInfo(userId));
        } catch (NotFoundUserInfoException e) {
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }

        return responseVO;
    }

    /**
     * 사용자 리스트 검색
     *
     * @param userSearchVO 사용자 검색 정보
     * @return ResponseVO&lt;List&lt;UserVO&gt;&gt; 사용자 리스트
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseVO<List<UserVO>> selectUserList(@RequestBody UserSearchVO userSearchVO) {

        ResponseVO<List<UserVO>> responseVO = new ResponseVO<>();
        responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
        responseVO.setMessage("사용자 리스트 검색 완료");
        responseVO.setResult(userService.selectUserList(userSearchVO));

        return responseVO;
    }

    /**
     * 사용자 저장
     * @param userSaveVO 사용자 저장정보
     * @return ResponseVO&lt;UserSaveCompletedVO&gt; 사용자 저장 완료 정보
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVO<UserSaveCompletedVO> saveUser(@Validated(VoValidationGroups.add.class) @RequestBody UserSaveVO userSaveVO
            , BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        // 등록자,수정자 정보 바인딩
        userSaveVO.getUserVO().setRegId(tokenDetailInfo.getId());
        userSaveVO.getUserVO().setUpdId(tokenDetailInfo.getId());
        for (UserTreatmentCenterVO vo : userSaveVO.getUserVO().getUserTreatmentCenterVOList()) {
            vo.setRegId(tokenDetailInfo.getId());
        }

        ResponseVO<UserSaveCompletedVO> responseVO = new ResponseVO<>();

        // 사용자 저장
        try {
            UserVO userVO = userService.saveUser(userSaveVO);

            // 사용자 저장 완료정보 구성
            UserSaveCompletedVO userSaveCompletedVO = new UserSaveCompletedVO();
            userSaveCompletedVO.setUserVO(userVO);
            userSaveCompletedVO.setUserVOList(userService.selectUserList(userSaveVO.getUserSearchVO()));

            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("사용자 저장 완료");
            responseVO.setResult(userSaveCompletedVO);
        } catch (FdlException e) {
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        } catch (NotFoundUserInfoException e) {
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }

        return responseVO;
    }

    /**
     * 사용자 삭제
     * 
     * @param userSaveVO 사용자 삭제 정보
     * @return 사용자 삭제 완료 정보
     */
    @RequestMapping(value = "/save", method = RequestMethod.DELETE)
    public ResponseVO<List<UserVO>> deleteUser(@Validated(VoValidationGroups.delete.class) @RequestBody UserSaveVO userSaveVO
            , BindingResult bindingResult, @RequestAttribute TokenDetailInfo tokenDetailInfo) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestArgumentException(bindingResult);
        }

        userSaveVO.getUserVO().setUpdId(tokenDetailInfo.getId());

        ResponseVO<List<UserVO>> responseVO = new ResponseVO<>();

        try {
            userService.deleteUser(userSaveVO.getUserVO());

            responseVO.setCode(ApiResponseCode.SUCCESS.getCode());
            responseVO.setMessage("사용자 삭제 성공");
            responseVO.setResult(userService.selectUserList(userSaveVO.getUserSearchVO()));
        } catch (NotFoundUserInfoException e) {
            responseVO.setCode(ApiResponseCode.CODE_INVALID_REQUEST_PARAMETER.getCode());
            responseVO.setMessage(e.getMessage());
        }

        return responseVO;
    }

}
