/*
  PatientRestControllerTest 테스트용 Script
 */


/* 회원정보 조회, 격리상태 조회용 데이터 생성 */
INSERT
  INTO PATIENT ( PATIENT_ID, LOGIN_ID, PASSWORD, PATIENT_NM, SSN
               , BIRTH_DATE, SEX, CELL_PHONE, ZIP_CODE, ADDRESS1, ADDRESS2)
VALUES ( 'PTESTSHY99', 'testshy', 'bOFW4fVzdPuNJp89%2FeNG%2FA%3D%3D', 'shy-unittest', 'OQdb%2FlUq81M781GbkM8Vng%3D%3D'
        , '1988-12-05', 'M', '01092615960', '111111', '서울시', '헬스커넥트');

/* 격리/입소내역 생성 */
INSERT
  INTO ADMISSION ( ADMISSION_ID, PATIENT_ID, ADMISSION_DATE, DSCHGE_SCHDLD_DATE, DSCHGE_DATE
               , QANTN_DIV, PERSON_CHARGE, CENTER_ID, ROOM, REG_ID, UPD_ID)
VALUES ( 'TESTSHY999', 'PTESTSHY99', NOW(), '9999-12-31', null
       , '1', 'JUNIT_TEST', 'C999', '01', 'JUNIT', 'JUNIT');

/* 격리상태 생성 */
INSERT
  INTO qantn_status (ADMISSION_ID, QANTN_STATUS_DIV) VALUES ('TESTSHY999', '1');


/* 개인정보 중복 확인용 데이터 생성 */
INSERT
  INTO PATIENT ( PATIENT_ID, LOGIN_ID, PASSWORD, PATIENT_NM, SSN
               , BIRTH_DATE, SEX, CELL_PHONE, ZIP_CODE, ADDRESS1
               , ADDRESS2)
VALUES ( 'PTESTDUP01', 'testshydup1', 'bOFW4fVzdPuNJp89%2FeNG%2FA%3D%3D', 'shy-DUP', 'OQdb%2FlUq81M781GbkM8Vng%3D%3D'
       , '1988-12-05', 'M', '01092615960', '111111', '서울시', '헬스커넥트');

INSERT
  INTO PATIENT ( PATIENT_ID, LOGIN_ID, PASSWORD, PATIENT_NM, SSN
               , BIRTH_DATE, SEX, CELL_PHONE, ZIP_CODE, ADDRESS1
               , ADDRESS2)
VALUES ( 'PTESTDUP02', 'testshydup2', 'bOFW4fVzdPuNJp89%2FeNG%2FA%3D%3D', 'shy-DUP', 'OQdb%2FlUq81M781GbkM8Vng%3D%3D'
       , '1988-12-05', 'M', '01092615960', '111111', '서울시', '헬스커넥트');

/* 격리상태 저장을 위한 입소정보 생성 */
insert
  into admission ( ADMISSION_ID, PATIENT_ID, ADMISSION_DATE, DSCHGE_SCHDLD_DATE, DSCHGE_DATE
                 , QANTN_DIV, PERSON_CHARGE, CENTER_ID, ROOM, REG_ID
                 , UPD_ID)
values ( 'TESTSHY998', 'PTESTDUP01', NOW(), '9999-12-31', null
       , '1', 'JUNIT_TEST', 'C999', '01', 'JUNIT'
       , 'JUNIT');


/* 회원가입용 정보생성
   ssn : 8812051525252 */
INSERT
  INTO PATIENT ( PATIENT_ID, LOGIN_ID, PASSWORD, PATIENT_NM, SSN
        , BIRTH_DATE, SEX, CELL_PHONE, ZIP_CODE, ADDRESS1
        , ADDRESS2)
VALUES ( 'TESTPTNT99', NULL, NULL, '회원가입용', 'yH3roR4%2B1OJ1tL%2FgX%2Bh28w%3D%3D'
        , '1988-12-05', 'M', NULL, NULL, NULL, NULL);



