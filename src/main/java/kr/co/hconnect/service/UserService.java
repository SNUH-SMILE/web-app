package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.hconnect.common.CryptoUtils;
import kr.co.hconnect.exception.NotFoundUserInfoException;
import kr.co.hconnect.exception.NotMatchPatientPasswordException;
import kr.co.hconnect.jwt.TokenDetailInfo;
import kr.co.hconnect.repository.UserDao;
import kr.co.hconnect.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

/**
 * 사용자 Service
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class UserService extends EgovAbstractServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    /**
     * 사용자 Dao
     */
    private final UserDao userDao;
    /**
     * 사용자ID 채번 서비스
     */
    private final EgovIdGnrService userIdGnrService;
    /**
     * 토큰 발급 이력 Service
     */
    private final TokenHistoryService tokenHistoryService;
    /**
     * MessageSource
     */
    private final MessageSource messageSource;

    /**
     * 생성자
     * @param userDao 사용자 Dao
     * @param userIdGnrService 사용자ID 채번 서비스
     * @param tokenHistoryService 토큰 발급 이력 Service
     * @param messageSource MessageSource
     */
    @Autowired
    @Deprecated
    public UserService(UserDao userDao, @Qualifier("userIdGnrService") EgovIdGnrService userIdGnrService
            , TokenHistoryService tokenHistoryService, MessageSource messageSource) {
        this.userDao = userDao;
        this.userIdGnrService = userIdGnrService;
        this.tokenHistoryService = tokenHistoryService;
        this.messageSource = messageSource;
    }

    /**
     * 사용자 조회
     * @param userId 사용자ID
     * @return UserVO 사용자VO
     */
    public UserVO selectUserInfo(String userId) throws NotFoundUserInfoException {
        UserVO userVO = userDao.selectUserInfo(userId);

        if (userVO == null) {
            throw new NotFoundUserInfoException(messageSource.getMessage("message.notfound.userInfo"
                    ,null, Locale.getDefault()));
        }

        // 비밀번호 복화화
        userVO.setPassword(CryptoUtils.decrypt(userVO.getPassword()));

        return userVO;
    }

    /**
     * 로그인 정보 조회
     * @param userLoginInfoVO UserLoginInfo
     * @return 로그인VO
     */
    public UserVO selectLoginInfo(UserLoginInfoVO userLoginInfoVO)
             throws NotFoundUserInfoException, NotMatchPatientPasswordException {
        // 사용자 정보 조회
    	UserVO userVO = selectUserInfo(userLoginInfoVO.getLoginId());

        if (!userVO.getDelYn().equals("N")) {
            // 삭제된 사용자 확인
            throw new NotFoundUserInfoException(messageSource.getMessage("message.notfound.userInfo"
                    ,null, Locale.getDefault()));
        } else if (!userVO.getPassword().equals(userLoginInfoVO.getPassword())) {
            // 비밀번호 확인
            throw new NotMatchPatientPasswordException(messageSource.getMessage("message.mismatch.password"
                    , null, Locale.getDefault()));
        }

         return userVO;
    }

    /**
     * 사용자 목록 조회
     * @return 사용자VO 목록
     */
    public List<UserVO> selectUserList(UserSearchVO vo) {
        List<UserVO> userVOList = userDao.selectUserList(vo);

        // 비밀번호 복호화
        for (UserVO userVO : userVOList) {
            userVO.setPassword(CryptoUtils.decrypt(userVO.getPassword()));
        }

        return userVOList;
    }

    /**
     * 사용자 저장
     * @param userSaveVO 사용자 저장 정보
     */
    @Transactional(rollbackFor = Exception.class)
    public UserVO saveUser(UserSaveVO userSaveVO) throws FdlException, NotFoundUserInfoException {
        UserVO userVO = userSaveVO.getUserVO();

        String userId;
        String password = userVO.getPassword();

        // 비밀번호 암호화
        userVO.setPassword(CryptoUtils.encrypt(password));

        // 1.사용자 정보 저장
        if (StringUtils.isEmpty(userVO.getUserId())) {
            // 신규 사용자 생성
            userId = userIdGnrService.getNextStringId();
            userVO.setUserId(userId);

            userDao.insertUser(userVO);
        } else {
            // 기존 사용자 수정
            userId = userVO.getUserId();

            // 사용자 존재여부 확인
            if (userDao.selectUserInfo(userId) == null) {
                throw new NotFoundUserInfoException(messageSource.getMessage("message.notfound.userInfo"
                        ,null, Locale.getDefault()));
            }

            userDao.updateUser(userVO);
        }

        // 2.사용자별 센터 정보 생성
        userDao.deleteUserTreatmentCenter(userId);
        for (UserTreatmentCenterVO vo : userSaveVO.getUserVO().getUserTreatmentCenterVOList()) {
            vo.setUserId(userId);
            userDao.insertUserTreatmentCenter(vo);
        }

        return selectUserInfo(userId);
    }

    /**
     * 사용자 삭제
     *
     * @param vo 사용자 삭제 정보
     * @throws NotFoundUserInfoException 사용자가 존재하지 않을 경우 발생
     */
    public void deleteUser(UserVO vo) throws NotFoundUserInfoException {
        // 사용자 존재여부 확인
        UserVO activeUser = userDao.selectUserInfo(vo.getUserId());
        if (activeUser == null || !activeUser.getDelYn().equals("N")) {
            throw new NotFoundUserInfoException(messageSource.getMessage("message.notfound.userInfo"
                    ,null, Locale.getDefault()));
        }

        // 사용자 삭제
        userDao.deleteUser(vo);
    }



    /**
     * 로그인 정보 업데이트
     * @param vo UserVO
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUserLoginInfo(UserVO vo) {
        userDao.updateUserLoginInfo(vo);
    }

    /**
     * 로그아웃 정보 업데이트
     * @param tokenDetailInfo 토큰 상세 정보
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUserLogoutInfo(TokenDetailInfo tokenDetailInfo) throws NotFoundUserInfoException {
        // 1. 로그인 유지 여부 초기화
        UserVO vo = selectUserInfo(tokenDetailInfo.getId());
        vo.setRememberYn("N");
        updateUserLoginInfo(vo);

        // 2. 토큰 내역 삭제
        tokenHistoryService.deleteTokenHistory(tokenDetailInfo.getToken());
    }

}