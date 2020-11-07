package org.practice.micronaut.bookshelf.presentation

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import java.util.*

private val uuidRegex = "[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}".toRegex()

fun String?.uuidValidator(): Either<PresentationErr, UUID> {
    return if (this.isNullOrEmpty()) {
        PresentationErr.PresentationNullErr.left()
    } else if(!this.matches(uuidRegex)) {
        PresentationErr.PresentationInvalidUUIDErr.left()
    } else {
        UUID.fromString(this).right()
    }
}