package org.practice.micronaut.bookshelf.presentation.book

import arrow.core.flatMap
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import org.practice.micronaut.bookshelf.application.book.BookCommandService
import org.practice.micronaut.bookshelf.application.book.BookQueryService
import org.practice.micronaut.bookshelf.domain.repository.BookUpdateCommand
import org.practice.micronaut.bookshelf.presentation.createErrorResponse
import org.practice.micronaut.bookshelf.presentation.httpResponseCreator
import org.practice.micronaut.bookshelf.presentation.localDateValidator
import org.practice.micronaut.bookshelf.presentation.uuidValidator
import org.practice.micronaut.bookshelf.util.GlobalError
import org.practice.micronaut.bookshelf.util.anyLoggerFactory
import org.practice.micronaut.bookshelf.util.tap
import java.time.LocalDate
import javax.inject.Inject


@Controller("/books")
class BookController @Inject
constructor(private val bookQueryService: BookQueryService,
            private val bookCommandService: BookCommandService
) {
    private val logger by lazy { anyLoggerFactory<BookController>() }

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
                            createErrorResponse(it)
                        },
                        {
                            HttpResponse.ok(BookResponse.fromDTO(it))
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
                            createErrorResponse(it)
                        },
                        {
                            httpResponseCreator(HttpStatus.NO_CONTENT)
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
                            createErrorResponse(it)
                        },
                        {
                            httpResponseCreator(HttpStatus.NO_CONTENT)
                        }
                )

    }
}