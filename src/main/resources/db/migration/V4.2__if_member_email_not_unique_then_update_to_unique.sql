DELIMITER //

-- 프로시저 정의
CREATE PROCEDURE AddUniqueConstraintIfNotExists()
BEGIN
    DECLARE constraint_exists INT DEFAULT 0;

    -- 유니크 제약 조건 존재 여부 확인
SELECT COUNT(*) INTO constraint_exists
FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
WHERE TABLE_NAME = 'member'
  AND CONSTRAINT_TYPE = 'UNIQUE'
  AND CONSTRAINT_NAME = 'UKmbmcqelty0fbrvxp1q58dn57t'
  AND TABLE_SCHEMA = 'localautoreview';

-- 제약 조건이 존재하지 않으면 추가
IF constraint_exists = 0 THEN
    ALTER TABLE member ADD CONSTRAINT UKmbmcqelty0fbrvxp1q58dn57t UNIQUE (email);
END IF;
END //

DELIMITER ;

-- 프로시저 호출
CALL AddUniqueConstraintIfNotExists();
