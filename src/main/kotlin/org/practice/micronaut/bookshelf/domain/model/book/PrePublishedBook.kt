package org.practice.micronaut.bookshelf.domain.model.book

import arrow.core.Either
import arrow.core.Option
import arrow.core.extensions.fx
import arrow.core.fix
import org.practice.micronaut.bookshelf.domain.lib.DomainErr
import org.practice.micronaut.bookshelf.domain.model.Entity
import org.practice.micronaut.bookshelf.domain.model.Id
import org.practice.micronaut.bookshelf.domain.type.BookName
import org.practice.micronaut.bookshelf.domain.type.PublicationDate
import java.time.LocalDate
import java.util.*

/**
 * まだ出版されていない本
 * BookName未確定
 */
data class PrePublishedBook private constructor(
        override val id: Id<PrePublishedBook>,
        val bookName: Option<BookName>,
        val publicationDate: PublicationDate
) : Entity<PrePublishedBook> {
    companion object {
        operator fun invoke(rawBookName: String, rawPublicationDate: LocalDate, currentDate: LocalDate): Either<DomainErr, PrePublishedBook> {
            return Option.fx {
                val id = Id<PrePublishedBook>(UUID.randomUUID())
                val bookName = BookName(rawBookName)
                val publicationDate = PublicationDate(rawPublicationDate, currentDate).bind()
                PrePublishedBook(id, bookName, publicationDate)
            }.fix().toEither {
                DomainErr.ValidationErr
            }
        }
    }
}