package org.practice.micronaut.bookshelf.domain.type

import arrow.core.Option
import arrow.core.toOption

data class AuthorName private constructor(override val value: String) : Value<String> {
  companion object {
    private const val maxLength = 100
    private const val minLength = 1

    operator fun invoke(value: String?): Option<AuthorName> {
      return value.toOption().filter { validAuthorName(it) }.map { AuthorName(it) }
    }

    private fun validAuthorName(value: String): Boolean {
      return value.length in (minLength + 1) until maxLength
    }
  }
}
