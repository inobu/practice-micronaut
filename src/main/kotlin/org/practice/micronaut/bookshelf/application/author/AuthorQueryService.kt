package org.practice.micronaut.bookshelf.application

import arrow.core.Option
import java.util.*


interface AuthorQueryService {
    fun findAuthor(id: UUID): Option<AuthorDTO>
}