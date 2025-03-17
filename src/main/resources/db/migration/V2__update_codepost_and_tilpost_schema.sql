-- CodePost 테이블에 writer_id, writer_email, writer_nickname 컬럼 추가
ALTER TABLE code_post
    ADD COLUMN writer_id BIGINT,
    ADD COLUMN writer_email VARCHAR(255),
    ADD COLUMN writer_nickname VARCHAR(255);

-- TILPost 테이블에 writer_id, writer_email, writer_nickname 컬럼 추가
ALTER TABLE tilpost
    ADD COLUMN writer_id BIGINT,
    ADD COLUMN writer_email VARCHAR(255),
    ADD COLUMN writer_nickname VARCHAR(255);

-- 기존 member_id 값을 writer_id로 복사
UPDATE code_post cp
    SET cp.writer_id = cp.member_id
    WHERE cp.member_id IS NOT NULL;  -- member_id가 NULL이 아닌 경우에만 업데이트

UPDATE tilpost tp
    SET tp.writer_id = tp.member_id
    WHERE tp.member_id IS NOT NULL;  -- member_id가 NULL이 아닌 경우에만 업데이트

-- writer_id를 기준으로 writer_email, writer_nickname 업데이트
UPDATE code_post cp
    JOIN Member m ON cp.writer_id = m.id
    SET cp.writer_email = m.email,
        cp.writer_nickname = m.nickname
    WHERE cp.writer_id IS NOT NULL;  -- writer_id가 NULL이 아닌 경우에만 업데이트

UPDATE tilpost tp
    JOIN Member m ON tp.writer_id = m.id
    SET tp.writer_email = m.email,
        tp.writer_nickname = m.nickname
    WHERE tp.writer_id IS NOT NULL;  -- writer_id가 NULL이 아닌 경우에만 업데이트


-- CodePost 테이블에서 member 외래 키 제거
ALTER TABLE code_post
DROP FOREIGN KEY FK8l99748ps5b7aylj2ydmlshxn;

-- TILPost 테이블에서 member 외래 키 제거
ALTER TABLE tilpost
DROP FOREIGN KEY FKnpcux5ip4dp8r2k8oteprqd3c;

-- CodePost 테이블에서 member 컬럼 제거
ALTER TABLE code_post
DROP COLUMN member_id;

-- TILPost 테이블에서 member 컬럼 제거
ALTER TABLE tilpost
DROP COLUMN member_id;
