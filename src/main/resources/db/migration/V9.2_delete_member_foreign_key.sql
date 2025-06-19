-- 1. 외래키 제약 조건 제거 (필요 시)
ALTER TABLE code_post_bookmark
    DROP FOREIGN KEY FKjy49hk8gdyg92iir31mofw9fh; -- 실제 제약조건 이름은 확인 필요

-- 2. member_id 컬럼 제거
ALTER TABLE code_post_bookmark
    DROP COLUMN member_id;