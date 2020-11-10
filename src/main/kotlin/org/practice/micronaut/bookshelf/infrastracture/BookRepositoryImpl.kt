package org.practice.micronaut.bookshelf.infrastracture

import arrow.core.*
import arrow.core.extensions.fx
import nu.studer.sample.tables.BookNames
import nu.studer.sample.tables.Books
import org.jooq.DSLContext
import org.jooq.exception.DataAccessException
import org.practice.micronaut.bookshelf.domain.lib.toBytes
import org.practice.micronaut.bookshelf.domain.lib.toUUID
import org.practice.micronaut.bookshelf.domain.model.EntityId
import org.practice.micronaut.bookshelf.domain.model.author.Author
import org.practice.micronaut.bookshelf.domain.model.book.PrePublishedBook
import org.practice.micronaut.bookshelf.domain.model.book.PublishedBook
import org.practice.micronaut.bookshelf.domain.repository.BookRepository
import org.practice.micronaut.bookshelf.domain.repository.BookUpdateCommand
import org.practice.micronaut.bookshelf.util.GlobalError
import java.time.LocalDate
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

    override fun updatePrePublishedBook(bookUpdateCommand: BookUpdateCommand): Either<GlobalError, Unit> {
        return runCatching {
            dslContext.transaction { c ->
                c.dsl().update(Books.BOOKS)
                        .set(Books.BOOKS.PUBLICATION_DATE, LocalDateTime.of(bookUpdateCommand.publicationDate, LocalTime.MAX))
                        .where(Books.BOOKS.ID.eq(bookUpdateCommand.id.toBytes()))
                        .execute()
                bookUpdateCommand.bookName?.run {
                    c.dsl().update(BookNames.BOOK_NAMES)
                            .set(BookNames.BOOK_NAMES.BOOK_NAME, this)
                            .where(BookNames.BOOK_NAMES.BOOKS_ID.eq(bookUpdateCommand.id.toBytes()))
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

    override fun findById(entityId: EntityId<PrePublishedBook>): Either<GlobalError, PrePublishedBook> {
        return dslContext.fetchOne(
                Books.BOOKS.join(BookNames.BOOK_NAMES).on(Books.BOOKS.ID.eq(BookNames.BOOK_NAMES.BOOKS_ID)),
                Books.BOOKS.ID.eq(entityId.value.toBytes()),
        )
                .rightIfNotNull { GlobalError.NotFoundError }
                .flatMap {
                    Either.fx {
                        val bookId = it.getValue(Books.BOOKS.ID)
                        val date = it.getValue(Books.BOOKS.PUBLICATION_DATE)
                        val bookName = it.getValue(BookNames.BOOK_NAMES.BOOK_NAME)
                        PrePublishedBook(
                                bookId.toUUID(),
                                bookName,
                                date.toLocalDate(),
                                LocalDate.now()
                        )
                                .bind()
                    }
                }
    }

    override fun deleteBook(entityId: EntityId<PrePublishedBook>): Either<GlobalError, Unit> {
        return dslContext
                .delete(Books.BOOKS)
                .where(
                        Books.BOOKS.ID.eq(
                                entityId.value.toBytes())
                ).runCatching { execute() }
                .fold(
                        onSuccess = {
                            when (it == 0) {
                                true -> GlobalError.NotFoundError.left()
                                false -> {
                                    {}().right()
                                }
                            }
                        },
                        onFailure = { throwable ->
                            when (throwable) {
                                is DataAccessException -> GlobalError.DatabaseConflictsError
                                else -> GlobalError.SeriousSystemError
                            }.left()
                        }
                )

    }
}