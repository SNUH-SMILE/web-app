package kr.co.hconnect.controller;

import kr.co.hconnect.common.ApiResponseCode;
import kr.co.hconnect.exception.NotFoundUserInfoException;
import kr.co.hconnect.service.TreatmentCenterService;
import kr.co.hconnect.service.UserService;
import kr.co.hconnect.vo.ResponseVO;
import kr.co.hconnect.vo.UserSearchVO;
import kr.co.hconnect.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 생활치료센터 Service
     */
    private final TreatmentCenterService treatmentCenterService;

    /**
     * 생성자
     * @param userService 사용자 Service
     * @param treatmentCenterService 생활치료센터 Service
     */
    @Autowired
    public UserController(UserService userService, TreatmentCenterService treatmentCenterService) {
        this.userService = userService;
        this.treatmentCenterService = treatmentCenterService;
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

}
