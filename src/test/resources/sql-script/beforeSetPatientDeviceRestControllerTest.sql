/*
-- >> Test Script <<
-- 환자 내원정보 생성
-- PATIENT_ID : PTESTSHY51
-- LOGIN_ID   : testshy51
-- PASSWORD   : 1234
-- SSN        : 8812051999951
*/

INSERT
INTO PATIENT ( PATIENT_ID, LOGIN_ID, PASSWORD, PATIENT_NM, SSN
             , BIRTH_DATE, SEX, CELL_PHONE, ZIP_CODE, ADDRESS1
             , ADDRESS2)
VALUES ( 'PTESTSHY51', 'testshy51', 'bOFW4fVzdPuNJp89%2FeNG%2FA%3D%3D', 'shy-unittest', 'LKfBXMrRmzsxJiZtK7jH6Q%3D%3D'
       , '1988-12-05', 'M', '01092615960', '111111', '서울시', '헬스커넥트');

insert
into admission ( ADMISSION_ID, PATIENT_ID, ADMISSION_DATE, DSCHGE_SCHDLD_DATE, DSCHGE_DATE
               , QANTN_DIV, PERSON_CHARGE, CENTER_ID, ROOM, REG_ID
               , UPD_ID)
values ( 'TESTSHY951', 'PTESTSHY51', NOW(), '9999-12-31', null
       , '1', 'JUNIT_TEST', 'C999', '01', 'JUNIT'
       , 'JUNIT');

/*
-- 비정상 데이터 - 다중입소내역
-- PATIENT_ID : PTESTSHY98
-- LOGIN_ID   : testshy2
-- PASSWORD   : 1234
-- SSN        : 8812051555555
*/

INSERT
INTO PATIENT ( PATIENT_ID, LOGIN_ID, PASSWORD, PATIENT_NM, SSN
             , BIRTH_DATE, SEX, CELL_PHONE, ZIP_CODE, ADDRESS1
             , ADDRESS2)
VALUES ( 'PTESTSHY98', 'testshy2', 'bOFW4fVzdPuNJp89%2FeNG%2FA%3D%3D', 'shy-unittest2', 'Q4nqavZTIDVui%2FdhI%2Bafcg%3D%3D'
       , '1988-12-05', 'M', '01092619999', '111111', '서울시', '헬스커넥트');

insert
into admission ( ADMISSION_ID, PATIENT_ID, ADMISSION_DATE, DSCHGE_SCHDLD_DATE, DSCHGE_DATE
               , QANTN_DIV, PERSON_CHARGE, CENTER_ID, ROOM, REG_ID
               , UPD_ID)
values ( 'TESTSHY997', 'PTESTSHY98', NOW(), '9999-12-31', null
       , '1', 'JUNIT_TEST', 'C999', '01', 'JUNIT'
       , 'JUNIT');

insert
into admission ( ADMISSION_ID, PATIENT_ID, ADMISSION_DATE, DSCHGE_SCHDLD_DATE, DSCHGE_DATE
               , QANTN_DIV, PERSON_CHARGE, CENTER_ID, ROOM, REG_ID
               , UPD_ID)
values ( 'TESTSHY998', 'PTESTSHY98', NOW(), '9999-12-31', null
       , '2', 'JUNIT_TEST', 'C999', '01', 'JUNIT'
       , 'JUNIT');