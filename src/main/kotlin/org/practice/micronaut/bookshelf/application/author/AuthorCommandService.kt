package org.practice.micronaut.bookshelf.application.author

import arrow.core.Either
import arrow.core.flatMap
import java.time.LocalDate
import java.util.*
import javax.inject.Singleton
import org.practice.micronaut.bookshelf.domain.model.EntityId
import org.practice.micronaut.bookshelf.domain.model.author.Author
import org.practice.micronaut.bookshelf.domain.model.book.PrePublishedBook
import org.practice.micronaut.bookshelf.domain.repository.AuthorRepository
import org.practice.micronaut.bookshelf.domain.repository.BookRepository
import org.practice.micronaut.bookshelf.util.GlobalError

@Singleton
class AuthorCommandService(
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository
) {
  fun commandAuthor(authorName: String?): Either<GlobalError, Unit> {
    return Author(authorName).flatMap { authorRepository.saveAuthor(it) }
  }

  fun updateAuthor(id: UUID, authorName: String?): Either<GlobalError, Unit> {
    return Author(id, authorName).flatMap { authorRepository.updateAuthor(it) }
  }

  fun createPrePublishedBook(
      authorId: UUID,
      bookName: String?,
      publicationDate: LocalDate?
  ): Either<GlobalError, Unit> {
    return authorRepository
        .findById(EntityId(authorId))
        .flatMap { PrePublishedBook(bookName, publicationDate, LocalDate.now()) }
        .flatMap { bookRepository.savePrePublishedBook(it, EntityId(authorId)) }
  }
}
