-- 1. email 컬럼 추가
ALTER TABLE code_post_bookmark
    ADD COLUMN email VARCHAR(255);

-- 2. member_id → member_email 데이터 복사
UPDATE code_post_bookmark cb
    JOIN member m ON cb.member_id = m.id
    SET cb.email = m.email;

-- 3. member_email NOT NULL 설정
ALTER TABLE code_post_bookmark
    MODIFY COLUMN email VARCHAR(255) NOT NULL;

-- 4. 기존 Unique 제약조건 삭제
ALTER TABLE code_post_bookmark
    DROP INDEX uq_codepost_member;

-- 5. 새로운 Unique 제약조건 추가
ALTER TABLE code_post_bookmark
    ADD CONSTRAINT uq_email_codepost UNIQUE (email, code_post_id);

-- 6. 외래키 제약 조건 제거 (필요 시)
ALTER TABLE code_post_bookmark
    DROP FOREIGN KEY FKjy49hk8gdyg92iir31mofw9fh; -- 실제 제약조건 이름은 확인 필요

-- 7. member_id 컬럼 제거
ALTER TABLE code_post_bookmark
    DROP COLUMN member_id;
