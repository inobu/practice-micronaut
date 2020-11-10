package org.practice.micronaut.bookshelf.domain.model.book

import arrow.core.Either
import arrow.core.Option
import arrow.core.extensions.fx
import arrow.core.fix
import org.practice.micronaut.bookshelf.domain.model.Entity
import org.practice.micronaut.bookshelf.domain.model.Id
import org.practice.micronaut.bookshelf.domain.type.BookName
import org.practice.micronaut.bookshelf.domain.type.PrePublicationDate
import org.practice.micronaut.bookshelf.util.GlobalError
import java.time.LocalDate
import java.util.*

/**
 * まだ出版されていない本
 * BookName未確定
 */
data class PrePublishedBook private constructor(
        override val id: Id<PrePublishedBook>,
        val bookName: Option<BookName>,
        val publicationDate: PrePublicationDate
) : Entity<PrePublishedBook> {
    companion object {
        operator fun invoke(rawBookName: String?, rawPublicationDate: LocalDate?, compareDate: LocalDate): Either<GlobalError, PrePublishedBook> {
            return Option.fx {
                val id = Id<PrePublishedBook>(UUID.randomUUID())
                val bookName = BookName(rawBookName)
                val publicationDate = PrePublicationDate(rawPublicationDate, compareDate).bind()
                PrePublishedBook(id, bookName, publicationDate)
            }.fix().toEither {
                GlobalError.DomainError
            }
        }

        operator fun invoke(rawId: UUID, rawBookName: String?, rawPublicationDate: LocalDate?, compareDate: LocalDate): Either<GlobalError, PrePublishedBook> {
            return Option.fx {
                val id = Id<PrePublishedBook>(rawId)
                val bookName = BookName(rawBookName)
                val publicationDate = PrePublicationDate(rawPublicationDate, compareDate).bind()
                PrePublishedBook(id, bookName, publicationDate)
            }.fix().toEither {
                GlobalError.DomainError
            }
        }
    }
}