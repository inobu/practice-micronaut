package org.practice.micronaut.bookshelf.application.book

import org.practice.micronaut.bookshelf.domain.type.AuthorName
import org.practice.micronaut.bookshelf.domain.type.BookName
import org.practice.micronaut.bookshelf.domain.type.PublicationDate

data class BookDTO(val id: String, val bookName: BookName, val publicationDate: PublicationDate, val authorName: AuthorName)