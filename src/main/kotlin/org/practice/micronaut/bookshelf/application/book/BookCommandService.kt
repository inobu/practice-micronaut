package org.practice.micronaut.bookshelf.application.book

import arrow.core.Either
import arrow.core.flatMap
import org.practice.micronaut.bookshelf.domain.model.EntityId
import org.practice.micronaut.bookshelf.domain.model.book.PrePublishedBook
import org.practice.micronaut.bookshelf.domain.repository.BookRepository
import org.practice.micronaut.bookshelf.domain.repository.BookUpdateCommand
import org.practice.micronaut.bookshelf.util.GlobalError
import java.time.LocalDate

import javax.inject.Singleton

@Singleton
class BookCommandService(private val bookRepository: BookRepository) {
    fun editBook(bookUpdateCommand: BookUpdateCommand): Either<GlobalError, Unit> {
        return bookRepository.findById(EntityId(bookUpdateCommand.id))
                .flatMap { PrePublishedBook(bookUpdateCommand.id, bookUpdateCommand.bookName, bookUpdateCommand.publicationDate, LocalDate.now()) }
                .flatMap { bookRepository.updatePrePublishedBook(bookUpdateCommand) }
    }
}