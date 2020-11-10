package org.practice.micronaut.bookshelf.application.book

import org.practice.micronaut.bookshelf.domain.type.AuthorName
import org.practice.micronaut.bookshelf.domain.type.BookName
import org.practice.micronaut.bookshelf.domain.type.PublicationDate
import java.time.LocalDate

data class BookDTO(val id: String, val bookName: BookName, val publicationDate: LocalDate, val authorName: AuthorName)