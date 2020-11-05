package org.practice.micronaut.bookshelf.domain.model.book

import arrow.core.Either
import arrow.core.Option
import arrow.core.extensions.fx
import arrow.core.fix
import org.practice.micronaut.bookshelf.domain.lib.DomainErr
import org.practice.micronaut.bookshelf.domain.model.Entity
import org.practice.micronaut.bookshelf.domain.type.BookName
import org.practice.micronaut.bookshelf.domain.type.PublicationDate
import java.time.LocalDate

/**
 * まだ出版されていない本
 * BookName未確定
 */
data class PrePublishedBook private constructor(
        val bookName: Option<BookName>, val publicationDate: PublicationDate) : Entity {
    companion object {
        operator fun invoke(rawBookName: String, rawPublicationDate: LocalDate, currentDate: LocalDate): Either<DomainErr, PrePublishedBook> {
            return Option.fx {
                val bookName = BookName(rawBookName)
                val publicationDate = PublicationDate(rawPublicationDate, currentDate).bind()
                PrePublishedBook(bookName, publicationDate)
            }.fix().toEither {
                DomainErr.ValidationErr
            }
        }
    }
}