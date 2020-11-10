package org.practice.micronaut.bookshelf.domain.repository


import java.time.LocalDate
import java.util.*

data class BookUpdateCommand(val id: UUID, val bookName: String?, val publicationDate: LocalDate)