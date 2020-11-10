package org.practice.micronaut.bookshelf.infrastracture

import arrow.core.None
import arrow.core.Option
import arrow.core.extensions.fx
import nu.studer.sample.tables.Authors
import org.jooq.DSLContext
import org.practice.micronaut.bookshelf.application.author.AuthorDTO
import org.practice.micronaut.bookshelf.application.author.AuthorQueryService
import org.practice.micronaut.bookshelf.domain.lib.toBytes
import org.practice.micronaut.bookshelf.domain.lib.toUUID
import org.practice.micronaut.bookshelf.domain.type.AuthorName
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorQueryServiceImpl @Inject constructor(private val dslContext: DSLContext) : AuthorQueryService {
    override fun findAuthor(id: UUID): Option<AuthorDTO> {
        return dslContext
                .select()
                .from(Authors.AUTHORS)
                .where(Authors.AUTHORS.ID.eq(id.toBytes()))
                .fetchOneInto(nu.studer.sample.tables.pojos.Authors::class.java)
                ?.run {
                    val author = this
                    Option.fx {
                        AuthorDTO(
                                author.id.toUUID().toString(),
                                AuthorName(author.authorName).bind()
                        )
                    }
                } ?: None
    }
}