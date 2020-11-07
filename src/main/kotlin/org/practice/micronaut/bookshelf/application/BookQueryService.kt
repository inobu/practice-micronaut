package org.practice.micronaut.bookshelf.application

import arrow.core.Option
import java.util.*

interface BookQueryService {
    fun findBook(id: UUID): Option<BookDTO>
}