package org.practice.micronaut.bookshelf.application.book

import java.time.LocalDate
import org.practice.micronaut.bookshelf.domain.type.AuthorName
import org.practice.micronaut.bookshelf.domain.type.BookName

data class BookDTO(
    val id: String,
    val bookName: BookName,
    val publicationDate: LocalDate,
    val authorName: AuthorName
)
