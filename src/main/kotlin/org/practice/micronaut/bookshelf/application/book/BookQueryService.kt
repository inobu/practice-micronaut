package org.practice.micronaut.bookshelf.application.book

import arrow.core.Option
import java.util.*

interface BookQueryService {
  fun findBook(id: UUID): Option<BookDTO>
}
