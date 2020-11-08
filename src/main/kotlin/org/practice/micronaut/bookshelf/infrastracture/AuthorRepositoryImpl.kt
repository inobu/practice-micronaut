package org.practice.micronaut.bookshelf.infrastracture

import nu.studer.sample.tables.Authors
import org.jooq.DSLContext
import org.practice.micronaut.bookshelf.domain.lib.uuidToBytes
import org.practice.micronaut.bookshelf.domain.model.author.Author
import org.practice.micronaut.bookshelf.domain.repository.AuthorRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorRepositoryImpl @Inject constructor(private val dslContext: DSLContext) : AuthorRepository {
    override fun saveAuthor(author: Author) {
        dslContext.insertInto(Authors.AUTHORS)
                .columns(Authors.AUTHORS.ID, Authors.AUTHORS.AUTHOR_NAME)
                .values(author.id.value.uuidToBytes(), author.authorName.value)
                .execute()
    }
}