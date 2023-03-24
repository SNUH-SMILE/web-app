package kr.co.hconnect.service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.hconnect.common.CryptoUtils;
import kr.co.hconnect.domain.*;
import kr.co.hconnect.exception.DuplicatePatientInfoException;
import kr.co.hconnect.exception.DuplicatePatientLoginIdException;
import kr.co.hconnect.exception.NotFoundPatientInfoException;
import kr.co.hconnect.exception.NotMatchPatientPasswordException;
import kr.co.hconnect.repository.PatientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;

/**
 * 환자관리 Service
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class PatientService extends EgovAbstractServiceImpl {

    /**
     * 환자정보 관리 Dao
     */
    private final PatientDao patientDao;

    private final MessageSource messageSource;

    @Autowired
    public PatientService(PatientDao patientDao, MessageSource messageSource) {
        this.patientDao = patientDao;
        this.messageSource = messageSource;
    }

    /**
     * 환자정보 조회-로그인ID 기준
     *
     * @param loginId 로그인ID
     * @return Patient
     */
    public Patient selectPatientByLoginId(String loginId) {
        return patientDao.selectPatientByLoginId(loginId);
    }

    /**
     * 환자정보 조회-로그인
     * 
     * @param loginInfo 로그인 정보
     * @return 환자정보
     * @throws NotFoundPatientInfoException 환자정보가 없을 경우 exception
     * @throws NotMatchPatientPasswordException 비밀번호가 일치하지 않을 경우 exception
     */
    public Patient selectPatientByLoginInfo(LoginInfo loginInfo)
            throws NotFoundPatientInfoException, NotMatchPatientPasswordException {
        // 환자정보 조회
        Patient patient = selectPatientByLoginId(loginInfo.getLoginId());

        // 전달받은 비밀번호 암호화
        String encryptPassword = CryptoUtils.encrypt(loginInfo.getPassword());

        if (patient == null) {
            throw new NotFoundPatientInfoException(messageSource.getMessage("message.notfound.patientInfo"
                ,null, Locale.getDefault()));
        } else if (!patient.getPassword().equals(encryptPassword)) {
            throw new NotMatchPatientPasswordException(messageSource.getMessage("message.mismatch.password"
                , null, Locale.getDefault()));
        }

        return patient;
    }

    /**
     * 환자 로그인ID 중복 체크
     *
     * @param loginId 로그인ID
     * @return boolean 중복여부
     */
    public boolean checkDuplicateLoginId(String loginId) {
        boolean isDuplicate = false;
        // 환자정보 조회
        Patient patient = patientDao.selectPatientByLoginId(loginId);

        if (patient != null) {
            isDuplicate = true;
        }

        return isDuplicate;
    }

    /**
     * 환자정보 저장 - App을 통한 환자정보 추가
     *
     * @param patient 환정보
     * @return Patient 환자정보
     */
    public Patient savePatientInfo(Patient patient)
        throws NotFoundPatientInfoException, DuplicatePatientLoginIdException, DuplicatePatientInfoException {



        // 환자정보 신규생성
        if (patient.getFlag().equals("A")) {
            // 환자정보 존재여부 확인 (성명, 생년월일, 성별, 휴대폰)
            IdentityInfo identityInfo = new IdentityInfo();
            identityInfo.setPatientNm(patient.getPatientNm());
            identityInfo.setBirthDate(patient.getBirthDate());
            identityInfo.setSex(patient.getSex());
            identityInfo.setCellPhone(patient.getCellPhone());

            Patient patientByIdentityInfo = patientDao.selectPatientByIdentityInfo(identityInfo);
            
            if (patientByIdentityInfo == null) {
                // 전달받은 본인인증 기준 환자정보 존재여부 확인
                throw new NotFoundPatientInfoException(messageSource.getMessage("message.notfound.IdentityInfo"
                    , null, Locale.getDefault()));
            } else if (!StringUtils.isEmpty(patientByIdentityInfo.getLoginId())) {
                // 전달받은 주민번호 기준 로그인ID 생성여부 확인
                throw new DuplicatePatientInfoException(messageSource.getMessage("message.duplicate.patientInfo"
                    , null, Locale.getDefault()));
            }

            // 로그인ID 중복체크
            if (checkDuplicateLoginId(patient.getLoginId())) {
                //throw new DuplicatePatientLoginIdException(patient.getLoginId());
                throw new DuplicatePatientLoginIdException(messageSource.getMessage("message.used.loginId"
                    , null, Locale.getDefault()));
            }

            patient.setPatientId(patientByIdentityInfo.getPatientId());
            patient.setSearsAccount(patientByIdentityInfo.getSearsAccount());  //시어스 계정
            // 비밀번호 암호화
            patient.setPassword(CryptoUtils.encrypt(patient.getPassword()));
            // 환자정보 생성 - 업데이트
            patientDao.createPatientInfo(patient);

        } else if (patient.getFlag().equals("M")) {
            // 환자정보 수정
            Patient patientByLoginId = patientDao.selectPatientByLoginId(patient.getLoginId());

            if (patientByLoginId == null) {
                throw new NotFoundPatientInfoException(messageSource.getMessage("message.notfound.loginId"
                    , null, Locale.getDefault()));
            } else if (!patient.getCellPhone().equals(patientByLoginId.getCellPhone())) {
                // 연락처 변경 시 변경연락처 존배여부 확인
                if (patientDao.selectPatientByCellPhone(patientByLoginId.getCellPhone()) != null) {
                    // 전달받은 주민번호 기준 로그인ID 생성여부 확인
                    throw new DuplicatePatientInfoException(messageSource.getMessage("message.duplicate.cellPhone"
                        , null, Locale.getDefault()));
                }
            }

            patient.setPatientId(patientByLoginId.getPatientId());
            patient.setSearsAccount(patientByLoginId.getSearsAccount());

            // 환자정보 업데이트
            patientDao.updatePatientInfo(patient);
        }

        return patientDao.selectPatientByLoginId(patient.getLoginId());
    }

    /**
     * 환자정보 조회-아이디 검색 조건 정보 기준
     * 
     * @param searchLoginIdInfo 아이디 검색 조건 정보
     * @return List&ltPatient&gt
     */
    public List<Patient> selectPatientBySearchLoginIdInfo(SearchLoginIdInfo searchLoginIdInfo) {
        return patientDao.selectPatientListBySearchLoginIdInfo(searchLoginIdInfo);
    }

    /**
     * 환자정보 조회-개인정보 확인 검색 조건 기준
     *
     * @param searchExistLoginInfo 개인정보 확인 검색 조건
     * @return List&ltPatient&gt
     */
    public List<Patient> selectPatientBySearchExistLoginInfo(SearchExistLoginInfo searchExistLoginInfo) {
        return patientDao.selectPatientBySearchExistLoginInfo(searchExistLoginInfo);
    }

    /**
     * 환자 비밀번호 변경-LoginId 기준
     * @param loginInfo 로그인 구성정보
     * @return affectedRow
     */
    public int updatePatientPasswordByLoginId(LoginInfo loginInfo) throws NotFoundPatientInfoException {
        // 환자정보 확인
        Patient patient = selectPatientByLoginId(loginInfo.getLoginId());
        if (patient == null) {
            throw new NotFoundPatientInfoException(messageSource.getMessage("message.notfound.patientInfo"
                , null, Locale.getDefault()));
        }

        // 비밀번호 암호화
        loginInfo.setPassword(CryptoUtils.encrypt(loginInfo.getPassword()));

        return patientDao.updatePatientPasswordByLoginId(loginInfo);
    }

    /**
     * 본인인증 내역 확인
     *
     * @param identityInfo 본인인증 확인 정보 (성명, 생년월일, 성별, 휴대폰)
     * @return IdentityResult 본인인증 완료 정보
     */
    public IdentityResult selectIdentityInfo(IdentityInfo identityInfo) throws NotFoundPatientInfoException {
        return patientDao.selectIdentityInfo(identityInfo);
    }
}
