package org.practice.micronaut.bookshelf.presentation

sealed class PresentationErr {
    object PresentationNullErr: PresentationErr()
}