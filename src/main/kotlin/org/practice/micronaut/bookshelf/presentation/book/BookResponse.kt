package org.practice.micronaut.bookshelf.presentation.book

import org.practice.micronaut.bookshelf.application.book.BookDTO
import java.time.LocalDate

data class BookResponse(val id: String, val bookName: String?, val publicationDate: LocalDate, val authorName: String) {
    companion object {
        fun fromDTO(bookDTO: BookDTO): BookResponse {
            return bookDTO.run {
                BookResponse(
                        id,
                        bookName.value,
                        publicationDate.value,
                        authorName.value
                )
            }
        }
    }
}