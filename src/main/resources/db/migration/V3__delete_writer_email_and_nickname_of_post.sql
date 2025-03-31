-- 기존 프로시저 삭제
DROP PROCEDURE IF EXISTS DropWriterColumns;

-- 프로시저 정의
CREATE PROCEDURE DropWriterColumns()
BEGIN
    -- CodePost 테이블에서 writer_email과 writer_nickname 컬럼 제거
    IF EXISTS (
        SELECT * 
        FROM INFORMATION_SCHEMA.COLUMNS 
        WHERE TABLE_NAME = 'code_post' 
          AND COLUMN_NAME = 'writer_email' 
          AND TABLE_SCHEMA = 'autoreview'
    ) THEN
        ALTER TABLE code_post DROP COLUMN writer_email;
    END IF;

    IF EXISTS (
        SELECT * 
        FROM INFORMATION_SCHEMA.COLUMNS 
        WHERE TABLE_NAME = 'code_post' 
          AND COLUMN_NAME = 'writer_nickname' 
          AND TABLE_SCHEMA = 'autoreview'
    ) THEN
        ALTER TABLE code_post DROP COLUMN writer_nickname;
    END IF;

    -- TILPost 테이블에서 writer_email과 writer_nickname 컬럼 제거
    IF EXISTS (
        SELECT * 
        FROM INFORMATION_SCHEMA.COLUMNS 
        WHERE TABLE_NAME = 'tilpost' 
          AND COLUMN_NAME = 'writer_email' 
          AND TABLE_SCHEMA = 'autoreview'
    ) THEN
        ALTER TABLE tilpost DROP COLUMN writer_email;
    END IF;

    IF EXISTS (
        SELECT * 
        FROM INFORMATION_SCHEMA.COLUMNS 
        WHERE TABLE_NAME = 'tilpost' 
          AND COLUMN_NAME = 'writer_nickname' 
          AND TABLE_SCHEMA = 'autoreview'
    ) THEN
        ALTER TABLE tilpost DROP COLUMN writer_nickname;
    END IF;
END;

-- 프로시저 호출
CALL DropWriterColumns();
