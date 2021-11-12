package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.UserVO;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * 유저
 */

@Repository
public class UserDao extends EgovAbstractMapper {

    /**
     *유저정보 조회
     * @param userId 사용자ID
     * @return UserVO 사용자VO
     */
    public UserVO selectUserInfo(String userId) {
        return selectOne("kr.co.hconnect.sqlmapper.selectUserInfo", userId);
    }

    /**
     *유저정보 리스트 조회
     * @return 유저 목록
     */
    public List<UserVO> selectUserList() {
        return selectList("kr.co.hconnect.sqlmapper.selectUserInfoList");
    }

    /**
     *유저정보 저장
     * @param vo 유저VO
     */
    public void insertUser(UserVO vo) {
        insert("kr.co.hconnect.sqlmapper.insertUser",vo);
    }

    /**
     * 유저정보 수정
     * @param vo 유저VO
     */
    public void updateUser(UserVO vo) {
        update("kr.co.hconnect.sqlmapper.updateUser",vo);
    }

    /**
     *유저정보 삭제
     * @param userId 유저Id
     */
    public void deleteUser(String userId) {
        delete("kr.co.hconnect.sqlmapper.deleteUser",userId);
    }
}
