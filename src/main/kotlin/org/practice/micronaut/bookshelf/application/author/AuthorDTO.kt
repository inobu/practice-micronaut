package org.practice.micronaut.bookshelf.application

import org.practice.micronaut.bookshelf.domain.type.AuthorName


data class AuthorDTO(val id: String, val authorName: AuthorName)