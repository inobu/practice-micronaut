package org.practice.micronaut.bookshelf.domain.repository

import arrow.core.Either
import org.practice.micronaut.bookshelf.domain.model.author.Author
import org.practice.micronaut.bookshelf.util.GlobalError

interface AuthorRepository {
    fun saveAuthor(author: Author): Either<GlobalError.DatabaseConflictsError, Unit>
}