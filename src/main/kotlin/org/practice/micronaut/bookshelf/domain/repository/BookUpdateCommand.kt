package org.practice.micronaut.bookshelf.domain.repository

import arrow.core.Either
import arrow.core.fix
import arrow.core.left
import arrow.core.right
import org.practice.micronaut.bookshelf.domain.model.book.PrePublishedBook
import org.practice.micronaut.bookshelf.util.GlobalError
import java.time.LocalDate
import java.util.*

data class BookUpdateCommand(val id: UUID, val bookName: String?, val publicationDate: LocalDate)

data class BookChangeCommand private constructor(val id: UUID, val bookName: String?, val publicationDate: LocalDate) {
    companion object {
        operator fun invoke(command: BookUpdateCommand, prePublishedBook: PrePublishedBook): Either<GlobalError.DomainError, BookChangeCommand> {
            return if (
                    LocalDate.now().isBefore(prePublishedBook.publicationDate.value)
                    &&
                    LocalDate.now().isBefore(command.publicationDate)
            ) {
                command.run {
                    BookChangeCommand(id, bookName, publicationDate).right()
                }
            } else {
                GlobalError.DomainError.left()
            }.fix()
        }
    }

}