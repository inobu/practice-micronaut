package org.practice.micronaut.bookshelf.domain.repository

import arrow.core.Either
import org.practice.micronaut.bookshelf.domain.model.EntityId
import org.practice.micronaut.bookshelf.domain.model.author.Author
import org.practice.micronaut.bookshelf.domain.model.book.PrePublishedBook
import org.practice.micronaut.bookshelf.domain.model.book.PublishedBook
import org.practice.micronaut.bookshelf.util.GlobalError

interface BookRepository {
  fun savePublishedBook(
      publishedBook: PublishedBook,
      entityId: EntityId<Author>
  ): Either<GlobalError, Unit>

  fun savePrePublishedBook(
      prePublishedBook: PrePublishedBook,
      entityId: EntityId<Author>
  ): Either<GlobalError, Unit>

  fun updatePrePublishedBook(bookChangeCommand: BookChangeCommand): Either<GlobalError, Unit>

  fun findById(entityId: EntityId<PrePublishedBook>): Either<GlobalError, PrePublishedBook>

  fun deleteBook(entityId: EntityId<PrePublishedBook>): Either<GlobalError, Unit>
}
