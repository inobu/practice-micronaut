package org.practice.micronaut.bookshelf.infrastracture

import arrow.core.Option
import arrow.core.extensions.fx
import nu.studer.sample.tables.Authors
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
                .from(Authors.AUTHORS)
                .innerJoin(Books.BOOKS)
                .on((Authors.AUTHORS.ID).eq(Books.BOOKS.AUTHOR_ID))
                .where(Books.BOOKS.ID.eq(id.uuidToBytes()))
                .fetchOneInto(BookTable::class.java)
                .run {
                    val books = this
                    Option.fx {
                        BookDTO(
                                bytesToUuid(books?.id).toString(),
                                BookName(books?.bookName).bind(),
                                PublicationDate(
                                        books?.publihedDate?.toLocalDate(),
                                        LocalDate.now()
                                ).bind(),
                                AuthorName(books.authorName).bind()
                        )
                    }
                }
    }
}