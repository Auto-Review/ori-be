-- 1. 외래키 제약 조건 제거
ALTER TABLE code_post_bookmark
DROP FOREIGN KEY fk_code_post_bookmark_member;

-- 2. 인덱스 제거 (외래키에 대한 인덱스였다면 함께 제거)
DROP INDEX fk_code_post_bookmark_member ON code_post_bookmark;

-- 3. 컬럼 제거
ALTER TABLE code_post_bookmark
DROP COLUMN member_id;
