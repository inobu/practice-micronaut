package org.practice.micronaut.bookshelf.domain.repository

import org.practice.micronaut.bookshelf.domain.model.author.Author

interface AuthorRepository {
    fun saveAuthor(author: Author)
}