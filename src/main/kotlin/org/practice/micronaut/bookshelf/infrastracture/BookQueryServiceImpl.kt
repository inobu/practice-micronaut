package org.practice.micronaut.bookshelf.infrastracture

import arrow.core.Option
import arrow.core.extensions.fx
import nu.studer.sample.tables.Authors
import nu.studer.sample.tables.BookNames
import nu.studer.sample.tables.Books
import org.jooq.DSLContext
import org.practice.micronaut.bookshelf.application.BookDTO
import org.practice.micronaut.bookshelf.application.BookQueryService
import org.practice.micronaut.bookshelf.domain.lib.bytesToUuid
import org.practice.micronaut.bookshelf.domain.lib.uuidToBytes
import org.practice.micronaut.bookshelf.domain.type.AuthorName
import org.practice.micronaut.bookshelf.domain.type.BookName
import org.practice.micronaut.bookshelf.domain.type.PublicationDate
import java.time.LocalDate
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookQueryServiceImpl
@Inject constructor(private val dSLContext: DSLContext) : BookQueryService {
    override fun findBook(id: UUID): Option<BookDTO> {
        return dSLContext.select()
                .from(
                        Books.BOOKS
                                .join(Authors.AUTHORS)
                                .on(Books.BOOKS.AUTHOR_ID.eq(Authors.AUTHORS.ID))
                                .join(BookNames.BOOK_NAMES)
                                .on(Books.BOOKS.ID.eq(BookNames.BOOK_NAMES.BOOKS_ID))
                )
                .where(Books.BOOKS.ID.eq(id.uuidToBytes()))
                .fetchOne().run {
                    val bookId = getValue(Books.BOOKS.ID)
                    val date = getValue(Books.BOOKS.PUBLICATION_DATE)
                    val authorName = getValue(Authors.AUTHORS.AUTHOR_NAME)
                    val bookName = getValue(BookNames.BOOK_NAMES.BOOK_NAME)
                    Option.fx {
                        BookDTO(
                                bytesToUuid(bookId).toString(),
                                BookName(bookName).bind(),
                                PublicationDate(
                                        date?.toLocalDate(),
                                        LocalDate.now()
                                ).bind(),
                                AuthorName(authorName).bind()
                        )
                    }
                }
    }
}