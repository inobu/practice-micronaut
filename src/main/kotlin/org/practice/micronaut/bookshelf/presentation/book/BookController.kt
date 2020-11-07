package org.practice.micronaut.bookshelf.presentation.book

import arrow.core.flatMap
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import org.practice.micronaut.bookshelf.application.BookQueryService
import org.practice.micronaut.bookshelf.presentation.uuidValidator
import org.practice.micronaut.bookshelf.util.tap
import org.slf4j.LoggerFactory
import javax.inject.Inject


@Controller("/books")
class BookController @Inject
constructor(private val bookQueryService: BookQueryService) {
    private val logger = LoggerFactory.getLogger(BookController::class.java)

    @Get(uri = "/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun getBook(@PathVariable("id") id: String?): HttpResponse<*> {

        return id.uuidValidator()
                .tap(leftSideEffect = { logger.info("invalid id $id cause ${it::class.java.name}") })
                .mapLeft { it.toString() }
                .flatMap { bookQueryService.findBook(it).toEither { "No Content $it" } }
                .fold(
                        {
                            HttpResponse.notFound<String>().body(it)
                        },
                        {
                            HttpResponse.ok(BookResponse(it.id, it.bookName.value, it.publicationDate.value, it.bookName.value))
                        })
    }
}