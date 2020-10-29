package org.practice.micronaut.bookshelf.domain.model

import org.practice.micronaut.bookshelf.domain.type.BookName
import java.util.*

data class Book(val bookName: BookName, val publicationDate: Date) {
}