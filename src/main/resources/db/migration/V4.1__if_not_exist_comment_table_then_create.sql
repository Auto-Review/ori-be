CREATE TABLE IF NOT EXISTS code_post_comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    writer_id BIGINT NOT NULL,
    writer_nick_name VARCHAR(255) NOT NULL,
    writer_email VARCHAR(255) NOT NULL,
    mention_nick_name VARCHAR(255),
    mention_email VARCHAR(255),
    body TEXT NOT NULL,
    is_public BOOLEAN NOT NULL DEFAULT TRUE,
    code_post_id BIGINT,
    parent_id BIGINT,
    create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (code_post_id) REFERENCES code_post(id),
    FOREIGN KEY (parent_id) REFERENCES code_post_comment(id)
);

CREATE TABLE IF NOT EXISTS til_post_comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    writer_id BIGINT NOT NULL,
    writer_nick_name VARCHAR(255) NOT NULL,
    writer_email VARCHAR(255) NOT NULL,
    mention_nick_name VARCHAR(255),
    mention_email VARCHAR(255),
    body TEXT NOT NULL,
    is_public BOOLEAN NOT NULL DEFAULT TRUE,
    til_post_id BIGINT,
    parent_id BIGINT,
    create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (til_post_id) REFERENCES tilpost(til_post_id),
    FOREIGN KEY (parent_id) REFERENCES til_post_comment(id)
);


