package org.practice.micronaut.bookshelf.presentation.book

import arrow.core.flatMap
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import org.practice.micronaut.bookshelf.application.book.BookCommandService
import org.practice.micronaut.bookshelf.application.book.BookQueryService
import org.practice.micronaut.bookshelf.domain.repository.BookUpdateCommand
import org.practice.micronaut.bookshelf.presentation.ResponseBodyJson
import org.practice.micronaut.bookshelf.presentation.localDateValidator
import org.practice.micronaut.bookshelf.presentation.uuidValidator
import org.practice.micronaut.bookshelf.util.GlobalError
import org.practice.micronaut.bookshelf.util.tap
import org.slf4j.LoggerFactory
import java.time.LocalDate
import javax.inject.Inject


@Controller("/books")
class BookController @Inject
constructor(private val bookQueryService: BookQueryService,
            private val bookCommandService: BookCommandService
) {
    private val logger = LoggerFactory.getLogger(BookController::class.java)

    @Get(uri = "/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun getBook(@PathVariable("id") id: String?): HttpResponse<*> {
        return id.uuidValidator()
                .tap(leftSideEffect = { logger.info("invalid id $id cause $it") })
                .flatMap { bookQueryService.findBook(it).toEither { GlobalError.NotFoundError } }
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
                            HttpResponse.ok(BookResponse(it.id, it.bookName.value, it.publicationDate.value, it.authorName.value))
                        }
                )
    }

    @Put(uri = "/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun updateBook(@PathVariable("id") id: String?,
                   @Body("publicationDate") publicationDate: LocalDate?,
                   @Body("bookName") bookName: String? = null): HttpResponse<*> {
        return id.uuidValidator()
                .flatMap { uuid -> publicationDate.localDateValidator().map { Pair(uuid, it) } }
                .flatMap { bookCommandService.editBook(BookUpdateCommand(it.first, bookName, it.second)) }
                .fold(
                        {
                            when (it) {
                                is GlobalError.DomainError, GlobalError.PresentationInvalidUUIDError, GlobalError.PresentationNullError -> HttpResponse.badRequest(ResponseBodyJson.fromHttpStatus(HttpStatus.BAD_REQUEST))
                                is GlobalError.NotFoundError -> HttpResponse.notFound(ResponseBodyJson.fromHttpStatus(HttpStatus.BAD_REQUEST))
                                is GlobalError.DatabaseConflictsError -> HttpResponse.status<ResponseBodyJson>(HttpStatus.CONFLICT).body(ResponseBodyJson.fromHttpStatus(HttpStatus.CONFLICT))
                                else -> HttpResponse.serverError()
                            }
                        },
                        {
                            HttpResponse.noContent()
                        }
                )
    }

    @Delete(uri = "/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun deleteBook(@PathVariable("id") id: String?): HttpResponse<*> {
        return id.uuidValidator()
                .flatMap { bookCommandService.deleteBook(it) }
                .fold(
                        {
                            when (it) {
                                is GlobalError.DomainError, GlobalError.PresentationInvalidUUIDError, GlobalError.PresentationNullError -> HttpResponse.badRequest(ResponseBodyJson.fromHttpStatus(HttpStatus.BAD_REQUEST))
                                is GlobalError.NotFoundError -> HttpResponse.notFound(ResponseBodyJson.fromHttpStatus(HttpStatus.BAD_REQUEST))
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