CREATE TABLE IF NOT EXISTS `micronaut`.`authors`
(
    `id`          BINARY(16)   NOT NULL,
    `author_name` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`(16)),
    UNIQUE INDEX `author_name_UNIQUE` (`author_name` ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `micronaut`.`books`
(
    `id`               BINARY(16) NOT NULL,
    `publication_date` DATETIME   NOT NULL,
    `author_id`        BINARY(16) NOT NULL,
    PRIMARY KEY (`id`(16)),
    INDEX `books_ibfk_1_idx` (`author_id`(16) ASC) VISIBLE,
    CONSTRAINT `books_ibfk_1`
        FOREIGN KEY (`author_id`)
            REFERENCES `micronaut`.`authors` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `micronaut`.`book_names`
(
    `id`        BINARY(16)   NOT NULL,
    `book_name` VARCHAR(100) NOT NULL,
    `books_id`  BINARY(16)   NOT NULL,
    PRIMARY KEY (`id`(16)),
    UNIQUE INDEX `book_name_UNIQUE` (`book_name` ASC) VISIBLE,
    UNIQUE INDEX `books_id_UNIQUE` (`books_id`(16) ASC) VISIBLE,
    CONSTRAINT `book_names_ibfk_1`
        FOREIGN KEY (`books_id`)
            REFERENCES `micronaut`.`books` (`id`)
            ON DELETE RESTRICT
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;