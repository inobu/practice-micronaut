package org.practice.micronaut.bookshelf.presentation

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import org.practice.micronaut.bookshelf.util.GlobalError

fun createErrorResponse(globalError: GlobalError): HttpResponse<*> {
  return when (globalError) {
    is GlobalError.DomainError,
    GlobalError.PresentationInvalidUUIDError,
    GlobalError.PresentationNullError -> httpResponseCreator(HttpStatus.BAD_REQUEST)
    is GlobalError.NotFoundError -> httpResponseCreator(HttpStatus.NOT_FOUND)
    is GlobalError.DatabaseConflictsError -> httpResponseCreator(HttpStatus.CONFLICT)
    else -> httpResponseCreator(HttpStatus.INTERNAL_SERVER_ERROR)
  }
}
