package org.practice.micronaut.bookshelf.presentation

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import org.practice.micronaut.bookshelf.application.BookQueryService
import org.practice.micronaut.bookshelf.util.tap
import org.slf4j.LoggerFactory
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Named


@Controller("/books")
class BookController @Inject
constructor(@Named("query") private val bookQueryService: BookQueryService) {
    private val logger = LoggerFactory.getLogger(BookController::class.java)

    @Get(uri = "/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun getBook(@PathVariable("id") id: String?): HttpResponse<BookResponse> {

        val a = id.uuidMatcher()
                .tap(leftSideEffect = { logger.info("invalid id $it") })
                .map { bookQueryService.findBook(it) }

        return HttpResponse.ok(BookResponse("", "", LocalDate.now(), "name"))
    }
}