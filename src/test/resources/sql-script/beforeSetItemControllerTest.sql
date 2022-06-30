/**
  수정 테스트용 아이템 생성
 */
INSERT
  INTO ITEM
     ( ITEM_ID
     , ITEM_NM
     , UNIT
     , REF_FROM
     , REF_TO
     , REG_ID
     , UPD_ID)
VALUES
     ( 'T-001'
     , 'JUNIT-UPDATE-TEST'
     , 'U'
     , 1
     , 2
     , 'admin'
     , 'admin'
     )
;

/**
  삭제 테스트용 아이템 생성
 */
INSERT
  INTO ITEM
     ( ITEM_ID
     , ITEM_NM
     , UNIT
     , REF_FROM
     , REF_TO
     , REG_ID
     , UPD_ID)
VALUES
     ( 'T-002'
     , 'JUNIT-DELETE-TEST'
     , 'D'
     , 1
     , 2
     , 'admin'
     , 'admin'
     )
;