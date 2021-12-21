package org.practice.micronaut.bookshelf.domain.type

import arrow.core.Option
import arrow.core.toOption
import java.time.LocalDate

data class PublicationDate private constructor(override val value: LocalDate) : Value<LocalDate> {
  companion object {
    operator fun invoke(value: LocalDate?, compareDate: LocalDate): Option<PublicationDate> {
      return value.toOption().filter { validPublicationDate(it, compareDate) }.map {
        PublicationDate(it)
      }
    }

    private fun validPublicationDate(value: LocalDate, compareDate: LocalDate): Boolean {
      return value.isBefore(compareDate)
    }
  }
}
