package org.practice.micronaut.bookshelf.domain.type

import arrow.core.Option
import arrow.core.toOption
import java.time.LocalDate

data class PrePublicationDate private constructor(override val value: LocalDate) :
    Value<LocalDate> {
  companion object {
    operator fun invoke(value: LocalDate?, compareDate: LocalDate): Option<PrePublicationDate> {
      return value.toOption().filter { validPrePublicationDate(it, compareDate) }.map {
        PrePublicationDate(it)
      }
    }

    private fun validPrePublicationDate(value: LocalDate, compareDate: LocalDate): Boolean {
      return value.isAfter(compareDate)
    }
  }
}
