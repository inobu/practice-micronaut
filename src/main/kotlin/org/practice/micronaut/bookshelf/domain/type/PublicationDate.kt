package org.practice.micronaut.bookshelf.domain.type

import arrow.core.Option
import arrow.core.toOption
import java.time.LocalDate

data class PublicationDate private constructor(override val value: LocalDate) : Value<LocalDate> {
    companion object {
        operator fun invoke(value: LocalDate?, currentDate: LocalDate): Option<PublicationDate> {
            return value.toOption().filter { validPublicationDate(it, currentDate) }.map { PublicationDate(it) }
        }

        //エラーハンドルそのうち追加する
        private fun validPublicationDate(value: LocalDate, currentDate: LocalDate): Boolean {
            return value.isBefore(currentDate)
        }
    }
}