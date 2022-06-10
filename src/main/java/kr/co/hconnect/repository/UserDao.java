package kr.co.hconnect.repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import kr.co.hconnect.vo.UserSearchVO;
import kr.co.hconnect.vo.UserTreatmentCenterVO;
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
        // 1. 사용자 정보 조회
        UserVO userVO = selectOne("kr.co.hconnect.sqlmapper.selectUserInfo", userId);

        // 2. 사용자 센터리스트 조회
        List<UserTreatmentCenterVO> userTreatmentCenterVOList = selectUserTreatmentCenterList(userId);
        if (userTreatmentCenterVOList != null && userTreatmentCenterVOList.size() > 0) {
            userVO.setUserTreatmentCenterVOList(userTreatmentCenterVOList);
        }

        return userVO;
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

    /**
     * 사용자 생성
     *
     * @param vo 사용자 저장 정보
     * @return 적용결과 count
     */
    public int insertUser(UserVO vo) {
        return insert("kr.co.hconnect.sqlmapper.insertUser", vo);
    }

    /**
     * 사용자 수정
     *
     * @param vo 사용자 수정 정보
     * @return 적용결과 count
     */
    public int updateUser(UserVO vo) {
        return update("kr.co.hconnect.sqlmapper.updateUser", vo);
    }

    /**
     * 사용자 삭제
     *
     * @param vo 사용자 삭제 정보
     * @return 적용결과 count
     */
    public int deleteUser(UserVO vo) {
        return update("kr.co.hconnect.sqlmapper.deleteUser", vo);
    }

    /**
     * 사용자별 센터 리스트 조회
     * @param userId 사용자ID
     * @return List&lt;UserTreatmentCenterVO&gt; 사용자별 센터 리스트
     */
    public List<UserTreatmentCenterVO> selectUserTreatmentCenterList(String userId) {
        return selectList("kr.co.hconnect.sqlmapper.selectUserTreatmentCenterList", userId);
    }

    /**
     * 사용자별 센터 정보
     *
     * @param vo 사용자별 센터 저장 정보
     * @return 적용결과 count
     */
    public int insertUserTreatmentCenter(UserTreatmentCenterVO vo) {
        return insert("kr.co.hconnect.sqlmapper.insertUserTreatmentCenter", vo);
    }

    /**
     * 사용자별 센터 삭제-사용자 기준 전체삭제
     * @param userId 사용자 ID
     * @return 적용결과 count
     */
    public int deleteUserTreatmentCenter(String userId) {
        return delete("kr.co.hconnect.sqlmapper.deleteUserTreatmentCenter", userId);
    }


}
