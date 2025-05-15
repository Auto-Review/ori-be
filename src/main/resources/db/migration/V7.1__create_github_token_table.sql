CREATE TABLE `github_token` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `email` varchar(255) NOT NULL,
    `github_token` varchar(255) DEFAULT NULL,
    `create_date` datetime(6) DEFAULT NULL,
    `update_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK4b3exbfoct7fo6vl8upp3qgo3` (`email`)
)