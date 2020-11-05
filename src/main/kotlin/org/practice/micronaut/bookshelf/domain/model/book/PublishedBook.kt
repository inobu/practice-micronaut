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
 * すでに出版された本
 * bookName確定済み
 */
data class PublishedBook private constructor(val bookName: BookName, val publicationDate: PublicationDate) : Entity {
    companion object {
        operator fun invoke(rawBookName: String, rawPublicationDate: LocalDate, currentDate: LocalDate): Either<DomainErr, PublishedBook> {
            return Option.fx {
                val bookName = BookName(rawBookName).bind()
                val publicationDate = PublicationDate(rawPublicationDate, currentDate).bind()
                PublishedBook(bookName, publicationDate)
            }.fix().toEither {
                DomainErr.ValidationErr
            }
        }
    }
}