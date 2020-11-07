package org.practice.micronaut.bookshelf.domain.type

import arrow.core.*

data class BookName private constructor(override val value: String) : Value<String> {
    companion object  {
        private const val minLength = 1
        private const val maxLength = 100

        operator fun invoke(value: String?): Option<BookName> {
            return value.toOption().filter { validBookName(it) }.map { BookName(it) }
        }

        private fun validBookName(value: String): Boolean {
            return value.length in (minLength + 1) until maxLength
        }
    }
}