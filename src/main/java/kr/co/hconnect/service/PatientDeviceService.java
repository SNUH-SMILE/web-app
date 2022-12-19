package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.domain.PatientDevice;
import kr.co.hconnect.domain.PatientDeviceList;
import kr.co.hconnect.domain.PatientDeviceUseHistory;
import kr.co.hconnect.repository.PatientDeviceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 환자별 장비 Service
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class PatientDeviceService extends EgovAbstractServiceImpl {

    /**
     * 환자별 장비 Dao
     */
    private final PatientDeviceDao patientDeviceDao;

    private final MessageSource messageSource;

    /**
     * 생성자
     *
     * @param patientDeviceDao 환자별 장비 Dao
     * @param messageSource MessageSource
     */
    @Autowired
    public PatientDeviceService(PatientDeviceDao patientDeviceDao, MessageSource messageSource) {
        this.patientDeviceDao = patientDeviceDao;
        this.messageSource = messageSource;
    }

    /**
     * 환자별 장비 미사용 처리
     *
     * @param admissionId 격리/입소내역ID
     * @return affectedRow
     */
    public int updatePatientDeviceNotUseByAdmissionId(String admissionId) {
        return patientDeviceDao.updatePatientDeviceNotUseByAdmissionId(admissionId);
    }

    /**
     * 환자별 장비 리스트 추가
     *
     * @param patientDeviceList 환자별 장비 추가리스트
     * @return affectedRow
     */
    public List<PatientDeviceUseHistory> insertPatientDevice(String admissionId, List<PatientDevice> patientDeviceList) {
        // 장비 사용 이력
        List<PatientDeviceUseHistory> patientDeviceUseHistories = new ArrayList<>();

        // 장비 미사용 처리
        updatePatientDeviceNotUseByAdmissionId(admissionId);

        // 장비 추가 내역
        List<String> addDeviceList = new ArrayList<>();
        for (PatientDevice patientDevice : patientDeviceList) {
            if (addDeviceList.contains(patientDevice.getDeviceId())) {
                continue;
            }
            addDeviceList.add(patientDevice.getDeviceId());

            // 장비 사용이력 조회
            int deviceHistoryCount = patientDeviceDao.selectPatientDeviceUseHistory(patientDevice);

            PatientDeviceUseHistory patientDeviceUseHistory = new PatientDeviceUseHistory();
            patientDeviceUseHistory.setDeviceId(patientDevice.getDeviceId());
            patientDeviceUseHistory.setDeviceNm(patientDevice.getDeviceNm());
            patientDeviceUseHistory.setCode(deviceHistoryCount == 0 ? "00" : "01");
            patientDeviceUseHistory.setMessage(
                  deviceHistoryCount == 0
                ? messageSource.getMessage("message.patientDeviceHistory.case1", null, Locale.getDefault())
                : messageSource.getMessage("message.patientDeviceHistory.case2", null, Locale.getDefault()));

            patientDeviceUseHistories.add(patientDeviceUseHistory);

            // 신규 환자별 장비 추가
            patientDeviceDao.insertPatientDevice(patientDevice);
        }

        return patientDeviceUseHistories;
    }
    /**
     * 환자별 장비 리스트 조회
     *
     * @param patientDeviceList 환자별 장비 추가리스트
     * @return affectedRow
     */
    public List<PatientDeviceList> selectPatientDevice(PatientDevice vo) {
        return patientDeviceDao.selectPatientDeviceList(vo);
    }
}
