-- book table

CREATE TABLE IF NOT EXISTS `micronaut`.`authors` (
  `id` BINARY(16) NOT NULL,
  `author_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`(16)))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci