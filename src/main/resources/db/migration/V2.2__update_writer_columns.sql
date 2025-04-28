-- 기존 프로시저 삭제
DROP PROCEDURE IF EXISTS UpdateWriterColumns;

-- 프로시저 정의
CREATE PROCEDURE UpdateWriterColumns()
BEGIN
    DECLARE column_exists_code_post INT;
    DECLARE column_exists_tilpost INT;

    -- code_post 테이블에서 member_id가 존재하는지 확인
    SELECT COUNT(*) INTO column_exists_code_post
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_NAME = 'code_post'
      AND COLUMN_NAME = 'member_id'
      AND TABLE_SCHEMA = 'autoreview'; -- 데이터베이스 이름으로 변경

    -- tilpost 테이블에서 member_id가 존재하는지 확인
    SELECT COUNT(*) INTO column_exists_tilpost
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_NAME = 'tilpost'
      AND COLUMN_NAME = 'member_id'
      AND TABLE_SCHEMA = 'autoreview'; -- 데이터베이스 이름으로 변경

    -- CodePost 테이블 업데이트 및 컬럼 제거
    IF column_exists_code_post > 0 THEN
        -- 기존 member_id 값을 writer_id로 복사
        UPDATE code_post cp
        SET cp.writer_id = cp.member_id
        WHERE cp.member_id IS NOT NULL;

        UPDATE code_post cp
            JOIN member m ON cp.writer_id = m.id
            SET cp.writer_email = m.email,
                cp.writer_nickname = m.nickname
        WHERE cp.writer_id IS NOT NULL;

        -- CodePost 테이블에서 member 외래 키 제거
        ALTER TABLE code_post
        DROP FOREIGN KEY FK8l99748ps5b7aylj2ydmlshxn;

                -- CodePost 테이블에서 member 컬럼 제거
        ALTER TABLE code_post
        DROP COLUMN member_id;
    END IF;

    -- TILPost 테이블 업데이트 및 컬럼 제거
    IF column_exists_tilpost > 0 THEN
        -- 기존 member_id 값을 writer_id로 복사
        UPDATE tilpost tp
        SET tp.writer_id = tp.member_id
        WHERE tp.member_id IS NOT NULL;

        UPDATE tilpost tp
            JOIN member m ON tp.writer_id = m.id
            SET tp.writer_email = m.email,
                tp.writer_nickname = m.nickname
        WHERE tp.writer_id IS NOT NULL;

        -- TILPost 테이블에서 member 외래 키 제거
        ALTER TABLE tilpost
        DROP FOREIGN KEY FKnpcux5ip4dp8r2k8oteprqd3c;

                -- TILPost 테이블에서 member 컬럼 제거
        ALTER TABLE tilpost
        DROP COLUMN member_id;
    END IF;
END;

-- 프로시저 호출
CALL UpdateWriterColumns();
