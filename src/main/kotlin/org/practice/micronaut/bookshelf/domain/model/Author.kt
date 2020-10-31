package org.practice.micronaut.bookshelf.domain.model

import org.practice.micronaut.bookshelf.domain.type.AuthorName

data class Author(val authorName: AuthorName) : Entity {
}