ALTER TABLE `micronaut`.`book_names`
    DROP FOREIGN KEY `book_names_ibfk_1`;
ALTER TABLE `micronaut`.`book_names`
    ADD CONSTRAINT `book_names_ibfk_1`
        FOREIGN KEY (`books_id`)
            REFERENCES `micronaut`.`books` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE;