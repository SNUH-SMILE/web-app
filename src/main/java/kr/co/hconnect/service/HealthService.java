package kr.co.hconnect.service;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.hconnect.exception.ActiveAdmissionExistsException;
import kr.co.hconnect.repository.PatientDeviceDao;
import kr.co.hconnect.repository.PatientEquipDao;
import kr.co.hconnect.repository.TreatmentCenterDao;
import kr.co.hconnect.vo.*;
import kr.co.hconnect.repository.HealthDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class HealthService extends EgovAbstractServiceImpl{

    private  final HealthDao healthDao;


    @Autowired
    public HealthService(HealthDao healthDao) {
        this.healthDao = healthDao;
    }

    @Transactional(rollbackFor = Exception.class)
    public void inserthelth(HealthVO vo) throws FdlException {
        healthDao.inserthelth(vo);
    }

    public HealthResponseVO selectHelth(HealthSearchVO vo)  {
        HealthResponseVO repEntity= new HealthResponseVO();
        repEntity = healthDao.selectHelth(vo);
        return repEntity;
    }

}
