-- 프로시저 정의
DROP PROCEDURE IF EXISTS AddColumns;

CREATE PROCEDURE AddColumns()
BEGIN
    DECLARE column_exists INT;

    -- code_post 테이블에서 writer_id가 존재하는지 확인
SELECT COUNT(*) INTO column_exists
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'code_post'
  AND COLUMN_NAME = 'writer_id'
  AND TABLE_SCHEMA = 'autoreview'; -- 데이터베이스 이름으로 변경

-- writer_id가 존재하지 않으면 추가
IF column_exists = 0 THEN
ALTER TABLE code_post
    ADD COLUMN writer_id BIGINT,
        ADD COLUMN writer_email VARCHAR(255),
        ADD COLUMN writer_nickname VARCHAR(255);
END IF;

    -- tilpost 테이블에서 writer_id가 존재하는지 확인
SELECT COUNT(*) INTO column_exists
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'tilpost'
  AND COLUMN_NAME = 'writer_id'
  AND TABLE_SCHEMA = 'autoreview'; -- 데이터베이스 이름으로 변경

-- writer_id가 존재하지 않으면 추가
IF column_exists = 0 THEN
ALTER TABLE tilpost
    ADD COLUMN writer_id BIGINT,
        ADD COLUMN writer_email VARCHAR(255),
        ADD COLUMN writer_nickname VARCHAR(255);
END IF;
END;

-- 프로시저 호출
CALL AddColumns();
