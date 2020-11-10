package org.practice.micronaut.bookshelf.infrastracture

import arrow.core.*
import nu.studer.sample.tables.Authors
import org.jooq.DSLContext
import org.jooq.exception.DataAccessException
import org.practice.micronaut.bookshelf.domain.lib.toBytes
import org.practice.micronaut.bookshelf.domain.lib.toUUID
import org.practice.micronaut.bookshelf.domain.model.EntityId
import org.practice.micronaut.bookshelf.domain.model.author.Author
import org.practice.micronaut.bookshelf.domain.repository.AuthorRepository
import org.practice.micronaut.bookshelf.util.GlobalError
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorRepositoryImpl @Inject constructor(private val dslContext: DSLContext) : AuthorRepository {
    override fun saveAuthor(author: Author): Either<GlobalError.DatabaseConflictsError, Unit> {
        return dslContext.insertInto(Authors.AUTHORS)
                .columns(Authors.AUTHORS.ID, Authors.AUTHORS.AUTHOR_NAME)
                .values(author.id.value.toBytes(), author.authorName.value)
                .runCatching { execute() }
                .fold(
                        onSuccess = {
                            {}().right()
                        },
                        onFailure = {
                            GlobalError.DatabaseConflictsError.left()
                        }
                )
    }

    override fun updateAuthor(author: Author): Either<GlobalError, Unit> {
        return dslContext.fetchOne(Authors.AUTHORS, Authors.AUTHORS.ID.eq(author.id.value.toBytes()))
                .rightIfNotNull { GlobalError.NotFoundError }
                .flatMap {
                    it.values(author.id.value.toBytes(), author.authorName.value)
                            .runCatching { store() }
                            .fold(
                                    onSuccess = {
                                        {}().right()
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

    override fun findById(id: EntityId<Author>): Either<GlobalError, Author> {
        return dslContext.select().from(Authors.AUTHORS)
                .where(Authors.AUTHORS.ID.eq(id.value.toBytes()))
                .fetchOneInto(nu.studer.sample.tables.pojos.Authors::class.java)
                ?.run {
                    val author = this
                    Author(
                            author.id.toUUID(),
                            author.authorName
                    )
                } ?: GlobalError.NotFoundError.left()
    }
}