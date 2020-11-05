package org.practice.micronaut.bookshelf.application

interface BookQuery {
    fun findBook(): BookDTO
}