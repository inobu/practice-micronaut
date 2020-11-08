package org.practice.micronaut.bookshelf.util

sealed class GlobalError {
    abstract val value: String
    override fun toString(): String {
        return value
    }

    object DomainError : GlobalError() {
        override val value: String by lazy { "DomainError" }
    }

    object PresentationNullError : GlobalError() {
        override val value: String by lazy { "PresentationNullError" }
    }

    object PresentationInvalidUUIDError : GlobalError() {
        override val value: String by lazy { "PresentationInvalidUUIDError" }
    }

    object DatabaseConflictsError : GlobalError() {
        override val value: String by lazy { " DatabaseConflictsError" }
    }

    object NotFoundError : GlobalError() {
        override val value: String by lazy { " NotFoundError" }
    }
}