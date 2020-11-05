package org.practice.micronaut.bookshelf.infrastracture

import org.jooq.DSLContext
import org.practice.micronaut.bookshelf.application.BookDTO
import org.practice.micronaut.bookshelf.application.BookQuery

class BookQueryImpl(private val dSLContext: DSLContext): BookQuery {
    override fun findBook(): BookDTO {
        dSLContext.select().from()
        TODO("Not yet implemented")
    }
}