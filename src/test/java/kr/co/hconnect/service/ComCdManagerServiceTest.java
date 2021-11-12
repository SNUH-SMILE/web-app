package kr.co.hconnect.service;

import kr.co.hconnect.common.ComCd;
import kr.co.hconnect.vo.ComCdDetailListVO;
import kr.co.hconnect.vo.ComCdDetailVO;
import kr.co.hconnect.vo.ComCdVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/config/context-*.xml")
public class ComCdManagerServiceTest {

    @Autowired
    private ComCdManagerService comCdManagerServer;

    /**
     * 공통코드상세 목록 조회 서비스 테스트
     */
    @Test
    public void selectComCdList() {
        List<ComCdDetailListVO> comCdDetailListVOs = comCdManagerServer.selectComCdDetailList(ComCd.HOSPITAL_CD);
        assertThat(comCdDetailListVOs.size(), is(2));
    }


    /**
     * 공통코드 null > 수정가능 테스트
     */
    @Test
    public void updateComCd(){
        ComCdVO comCdVO = new ComCdVO();
        comCdVO.setComCd("9999");
        comCdVO.setRemark("공통코드 없이 수정 테스트 FROM JUNIT4");

        comCdManagerServer.updateCommonCode(comCdVO);
    }

    /**
     * 공통코드 null > 삭제 가능 테스트
     */
    @Test
    public void deleteComCd(){
        ComCdVO comCdVO = new ComCdVO();
        comCdVO.setComCd("9999");
        comCdVO.setRemark("공통코드 없이 삭제 테스트 FROM JUNIT4");

        comCdManagerServer.deleteCommonCode(comCdVO);
    }

    /**
     * 세부코드 null > 수정 가능 테스트
     */
    @Test
    public void updateComDetailCd(){
        ComCdDetailVO comCdDetailVO = new ComCdDetailVO();
        comCdDetailVO.setDetailCd("C003");
        comCdDetailVO.setComCd("CD040");
        comCdDetailVO.setDetailCdNm("세브란스#3");
        comCdDetailVO.setProperty1("Property1");

        comCdManagerServer.updateComCdDetail(comCdDetailVO);
    }

    /**
     * 세부코드 null > 삭제 가능 테스트
     */
    @Test
    public void deleteComDetailCd(){
        ComCdDetailVO comCdDetailVO = new ComCdDetailVO();
        comCdDetailVO.setDetailCd("C003");
        comCdDetailVO.setComCd("CD040");
        comCdDetailVO.setDetailCdNm("세브란스#3");
        comCdDetailVO.setProperty1("Property1");

        comCdManagerServer.deleteCommonDetail(comCdDetailVO);
    }

}