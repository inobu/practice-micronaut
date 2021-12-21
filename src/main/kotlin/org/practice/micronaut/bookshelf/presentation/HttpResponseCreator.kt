package org.practice.micronaut.bookshelf.presentation

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus

fun httpResponseCreator(httpStatus: HttpStatus): HttpResponse<ResponseBodyJson> {
  return HttpResponse.status<ResponseBodyJson>(httpStatus)
      .body(ResponseBodyJson.fromHttpStatus(httpStatus))
}
