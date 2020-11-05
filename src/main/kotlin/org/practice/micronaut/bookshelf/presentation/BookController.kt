package org.practice.micronaut.bookshelf.presentation

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces


@Controller("/books")
class BookController {

    @Get(uri = "/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun getBook(): HttpResponse<BookResponse> =
            HttpResponse.ok(BookResponse("book"))
}