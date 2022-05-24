package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.hconnect.common.CryptoUtils;
import kr.co.hconnect.domain.UserLoginInfo;
import kr.co.hconnect.exception.NotFoundUserInfoException;
import kr.co.hconnect.exception.NotMatchPatientPasswordException;
import kr.co.hconnect.repository.UserDao;
import kr.co.hconnect.vo.UserVO;
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
 * 사용자 서비스
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
     * MessageSource
     */
    private final MessageSource messageSource;

    /**
     * 생성자
     * @param userDao 사용자 Dao
     * @param userIdGnrService 사용자ID 채번 서비스
     * @param messageSource MessageSource
     */
    @Autowired
    public UserService(UserDao userDao, @Qualifier("userIdGnrService") EgovIdGnrService userIdGnrService, MessageSource messageSource) {
        this.userDao = userDao;
        this.userIdGnrService = userIdGnrService;
        this.messageSource = messageSource;
    }

    /**
     * 사용자 조회
     * @param userId 사용자ID
     * @return UserVO 사용자VO
     */
    public UserVO selectUserInfo(String userId) {
        UserVO userVO = userDao.selectUserInfo(userId);

        // 비밀번호 복화화
        if (userVO != null)
            userVO.setPassword(CryptoUtils.decrypt(userVO.getPassword()));
        return userVO;
    }

    /**
     * 로그인 정보 조회
     * @param userLoginInfo UserLoginInfo
     * @return 로그인VO
     */
    //public LoginVO selectLoginInfo(String userId, String password) {
     public UserVO selectLoginInfo(UserLoginInfo userLoginInfo)
             throws NotFoundUserInfoException, NotMatchPatientPasswordException {
        // 사용자 정보 조회
    	UserVO userVO = selectUserInfo(userLoginInfo.getLoginId());

         if (userVO == null) {
             throw new NotFoundUserInfoException(messageSource.getMessage("message.notfound.userInfo"
                     ,null, Locale.getDefault()));
         } else if (!userVO.getPassword().equals(userLoginInfo.getPassword())) {
             throw new NotMatchPatientPasswordException(messageSource.getMessage("message.mismatch.password"
                     , null, Locale.getDefault()));
         }

         return userVO;
    }

    /**
     * 사용자 목록 조회
     * @return 사용자VO 목록
     */
    public List<UserVO> selectUserList() {
        List<UserVO> userVOs = userDao.selectUserList();

        // 비밀번호 복호화
        for (UserVO userVO : userVOs) {
            userVO.setPassword(CryptoUtils.decrypt(userVO.getPassword()));
        }

        return userVOs;
    }

    /**
     * 사용자 입력
     * @param vo 사용자VO
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertUser(UserVO vo) {
        try {
            // 사용자ID 채번
            vo.setUserId(userIdGnrService.getNextStringId());
        } catch (FdlException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("사용자ID 채번중 오류가 발생 했습니다.", e);
        }

        // 비밀번호 암호화
        vo.setPassword(CryptoUtils.encrypt(vo.getPassword()));

        // 사용자 입력
        userDao.insertUser(vo);
    }

    /**
     * 트랜잭션 테스트
     * @param userVOs 사용자VO 목록
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertUser(List<UserVO> userVOs) {
        for (int index = 0; index < userVOs.size(); index++) {
            if (index == 7)
                throw new RuntimeException();

            this.insertUser(userVOs.get(index));
        }
    }

    /**
     * 사용자 수정
     * @param vo 사용자VO
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserVO vo) {
        // 비밀번호 암호화
        vo.setPassword(CryptoUtils.encrypt(vo.getPassword()));
        
        // 사용자 수정
        userDao.updateUser(vo);
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
     * 사용자 삭제
     * @param userId 사용자Id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String userId) {
        userDao.deleteUser(userId);
    }

}
