package org.practice.micronaut.bookshelf.domain.type

import arrow.core.None
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test

@MicronautTest
class BookNameTest {
    companion object {
        private const val invalidLength = 101
        private val invalidValue by lazy {
            (1..invalidLength).map { "a" }.reduce { first, second -> first + second }
        }
    }


    @Test
    fun bookNameInvalidMaxLengthTest() {
        assert(BookName(invalidValue) == None)
    }

    @Test
    fun bookNameInvalidMinLengthTest() {
        assert(BookName("") == None)
    }

    @Test
    fun bookNameValidTest() {
        val validValue = "Clean Architecture　達人に学ぶソフトウェアの構造と設計"
        assert(BookName(validValue).nonEmpty())
    }
}