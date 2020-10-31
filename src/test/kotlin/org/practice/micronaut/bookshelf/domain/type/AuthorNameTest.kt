package org.practice.micronaut.bookshelf.domain.type

import arrow.core.None
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import org.practice.micronaut.bookshelf.domain.TestDataFactory.invalidLengthName
import org.practice.micronaut.bookshelf.domain.TestDataFactory.validAuthorName

@MicronautTest
class AuthorNameTest {

    @Test
    fun authorNameInvalidMaxLengthTest() {
        assert(AuthorName(invalidLengthName) == None)
    }

    @Test
    fun authorNameInvalidMinLengthTest() {
        assert(AuthorName("") == None)
    }

    @Test
    fun authorNameValidTest() {
        assert(AuthorName(validAuthorName).nonEmpty())
    }

}