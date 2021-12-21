package org.practice.micronaut.bookshelf.presentation.book

import java.time.format.DateTimeFormatter
import org.practice.micronaut.bookshelf.application.book.BookDTO

data class BookResponse(
    val id: String,
    val bookName: String?,
    val publicationDate: String,
    val authorName: String
) {
  companion object {
    fun fromDTO(bookDTO: BookDTO): BookResponse {
      return bookDTO.run {
        BookResponse(
            id,
            bookName.value,
            publicationDate.format(DateTimeFormatter.ISO_DATE),
            authorName.value)
      }
    }
  }
}
