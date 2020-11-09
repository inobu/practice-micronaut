package org.practice.micronaut.bookshelf.presentation.book

import java.time.LocalDate

data class BookResponse(val id: String, val bookName: String?, val publicationDate: LocalDate, val authorName: String)