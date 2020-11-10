package org.practice.micronaut.bookshelf.presentation

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import org.practice.micronaut.bookshelf.util.GlobalError

fun <T> createErrorResponse(globalError: GlobalError): HttpResponse<*> {
    return when (globalError) {
        is GlobalError.DomainError, GlobalError.PresentationInvalidUUIDError, GlobalError.PresentationNullError -> HttpResponse.badRequest(ResponseBodyJson.fromHttpStatus(HttpStatus.BAD_REQUEST))
        is GlobalError.NotFoundError -> HttpResponse.notFound(ResponseBodyJson.fromHttpStatus(HttpStatus.BAD_REQUEST))
        is GlobalError.DatabaseConflictsError -> HttpResponse.status<ResponseBodyJson>(HttpStatus.CONFLICT).body(ResponseBodyJson.fromHttpStatus(HttpStatus.CONFLICT))
        else -> HttpResponse.serverError<T>()
    }
}