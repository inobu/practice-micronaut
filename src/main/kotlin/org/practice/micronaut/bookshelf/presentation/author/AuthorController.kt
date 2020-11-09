package org.practice.micronaut.bookshelf.presentation.author

import arrow.core.flatMap
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import org.practice.micronaut.bookshelf.application.AuthorCommandService
import org.practice.micronaut.bookshelf.application.AuthorQueryService
import org.practice.micronaut.bookshelf.presentation.ResponseBodyJson
import org.practice.micronaut.bookshelf.presentation.uuidValidator
import org.practice.micronaut.bookshelf.util.GlobalError
import org.practice.micronaut.bookshelf.util.tap
import org.slf4j.LoggerFactory
import javax.inject.Inject

@Controller("/author")
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
                            when (it) {
                                is GlobalError.DomainError, GlobalError.PresentationInvalidUUIDError -> HttpResponse.badRequest(ResponseBodyJson.fromHttpStatus(HttpStatus.BAD_REQUEST))
                                is GlobalError.NotFoundError -> HttpResponse.notFound(ResponseBodyJson.fromHttpStatus(HttpStatus.BAD_REQUEST))
                                is GlobalError.DatabaseConflictsError -> HttpResponse.status<ResponseBodyJson>(HttpStatus.CONFLICT).body(ResponseBodyJson.fromHttpStatus(HttpStatus.CONFLICT))
                                else -> HttpResponse.serverError()
                            }
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
        return authorCommandService.command(authorName).fold(
                {
                    when (it) {
                        is GlobalError.DomainError -> HttpResponse.badRequest(ResponseBodyJson.fromHttpStatus(HttpStatus.BAD_REQUEST))
                        is GlobalError.DatabaseConflictsError -> HttpResponse.status<ResponseBodyJson>(HttpStatus.CONFLICT).body(ResponseBodyJson.fromHttpStatus(HttpStatus.CONFLICT))
                        else -> HttpResponse.serverError()
                    }
                },
                {
                    HttpResponse.created(ResponseBodyJson.fromHttpStatus(HttpStatus.CREATED))
                }
        )
    }

    @Put(uri = "/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun put(@PathVariable("id") id: String?, @Body("authorName") authorName: String?): HttpResponse<*> {
        return id.uuidValidator()
                .tap(leftSideEffect = { logger.info("invalid id $id cause $it") })
                .flatMap { authorCommandService.update(it, authorName) }
                .fold(
                        {
                            when (it) {
                                is GlobalError.DomainError, GlobalError.PresentationInvalidUUIDError -> HttpResponse.badRequest(ResponseBodyJson.fromHttpStatus(HttpStatus.BAD_REQUEST))
                                is GlobalError.NotFoundError -> HttpResponse.notFound(ResponseBodyJson.fromHttpStatus(HttpStatus.NOT_FOUND))
                                is GlobalError.DatabaseConflictsError -> HttpResponse.status<ResponseBodyJson>(HttpStatus.CONFLICT).body(ResponseBodyJson.fromHttpStatus(HttpStatus.CONFLICT))
                                else -> HttpResponse.serverError()
                            }
                        },
                        {
                            HttpResponse.noContent()
                        }
                )
    }
}