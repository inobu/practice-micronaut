package org.practice.micronaut.bookshelf.domain.type

import arrow.core.None
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import org.practice.micronaut.bookshelf.domain.TestDataFactory.invalidLengthName
import org.practice.micronaut.bookshelf.domain.TestDataFactory.validBookName

@MicronautTest
class BookNameTest {
    @Test
    fun bookNameInvalidMaxLengthTest() {
        assert(BookName(invalidLengthName) == None)
    }

    @Test
    fun bookNameInvalidMinLengthTest() {
        assert(BookName("") == None)
    }

    @Test
    fun bookNameValidTest() {
        assert(BookName(validBookName).nonEmpty())
    }
}