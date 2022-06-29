/**
  공통코드상세 중복 테스트용 코드 생성
 */
INSERT
  INTO COM_CD
     ( COM_CD
     , COM_CD_NM
     , COM_CD_DIV
     , REG_ID
     , UPD_ID)
VALUES
     ( 'JUINT'
     , 'JUNIT 중복 저장 테스트'
     , 'TEST'
     , 'admin'
     , 'admin')
;

INSERT
  INTO COM_CD_DETAIL
     ( COM_CD
     , DETAIL_CD
     , DETAIL_CD_NM
     , SORT_SEQ
     , REG_ID
     , UPD_ID)
VALUES
     ( 'JUINT'
     , 'JUNIT-DUP-TEST'
     , 'JUNIT 중복 저장 테스트'
     , 1
     , 'admin'
     , 'admin'
     )
;