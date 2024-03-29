package org.practice.micronaut.bookshelf.domain.model.book

import arrow.core.Either
import arrow.core.Option
import arrow.core.extensions.fx
import arrow.core.fix
import java.time.LocalDate
import java.util.*
import org.practice.micronaut.bookshelf.domain.model.Entity
import org.practice.micronaut.bookshelf.domain.model.Id
import org.practice.micronaut.bookshelf.domain.type.BookName
import org.practice.micronaut.bookshelf.domain.type.PublicationDate
import org.practice.micronaut.bookshelf.util.GlobalError

/** すでに出版された本 bookName確定済み */
data class PublishedBook
private constructor(
    override val id: Id<PublishedBook>,
    val bookName: BookName,
    val publicationDate: PublicationDate,
) : Entity<PublishedBook> {
  companion object {
    operator fun invoke(
        rawBookName: String,
        rawPublicationDate: LocalDate,
        compareDate: LocalDate
    ): Either<GlobalError, PublishedBook> {
      return Option.fx {
        val id = Id<PublishedBook>(UUID.randomUUID())
        val bookName = BookName(rawBookName).bind()
        val publicationDate = PublicationDate(rawPublicationDate, compareDate).bind()
        PublishedBook(id, bookName, publicationDate)
      }
          .fix()
          .toEither { GlobalError.DomainError }
    }
  }
}
