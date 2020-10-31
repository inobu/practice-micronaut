package org.practice.micronaut.bookshelf.domain.type

import arrow.core.None
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test

@MicronautTest
class AuthorNameTest {
    companion object {
        private const val invalidLength = 101
        private val invalidValue by lazy {
            (1..invalidLength).map { "a" }.fold("", { first, second -> first + second })
        }
    }


    @Test
    fun authorNameInvalidMaxLengthTest() {
        assert(AuthorName(invalidValue) == None)
    }

    @Test
    fun authorNameInvalidMinLengthTest() {
        assert(AuthorName("") == None)
    }

    @Test
    fun authorNameValidTest() {
        val validValue = "ロバート・C・マーティン"
        assert(AuthorName(validValue).nonEmpty())
    }

}