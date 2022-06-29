/**
  환자 중복 확인용 데이터 생성
 */
INSERT
  INTO patient
     ( PATIENT_ID
     , PATIENT_NM
     , BIRTH_DATE
     , SEX
     , CELL_PHONE)
VALUES
     ( 'JUNITTEST1'
     , 'JUNIT-DUP-TEST'
     , '19881205'
     , 'M'
     , '01011221122')
;

INSERT
  INTO patient
     ( PATIENT_ID
     , PATIENT_NM
     , BIRTH_DATE
     , SEX
     , CELL_PHONE)
VALUES
     ( 'JUNITTEST2'
     , 'JUNIT-DUP-TEST2'
     , '19881205'
     , 'M'
     , '01011221122')
;

/**
  입소 중복 테스트를 위한 입소내역 생성
 */
INSERT
  INTO admission
     ( ADMISSION_ID
     , PATIENT_ID
     , ADMISSION_DATE
     , DSCHGE_SCHDLD_DATE
     , QANTN_DIV
     , PERSON_CHARGE
     , CENTER_ID
     , ROOM
     , REG_ID
     , UPD_ID)
VALUES
     ( 'JUNITADMIN'
     , 'JUNITTEST1'
     , NOW()
     , DATE_ADD(NOW(), INTERVAL 10 DAY)
     , '2'
     , 'TEST'
     , 'C001'
     , '01'
     , 'admin'
     , 'admin'
     )
;

/**
  입소자 퇴소처리 테스트를 위한 입소내역 생성
 */
INSERT
  INTO admission
     ( ADMISSION_ID
     , PATIENT_ID
     , ADMISSION_DATE
     , DSCHGE_SCHDLD_DATE
     , QANTN_DIV
     , PERSON_CHARGE
     , CENTER_ID
     , ROOM
     , REG_ID
     , UPD_ID)
VALUES
     ( 'JUNITDISCH'
     , 'JUNITTEST2'
     , DATE_ADD(NOW(), INTERVAL -2 DAY)
     , DATE_ADD(NOW(), INTERVAL 10 DAY)
     , '2'
     , 'TEST'
     , 'C001'
     , '01'
     , 'admin'
     , 'admin'
     )
;
