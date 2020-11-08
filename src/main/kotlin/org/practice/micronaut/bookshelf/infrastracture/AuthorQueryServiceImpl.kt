package org.practice.micronaut.bookshelf.infrastracture

import arrow.core.Option
import arrow.core.extensions.fx
import arrow.core.fix
import nu.studer.sample.tables.Authors
import org.jooq.DSLContext
import org.practice.micronaut.bookshelf.application.AuthorDTO
import org.practice.micronaut.bookshelf.application.AuthorQueryService
import org.practice.micronaut.bookshelf.domain.lib.bytesToUuid
import org.practice.micronaut.bookshelf.domain.lib.uuidToBytes
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
                .where(Authors.AUTHORS.ID.eq(id.uuidToBytes()))
                .fetchOneInto(nu.studer.sample.tables.pojos.Authors::class.java)
                .run {
                    val author = this
                    Option.fx {
                        AuthorDTO(
                                bytesToUuid(author?.id).toString(),
                                AuthorName(author?.authorName).bind()
                        )
                    }.fix()
                }
    }
}