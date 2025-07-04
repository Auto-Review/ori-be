-- -- 1. token 컬럼 NOT NULL 제약 추가 (있다면 무시)
-- ALTER TABLE fcm_token
--     MODIFY COLUMN token VARCHAR(255) NOT NULL;

-- 2. token 컬럼에 UNIQUE 제약 추가
ALTER TABLE fcm_token
ADD CONSTRAINT uq_fcm_token UNIQUE (token);
