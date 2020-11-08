CREATE TABLE IF NOT EXISTS `micronaut`.`books`
(
    `id`            BINARY(16)   NOT NULL,
    `book_name`     VARCHAR(100) NOT NULL,
    `publihed_date` DATETIME     NOT NULL,
    `author_id`     BINARY(16)   NOT NULL,
    PRIMARY KEY (`id`(16)),
    INDEX `books_ibfk_1_idx` (`author_id`(16) ASC) VISIBLE,
    CONSTRAINT `books_ibfk_1`
        FOREIGN KEY (`author_id`)
            REFERENCES `micronaut`.`authors` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci