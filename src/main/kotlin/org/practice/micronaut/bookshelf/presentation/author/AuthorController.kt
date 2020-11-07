package org.practice.micronaut.bookshelf.presentation.author

import arrow.core.flatMap
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import org.practice.micronaut.bookshelf.application.AuthorQueryService
import org.practice.micronaut.bookshelf.presentation.uuidValidator
import org.practice.micronaut.bookshelf.util.tap
import org.slf4j.LoggerFactory
import javax.inject.Inject

@Controller("/author")
class AuthorController @Inject constructor(private val authorQueryService: AuthorQueryService) {
    private val logger = LoggerFactory.getLogger(AuthorController::class.java)

    @Get(uri = "/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun get(@PathVariable("id") id: String?): HttpResponse<*> {
        return id.uuidValidator()
                .tap(leftSideEffect = { logger.info("invalid id $id cause ${it::class.java.name}") })
                .mapLeft { it.toString() }
                .flatMap { authorQueryService.findAuthor(it).toEither { "No Content $it" } }
                .fold(
                        {
                            HttpResponse.notFound<String>().body(it)
                        },
                        {
                            HttpResponse.ok(AuthorResponse(it.id, it.authorName.value))
                        }
                )
    }
}