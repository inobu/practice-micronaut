package org.practice.micronaut.bookshelf.presentation

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.practice.micronaut.bookshelf.util.GlobalError
import java.time.LocalDate

fun LocalDate?.localDateValidator() : Either<GlobalError, LocalDate> {
    return this?.right() ?: GlobalError.PresentationNullError.left()
}