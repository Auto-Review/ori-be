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