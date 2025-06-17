CREATE TABLE 'tb_password_recover' (
    `id` BIGINT NOT NULL  AUTO_INCREMENT,
    `token` VARCHAR NOT NULL,
    `email` VARCHAR NOT NULL,
    `expiration` VARCHAR NOT NULL,
    PRIMARY KEY (`id`)
)