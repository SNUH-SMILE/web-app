package kr.co.hconnect.service;


import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.hconnect.exception.ActiveAdmissionExistsException;
import kr.co.hconnect.repository.*;
import kr.co.hconnect.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class SupportService extends EgovAbstractServiceImpl{

    private  final SupportDao supportDao;

    @Autowired
    public SupportService(SupportDao supportDao
        , MessageSource messageSource
    ) {
        this.supportDao = supportDao;
    }

    public List<NoticeSupportListVO> selectNoticeList(NoticeSupportSearchVO vo) {
        return  supportDao.selectNoticeList(vo);
    }

}
