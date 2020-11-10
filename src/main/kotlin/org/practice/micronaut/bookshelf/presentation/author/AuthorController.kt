package org.practice.micronaut.bookshelf.presentation.author

import arrow.core.flatMap
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import org.practice.micronaut.bookshelf.application.author.AuthorCommandService
import org.practice.micronaut.bookshelf.application.author.AuthorQueryService
import org.practice.micronaut.bookshelf.presentation.createErrorResponse
import org.practice.micronaut.bookshelf.presentation.httpResponseCreator
import org.practice.micronaut.bookshelf.presentation.uuidValidator
import org.practice.micronaut.bookshelf.util.GlobalError
import org.practice.micronaut.bookshelf.util.tap
import org.slf4j.LoggerFactory
import java.time.LocalDate
import javax.inject.Inject

@Controller("/authors")
class AuthorController @Inject constructor(
        private val authorQueryService: AuthorQueryService,
        private val authorCommandService: AuthorCommandService
) {
    private val logger = LoggerFactory.getLogger(AuthorController::class.java)

    @Get(uri = "/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun get(@PathVariable("id") id: String?): HttpResponse<*> {
        return id.uuidValidator()
                .tap(leftSideEffect = { logger.info("invalid id $id cause $it") })
                .flatMap { authorQueryService.findAuthor(it).toEither { GlobalError.NotFoundError } }
                .tap(leftSideEffect = { logger.info("notFoundResource $id cause $it") })
                .fold(
                        {
                            createErrorResponse(it)
                        },
                        {
                            HttpResponse.ok(AuthorResponse(it.id, it.authorName.value))
                        }
                )
    }

    @Post
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun post(@Body("authorName") authorName: String?): HttpResponse<*> {
        return authorCommandService.commandAuthor(authorName).fold(
                {
                    createErrorResponse(it)
                },
                {
                    httpResponseCreator(HttpStatus.CREATED)
                }
        )
    }

    @Put(uri = "/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun put(@PathVariable("id") id: String?, @Body("authorName") authorName: String?): HttpResponse<*> {
        return id.uuidValidator()
                .tap(leftSideEffect = { logger.info("invalid id $id cause $it") })
                .flatMap { authorCommandService.updateAuthor(it, authorName) }
                .fold(
                        {
                            createErrorResponse(it)
                        },
                        {
                            httpResponseCreator(HttpStatus.NO_CONTENT)
                        }
                )
    }


    @Post("/{id}/books")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun createBook(@PathVariable("id") id: String?,
                   @Body("publicationDate") publicationDate: LocalDate?,
                   @Body("bookName") bookName: String? = null): HttpResponse<*> {
        return id.uuidValidator()
                .flatMap { authorCommandService.createPrePublishedBook(it, bookName, publicationDate) }
                .fold(
                        {
                            createErrorResponse(it)
                        },
                        {
                            httpResponseCreator(HttpStatus.CREATED)
                        }
                )
    }
}