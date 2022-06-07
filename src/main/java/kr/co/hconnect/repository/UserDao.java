package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.UserSearchVO;
import kr.co.hconnect.vo.UserVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 사용자 Dao
 */
@Repository
public class UserDao extends EgovAbstractMapper {

    /**
     * 사용자 정보 조회
     *
     * @param userId 사용자ID
     * @return UserVO 사용자VO
     */
    public UserVO selectUserInfo(String userId) {
        return selectOne("kr.co.hconnect.sqlmapper.selectUserInfo", userId);
    }

    /**
     * 사용자 리스트 조회
     * @param vo 사용자 조회 정보 VO
     * @return List&lt;UserVO&gt; 사용자 리스트
     */
    public List<UserVO> selectUserList(UserSearchVO vo) {
        return selectList("kr.co.hconnect.sqlmapper.selectUserList", vo);
    }

    /**
     * 로그인 정보 업데이트
     * @param vo UserVO
     */
    public void updateUserLoginInfo(UserVO vo) {
        update("kr.co.hconnect.sqlmapper.updateUserLoginInfo", vo);
    }

}
