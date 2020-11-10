package org.practice.micronaut.bookshelf.presentation.book

import org.practice.micronaut.bookshelf.application.book.BookDTO
import java.time.format.DateTimeFormatter

data class BookResponse(val id: String, val bookName: String?, val publicationDate: String, val authorName: String) {
    companion object {
        fun fromDTO(bookDTO: BookDTO): BookResponse {
            return bookDTO.run {
                BookResponse(
                        id,
                        bookName.value,
                        publicationDate.value.format(DateTimeFormatter.ISO_DATE),
                        authorName.value
                )
            }
        }
    }
}