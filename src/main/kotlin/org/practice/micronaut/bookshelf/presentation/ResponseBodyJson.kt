package org.practice.micronaut.bookshelf.presentation

import io.micronaut.http.HttpStatus

data class ResponseBodyJson(val code: Int, val reason: String) {
  companion object {
    fun fromHttpStatus(httpStatus: HttpStatus): ResponseBodyJson {
      return ResponseBodyJson(httpStatus.code, httpStatus.reason)
    }
  }
}
