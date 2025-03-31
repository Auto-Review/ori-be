-- 중복 이메일이 있는지 확인
SELECT email
FROM member
GROUP BY email
HAVING COUNT(*) > 1;

-- 조건에 따라 UNIQUE 제약 조건 추가
ALTER TABLE member
    ADD CONSTRAINT UKmbmcqelty0fbrvxp1q58dn57t UNIQUE (email);
