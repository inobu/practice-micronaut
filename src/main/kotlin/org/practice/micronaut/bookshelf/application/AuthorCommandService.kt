package org.practice.micronaut.bookshelf.application

import arrow.core.Either
import arrow.core.flatMap
import org.practice.micronaut.bookshelf.domain.model.author.Author
import org.practice.micronaut.bookshelf.domain.repository.AuthorRepository
import org.practice.micronaut.bookshelf.util.GlobalError
import org.practice.micronaut.bookshelf.util.tap
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class AuthorCommandService(private val authorRepository: AuthorRepository) {
    private val logger = LoggerFactory.getLogger(AuthorCommandService::class.java)

    fun command(authorName: String?): Either<GlobalError, Int> {
        return Author(authorName)
                .tap(leftSideEffect = { logger.info("invalid authorName") })
                .flatMap { authorRepository.saveAuthor(it) }
                .tap(leftSideEffect = { logger.info("conflict authorName $authorName") })
    }
}