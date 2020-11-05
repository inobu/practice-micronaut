package org.practice.micronaut.bookshelf.domain.model.author

import org.practice.micronaut.bookshelf.domain.model.Entity
import org.practice.micronaut.bookshelf.domain.type.AuthorName

data class Author(val authorName: AuthorName) : Entity {
}