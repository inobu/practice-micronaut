package org.practice.micronaut.bookshelf.infrastracture

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import nu.studer.sample.tables.BookNames
import nu.studer.sample.tables.Books
import org.jooq.DSLContext
import org.jooq.exception.DataAccessException
import org.practice.micronaut.bookshelf.domain.lib.toBytes
import org.practice.micronaut.bookshelf.domain.model.EntityId
import org.practice.micronaut.bookshelf.domain.model.author.Author
import org.practice.micronaut.bookshelf.domain.model.book.PrePublishedBook
import org.practice.micronaut.bookshelf.domain.model.book.PublishedBook
import org.practice.micronaut.bookshelf.domain.repository.BookRepository
import org.practice.micronaut.bookshelf.util.GlobalError
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepositoryImpl
@Inject constructor(private val dslContext: DSLContext) : BookRepository {

    override fun savePublishedBook(publishedBook: PublishedBook, entityId: EntityId<Author>): Either<GlobalError, Unit> {
        return runCatching {
            dslContext.transaction { c ->
                c.dsl().insertInto(Books.BOOKS)
                        .columns(Books.BOOKS.ID, Books.BOOKS.PUBLICATION_DATE, Books.BOOKS.AUTHOR_ID)
                        .values(publishedBook.id.value.toBytes(), LocalDateTime.of(publishedBook.publicationDate.value, LocalTime.MIN), entityId.value.toBytes())
                        .execute()

                c.dsl().insertInto(BookNames.BOOK_NAMES)
                        .columns(BookNames.BOOK_NAMES.ID, BookNames.BOOK_NAMES.BOOK_NAME, BookNames.BOOK_NAMES.BOOKS_ID)
                        .values(UUID.randomUUID().toBytes(), publishedBook.bookName.value, publishedBook.id.value.toBytes())
                        .execute()
            }
        }.fold(
                onSuccess = { it.right() },
                onFailure = {
                    when (it) {
                        is DataAccessException -> GlobalError.DatabaseConflictsError
                        else -> GlobalError.SeriousSystemError
                    }.left()
                },
        )
    }

    override fun savePrePublishedBook(prePublishedBook: PrePublishedBook, entityId: EntityId<Author>): Either<GlobalError, Unit> {
        return runCatching {
            dslContext.transaction { c ->
                c.dsl().insertInto(Books.BOOKS)
                        .columns(Books.BOOKS.ID, Books.BOOKS.PUBLICATION_DATE, Books.BOOKS.AUTHOR_ID)
                        .values(prePublishedBook.id.value.toBytes(), LocalDateTime.of(prePublishedBook.publicationDate.value, LocalTime.MIN), entityId.value.toBytes())
                        .execute()

                prePublishedBook.bookName.orNull()?.value?.let {
                    c.dsl().insertInto(BookNames.BOOK_NAMES)
                            .columns(BookNames.BOOK_NAMES.ID, BookNames.BOOK_NAMES.BOOK_NAME, BookNames.BOOK_NAMES.BOOKS_ID)
                            .values(UUID.randomUUID().toBytes(), it, prePublishedBook.id.value.toBytes())
                            .execute()
                }
            }
        }.fold(
                onSuccess = { it.right() },
                onFailure = {
                    when (it) {
                        is DataAccessException -> GlobalError.DatabaseConflictsError
                        else -> GlobalError.SeriousSystemError
                    }.left()
                },
        )
    }
}