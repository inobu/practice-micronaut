package org.practice.micronaut.bookshelf.domain.model

import arrow.core.Either
import arrow.core.Option
import arrow.core.extensions.fx
import arrow.core.fix
import org.practice.micronaut.bookshelf.domain.lib.DomainErr
import org.practice.micronaut.bookshelf.domain.type.BookName
import org.practice.micronaut.bookshelf.domain.type.PublicationDate
import java.time.LocalDate

data class Book private constructor(val bookName: BookName, val publicationDate: PublicationDate) : Entity {
    companion object {
        operator fun invoke(rawBookName: String, rawPublicationDate: LocalDate, currentDate: LocalDate): Either<DomainErr, Book> {
            return Option.fx {
                val bookName = BookName(rawBookName).bind()
                val publicationDate = PublicationDate(rawPublicationDate, currentDate).bind()
                Book(bookName, publicationDate)
            }.fix().toEither {
                DomainErr.ValidationErr
            }
        }
    }
}