-- 프로시저 정의
DROP PROCEDURE IF EXISTS ModifyRoleColumn;

CREATE PROCEDURE ModifyRoleColumn()
BEGIN
    DECLARE column_type VARCHAR(64);

    -- 현재 role 컬럼의 데이터 타입을 확인
SELECT DATA_TYPE INTO column_type
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'member'
  AND COLUMN_NAME = 'role'
  AND TABLE_SCHEMA = 'autoreview'; -- 데이터베이스 이름으로 변경

-- role 컬럼이 TINYINT가 아닐 경우에만 수정
IF column_type IS NULL OR column_type != 'tinyint' THEN
ALTER TABLE member
    MODIFY COLUMN role TINYINT;
END IF;
END;

-- 프로시저 호출
CALL ModifyRoleColumn();
