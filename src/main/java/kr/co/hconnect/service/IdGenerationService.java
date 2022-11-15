package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class IdGenerationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdGenerationService.class);

    private final EgovIdGnrService centerIdGnrService;          // 센터ID 채번 서비스
    private final EgovIdGnrService userIdGnrService;            // 사용자ID 채번 서비스
    private final EgovIdGnrService patientIdGnrService;         // 환자ID 채번 서비스
    private final EgovIdGnrService admissionIdGnrService;       // 입소내역ID 채번 서비스
    private final EgovIdGnrService itemIdGnrService;            // 측정항목ID 채번 서비스
    private final EgovIdGnrService comCdGnrService;              // 공통코드 채번 서비스
    private final EgovIdGnrService interviewIdGnrService;        // 문진내역Id 채번 서비스
    private final EgovIdGnrService interviewDetailIdGnrService;  // 문진내역세부Id 채번 서비스
    private final EgovIdGnrService drugSeqGnrService;             // 복약 채번 서비스
    private final EgovIdGnrService drugAlarmSeqGnrService;        // 복약아랆 채번 서비스
    private final EgovIdGnrService drugDoseSeqGnrService;         // 복약 여부드 채번 서비스


    /**
     * 생성자를 통해 각 채번 서비스를 DI (using @Qualifier)
     */
    public IdGenerationService(@Qualifier("centerIdGnrService") EgovIdGnrService centerIdGnrService
        , @Qualifier("userIdGnrService") EgovIdGnrService userIdGnrService
        , @Qualifier("patientIdGnrService") EgovIdGnrService patientIdGnrService
        , @Qualifier("admissionIdGnrService") EgovIdGnrService admissionIdGnrService
        , @Qualifier("itemIdGnrService") EgovIdGnrService itemIdGnrService
        , @Qualifier("comCdGnrService") EgovIdGnrService comCdGnrService
        , @Qualifier("interviewIdGnrService") EgovIdGnrService interviewIdGnrService
        , @Qualifier("interviewDetailIdGnrService") EgovIdGnrService interviewDetailIdGnrService
        , @Qualifier("drugSeqGnrService") EgovIdGnrService drugSeqGnrService
        , @Qualifier("drugAlarmSeqGnrService") EgovIdGnrService drugAlarmSeqGnrService
        , @Qualifier("drugDoseSeqGnrService") EgovIdGnrService drugDoseSeqGnrService) {

        this.centerIdGnrService = centerIdGnrService;
        this.userIdGnrService = userIdGnrService;
        this.patientIdGnrService = patientIdGnrService;
        this.admissionIdGnrService = admissionIdGnrService;
        this.itemIdGnrService = itemIdGnrService;
        this.comCdGnrService = comCdGnrService;
        this.interviewIdGnrService = interviewIdGnrService;
        this.interviewDetailIdGnrService = interviewDetailIdGnrService;
        this.drugSeqGnrService = drugSeqGnrService;
        this.drugAlarmSeqGnrService = drugAlarmSeqGnrService;
        this.drugDoseSeqGnrService = drugDoseSeqGnrService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void doIdGeneration() throws FdlException {
        // 각 채번 서비스의 getNextStringId() 메서드를 호출해 채번
        String centerId = centerIdGnrService.getNextStringId();
        String userId = userIdGnrService.getNextStringId();
        String patientId = patientIdGnrService.getNextStringId();
        String admissionId = admissionIdGnrService.getNextStringId();
        String itemId = itemIdGnrService.getNextStringId();
        String comCd = comCdGnrService.getNextStringId();
        String drugSeq = drugSeqGnrService.getNextStringId();
        String drugAlarmSeq = drugAlarmSeqGnrService.getNextStringId();
        String drugDoseSeq = drugDoseSeqGnrService.getNextStringId();
        Integer interviewId = interviewIdGnrService.getNextIntegerId();
        Integer interviewDetailId = interviewDetailIdGnrService.getNextIntegerId();

        LOGGER.info("CenterId {}", centerId);
        LOGGER.info("UserId {}", userId);
        LOGGER.info("PatientId {}", patientId);
        LOGGER.info("AdmissionId {}", admissionId);
        LOGGER.info("ItemId {}", itemId);
        LOGGER.info("ComCd {}", comCd);
        LOGGER.info("interviewId {}", interviewId);
        LOGGER.info("interviewDetailId {}", interviewDetailId);
        LOGGER.info("drugSeq {}", drugSeq);
        LOGGER.info("drugAlarmSeq {}", drugAlarmSeq);
        LOGGER.info("drugDoseSeq {}", drugDoseSeq);
    }

}
