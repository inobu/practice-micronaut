package org.practice.micronaut.bookshelf.presentation

import arrow.core.rightIfNotNull
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import org.practice.micronaut.bookshelf.application.BookQueryService
import java.time.LocalDate
import java.util.*
import javax.inject.Inject
import javax.inject.Named


@Controller("/books")
class BookController @Inject
constructor(@Named("query")  private val bookQueryService: BookQueryService) {

    @Get(uri = "/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun getBook(@PathVariable("id") id: UUID?): HttpResponse<BookResponse> {
//        val a = id.rightIfNotNull { PresentationErr.PresentationNullErr }


        return HttpResponse.ok(BookResponse("", "", LocalDate.now()))
    }
}