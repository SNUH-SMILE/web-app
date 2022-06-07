package kr.co.hconnect.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/config/context-*.xml")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void insertUser() {
//        UserVO userVO = new UserVO();
//        userVO.setPassword("1111");
//        userVO.setUserNm("개발자");
//        userVO.setCenterId("C999");
//
//        userService.insertUser(userVO);
    }

    /**
     * 트랜잭션 테스트
     */
    @Test
    public void insertUserList() {
//        List<UserVO> userVOs = new ArrayList<>();
//        for (int index = 1; index <= 10; index++) {
//            UserVO userVO = new UserVO();
//            userVO.setPassword(String.valueOf(index));
//            userVO.setUserNm("의료진" + index);
//            userVO.setCenterId("C999");
//            userVO.setRemark("리마크" + index);
//            userVOs.add(userVO);
//        }
//
//        userService.insertUser(userVOs);

/*
        //# @Transactional(rollbackFor = Exception.class) 선언한 경우
        2021-09-08 19:48:59,772 DEBUG [jdbc.audit] 2. Connection.rollback() returned   org.apache.commons.dbcp.DelegatingConnection.rollback(DelegatingConnection.java:368)
        2021-09-08 19:48:59,772 DEBUG [jdbc.audit] 2. Connection.isReadOnly() returned false  org.apache.commons.dbcp.DelegatingConnection.isReadOnly(DelegatingConnection.java:362)
        2021-09-08 19:48:59,772 DEBUG [jdbc.audit] 2. Connection.isClosed() returned false  org.apache.commons.dbcp.DelegatingConnection.isClosed(DelegatingConnection.java:386)
        2021-09-08 19:48:59,772 DEBUG [jdbc.audit] 2. Connection.getAutoCommit() returned false  org.apache.commons.dbcp.DelegatingConnection.getAutoCommit(DelegatingConnection.java:337)
        2021-09-08 19:48:59,772 DEBUG [jdbc.audit] 2. Connection.isReadOnly() returned false  org.apache.commons.dbcp.DelegatingConnection.isReadOnly(DelegatingConnection.java:362)
        2021-09-08 19:48:59,772 DEBUG [jdbc.audit] 2. Connection.rollback() returned   org.apache.commons.dbcp.DelegatingConnection.rollback(DelegatingConnection.java:368)
        2021-09-08 19:48:59,772 DEBUG [jdbc.audit] 2. Connection.clearWarnings() returned   org.apache.commons.dbcp.DelegatingConnection.clearWarnings(DelegatingConnection.java:331)
        2021-09-08 19:48:59,772 DEBUG [jdbc.audit] 2. Connection.getAutoCommit() returned false  org.apache.commons.dbcp.DelegatingConnection.getAutoCommit(DelegatingConnection.java:337)
        2021-09-08 19:48:59,788 DEBUG [jdbc.audit] 2. Connection.setAutoCommit(true) returned   org.apache.commons.dbcp.DelegatingConnection.setAutoCommit(DelegatingConnection.java:371)

        //# @Transactional(rollbackFor = Exception.class) 선언하지 않은 경우
        2021-09-08 19:51:23,995 DEBUG [jdbc.audit] 2. Connection.rollback() returned   org.apache.commons.dbcp.DelegatingConnection.rollback(DelegatingConnection.java:368)
        2021-09-08 19:51:23,995 DEBUG [jdbc.audit] 2. Connection.isReadOnly() returned true  org.apache.commons.dbcp.DelegatingConnection.isReadOnly(DelegatingConnection.java:362)
        2021-09-08 19:51:23,995 DEBUG [jdbc.audit] 2. Connection.setReadOnly(false) returned   org.apache.commons.dbcp.DelegatingConnection.setReadOnly(DelegatingConnection.java:377)
        2021-09-08 19:51:23,995 DEBUG [jdbc.audit] 2. Connection.isClosed() returned false  org.apache.commons.dbcp.DelegatingConnection.isClosed(DelegatingConnection.java:386)
        2021-09-08 19:51:23,995 DEBUG [jdbc.audit] 2. Connection.getAutoCommit() returned false  org.apache.commons.dbcp.DelegatingConnection.getAutoCommit(DelegatingConnection.java:337)
        2021-09-08 19:51:23,995 DEBUG [jdbc.audit] 2. Connection.isReadOnly() returned false  org.apache.commons.dbcp.DelegatingConnection.isReadOnly(DelegatingConnection.java:362)
        2021-09-08 19:51:23,995 DEBUG [jdbc.audit] 2. Connection.rollback() returned   org.apache.commons.dbcp.DelegatingConnection.rollback(DelegatingConnection.java:368)
        2021-09-08 19:51:23,995 DEBUG [jdbc.audit] 2. Connection.clearWarnings() returned   org.apache.commons.dbcp.DelegatingConnection.clearWarnings(DelegatingConnection.java:331)
        2021-09-08 19:51:23,995 DEBUG [jdbc.audit] 2. Connection.getAutoCommit() returned false  org.apache.commons.dbcp.DelegatingConnection.getAutoCommit(DelegatingConnection.java:337)
        2021-09-08 19:51:24,010 DEBUG [jdbc.audit] 2. Connection.setAutoCommit(true) returned   org.apache.commons.dbcp.DelegatingConnection.setAutoCommit(DelegatingConnection.java:371)
*/
    }

}