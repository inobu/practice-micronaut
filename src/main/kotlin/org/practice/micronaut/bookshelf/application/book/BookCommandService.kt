package org.practice.micronaut.bookshelf.application.book

import arrow.core.Either
import arrow.core.flatMap
import java.util.*
import javax.inject.Singleton
import org.practice.micronaut.bookshelf.domain.model.EntityId
import org.practice.micronaut.bookshelf.domain.repository.BookChangeCommand
import org.practice.micronaut.bookshelf.domain.repository.BookRepository
import org.practice.micronaut.bookshelf.domain.repository.BookUpdateCommand
import org.practice.micronaut.bookshelf.util.GlobalError

@Singleton
class BookCommandService(private val bookRepository: BookRepository) {
  fun editBook(bookUpdateCommand: BookUpdateCommand): Either<GlobalError, Unit> {
    return bookRepository
        .findById(EntityId(bookUpdateCommand.id))
        .flatMap { BookChangeCommand(bookUpdateCommand, it) }
        .flatMap { bookRepository.updatePrePublishedBook(it) }
  }

  fun deleteBook(id: UUID): Either<GlobalError, Unit> {
    return bookRepository.findById(EntityId(id)).flatMap { bookRepository.deleteBook(it.id) }
  }
}
