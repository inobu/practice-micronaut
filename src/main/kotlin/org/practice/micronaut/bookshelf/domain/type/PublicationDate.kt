package org.practice.micronaut.bookshelf.domain.type

import java.time.LocalDate

import arrow.core.Option
import arrow.core.Some

data class PublicationDate private constructor(val value: LocalDate) {
    companion object  {
        operator fun invoke(value: LocalDate): Option<PublicationDate> {
            return Some(value).map { PublicationDate(it) }
        }

        //エラーハンドルそのうち追加する
    }
}