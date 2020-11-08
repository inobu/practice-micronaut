package org.practice.micronaut.bookshelf.domain.model.author

import arrow.core.Either
import arrow.core.Option
import arrow.core.extensions.fx
import arrow.core.fix
import org.jetbrains.annotations.TestOnly
import org.practice.micronaut.bookshelf.domain.lib.DomainErr
import org.practice.micronaut.bookshelf.domain.model.Entity
import org.practice.micronaut.bookshelf.domain.model.Id
import org.practice.micronaut.bookshelf.domain.type.AuthorName
import java.util.*

data class Author private constructor(override val id: Id<Author>, val authorName: AuthorName) : Entity<Author> {
    companion object {
        operator fun invoke(rawName: String): Either<DomainErr, Author> {
            return Option.fx {
                val id = Id<Author>(UUID.randomUUID())
                val authorName = AuthorName(rawName).bind()
                Author(id, authorName)
            }.fix().toEither {
                DomainErr.ValidationErr
            }
        }
    }

    @TestOnly
    constructor(id: String, authorName: String): this(
            Id.fromString<Author>(id),
            AuthorName(authorName).orNull() ?: throw IllegalArgumentException("テスト用データぐらいまともに入れろ")
    )
}