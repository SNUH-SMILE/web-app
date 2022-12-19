package kr.co.hconnect.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.hconnect.exception.ActiveAdmissionExistsException;
import kr.co.hconnect.repository.AiInferenceDao;
import kr.co.hconnect.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;

import java.io.*;
import java.util.List;
import java.util.Locale;

@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class AiInferenceService extends EgovAbstractServiceImpl{
    // private  final AiInferenceDao dao;
    //
    // public AiInferenceService(AiInferenceDao dao) {
    //     this.dao = dao;
    // }
    @Transactional(rollbackFor = Exception.class)
    public String insertInference(AiInferenceVO vo) throws FdlException, IOException {

        // String path = System.getProperty("user.dir"); // 현재폴더의 디렉토리 가지고 오기.
        // File file = new File(path + "\\Data.csv"); // 현재 폴더의 디렉토리에 파일 저장해놓고 경로 지정.

        File file = new File("e:\\temp\\result_score.csv"); // 현재 폴더의 디렉토리에 파일 저장해놓고 경로 지정.

        BufferedReader br = new BufferedReader(new BufferedReader(new FileReader(file))); //버퍼리더 만들기.

        String line = "";

        while ((line = br.readLine()) != null) { //한 라인씩 읽어오기.
            System.out.println(line);
        }
        return "1";
    }
}
